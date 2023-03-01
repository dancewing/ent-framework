/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.authentication.auth;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.entframework.kernel.auth.api.AuthServiceApi;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.auth.api.constants.LoginCacheConstants;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.expander.AuthConfigExpander;
import io.entframework.kernel.auth.api.expander.LoginConfigExpander;
import io.entframework.kernel.auth.api.password.PasswordStoredEncryptApi;
import io.entframework.kernel.auth.api.password.PasswordTransferEncryptApi;
import io.entframework.kernel.auth.api.pojo.SsoProperties;
import io.entframework.kernel.auth.api.pojo.auth.LoginRequest;
import io.entframework.kernel.auth.api.pojo.auth.LoginResponse;
import io.entframework.kernel.auth.api.pojo.auth.LoginWithTokenRequest;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.jwt.JwtTokenOperator;
import io.entframework.kernel.jwt.api.config.JwtProperties;
import io.entframework.kernel.jwt.api.context.JwtContext;
import io.entframework.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import io.entframework.kernel.log.api.LoginLogServiceApi;
import io.entframework.kernel.log.api.enums.LoginEventType;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.message.api.expander.WebSocketConfigExpander;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.rule.util.HttpServletUtil;
import io.entframework.kernel.scanner.api.exception.ScannerException;
import io.entframework.kernel.scanner.api.exception.enums.ScannerExceptionEnum;
import io.entframework.kernel.security.api.DragCaptchaApi;
import io.entframework.kernel.security.api.ImageCaptchaApi;
import io.entframework.kernel.security.api.expander.SecurityConfigExpander;
import io.entframework.kernel.system.api.UserClientServiceApi;
import io.entframework.kernel.system.api.enums.UserStatusEnum;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.service.SysUserService;
import io.entframework.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;

/**
 * 认证服务的实现
 *
 * @date 2020/10/20 10:25
 */
public class AuthServiceImpl implements AuthServiceApi {

    /**
     * 用于操作缓存时候加锁
     */
    private static final Object SESSION_OPERATE_LOCK = new Object();

    @Resource
    private UserClientServiceApi userClientServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private PasswordTransferEncryptApi passwordTransferEncryptApi;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Resource
    private ImageCaptchaApi captchaApi;

    @Resource
    private DragCaptchaApi dragCaptchaApi;

    @Resource
    private SsoProperties ssoProperties;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private CacheOperatorApi<String> loginCacheOperatorApi;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return loginAction(loginRequest, true, null);
    }

    @Override
    public LoginResponse loginWithUserName(String username) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        return loginAction(loginRequest, false, null);
    }

    @Override
    public LoginResponse loginWithUserNameAndCaToken(String username, String caToken) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        return loginAction(loginRequest, false, caToken);
    }

    @Override
    public LoginResponse loginWithToken(LoginWithTokenRequest loginWithTokenRequest) {

        // 解析jwt token中的账号
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setJwtSecret(AuthConfigExpander.getSsoJwtSecret());
        jwtProperties.setExpiredSeconds(0L);

        // jwt工具类初始化
        JwtTokenOperator jwtTokenOperator = new JwtTokenOperator(jwtProperties);

        // 解析token中的用户信息
        Claims payload = null;
        try {
            payload = jwtTokenOperator.getJwtPayloadClaims(loginWithTokenRequest.getToken());
        }
        catch (Exception exception) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_PARSE_ERROR, exception.getMessage());
        }

        // 获取到用户信息
        Object userInfoEncryptString = payload.get("userInfo");
        if (ObjectUtil.isEmpty(userInfoEncryptString)) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_GET_USER_ERROR);
        }

        // 解密出用户账号和caToken（caToken用于校验用户是否在单点中心）
        String account;
        String caToken;
        try {
            AES aesUtil = SecureUtil.aes(Base64.decode(AuthConfigExpander.getSsoDataDecryptSecret()));
            String loginUserJson = aesUtil.decryptStr(userInfoEncryptString.toString(), CharsetUtil.CHARSET_UTF_8);
            JSONObject userInfoJsonObject = JSON.parseObject(loginUserJson);
            account = userInfoJsonObject.getString("account");
            caToken = userInfoJsonObject.getString("caToken");
        }
        catch (Exception exception) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_DECRYPT_USER_ERROR, exception.getMessage());
        }

        // 账号为空，抛出异常
        if (account == null) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_DECRYPT_USER_ERROR);
        }

        return loginWithUserNameAndCaToken(account, caToken);
    }

    @Override
    public void logout() {
        String token = LoginContext.me().getToken();

        if (CharSequenceUtil.isNotEmpty(token)) {
            loginLogServiceApi.add(createLogRequest(LoginContext.me().getLoginUser().getUserId(),
                    HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()), LoginEventType.LOGIN_OUT_SUCCESS,
                    null, LoginContext.me().getLoginUser().getAccount()));
        }

        logoutWithToken(token);
        sessionManagerApi.destroySessionCookie();

    }

    private SysLoginLogRequest createLogRequest(Long userId, String requestClientIp, LoginEventType eventType,
            String message, String account) {
        return SysLoginLogRequest.builder().userId(userId).llgIpAddress(requestClientIp).type(eventType)
                .llgMessage(message).loginAccount(account).build();
    }

    @Override
    public void logoutWithToken(String token) {
        // 清除token缓存的用户信息
        sessionManagerApi.removeSession(token);
    }

    /**
     * 登录的真正业务逻辑
     * @param loginRequest 登录参数
     * @param validatePassword 是否校验密码，true-校验密码，false-不会校验密码
     * @param caToken 单点登录后服务端的token，一般为32位uuid
     * @date 2020/10/21 16:59
     */
    private LoginResponse loginAction(LoginRequest loginRequest, Boolean validatePassword, String caToken) {
        SysUser userByAccount = sysUserService.getUserByAccount(loginRequest.getUsername());
        // 判断登录错误检测是否开启
        if (LoginConfigExpander.getAccountErrorDetectionFlag()) {
            // 判断错误次数，超过最大放入缓存中
            if (CharSequenceUtil.isBlank(loginCacheOperatorApi.get(userByAccount.getUserId().toString()))) {
                if (userByAccount.getLoginCount() > LoginCacheConstants.MAX_LOGIN_COUNT) {
                    loginCacheOperatorApi.put(userByAccount.getUserId().toString(), "true", 1800L);
                    throw new AuthException(AuthExceptionEnum.EXCEED_MAX_LOGIN_COUNT);
                }
            }
            else {
                throw new AuthException(AuthExceptionEnum.EXCEED_MAX_LOGIN_COUNT);
            }
        }

        // 5. 获取用户密码的加密值和用户的状态
        UserLoginInfoDTO userValidateInfo = userClientServiceApi.getUserLoginInfo(loginRequest.getUsername());

        // 8. 获取LoginUser，用于用户的缓存
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 1.参数为空校验
        if (validatePassword) {
            if (CharSequenceUtil.hasBlank(loginRequest.getUsername(), loginRequest.getPassword())) {
                throw new AuthException(AuthExceptionEnum.PARAM_EMPTY);
            }
        }
        else {
            if (CharSequenceUtil.hasBlank(loginRequest.getUsername())) {
                throw new AuthException(AuthExceptionEnum.ACCOUNT_IS_BLANK);
            }
        }
        String ip = HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest());
        // 2. 如果开启了验证码校验，则验证当前请求的验证码是否正确
        if (SecurityConfigExpander.getCaptchaOpen()) {
            String verKey = loginRequest.getVerKey();
            String verCode = loginRequest.getVerCode();

            if (CharSequenceUtil.isEmpty(verKey) || CharSequenceUtil.isEmpty(verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!captchaApi.validateCaptcha(verKey, verCode)) {
                // 登录失败日志
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "验证码错误", loginUser.getAccount()));
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_ERROR);
            }
        }

        // 2.1 验证拖拽验证码
        if (SecurityConfigExpander.getDragCaptchaOpen()) {
            String verKey = loginRequest.getVerKey();
            String verXLocationValue = loginRequest.getVerCode();

            if (CharSequenceUtil.isEmpty(verKey) || CharSequenceUtil.isEmpty(verXLocationValue)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!dragCaptchaApi.validateCaptcha(verKey, Convert.toInt(verXLocationValue))) {
                // 登录失败日志
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "拖拽验证码错误", loginRequest.getUsername()));
                throw new AuthException(ValidatorExceptionEnum.DRAG_CAPTCHA_ERROR);
            }
        }

        // 2.2 校验当前系统是否初始化资源完成，如果资源还没有初始化，提示用户请等一下再登录
        long resourceCount = resourceServiceApi.getResourceCount();
        if (resourceCount == 0) {
            throw new ScannerException(ScannerExceptionEnum.SYSTEM_RESOURCE_URL_NOT_INIT);
        }

        // 3. 解密密码的密文
        // String decryptPassword =
        // passwordTransferEncryptApi.decrypt(loginRequest.getPassword());

        // 4. 如果开启了单点登录，并且CaToken没有值，走单点登录，获取loginCode
        if (ssoProperties.isOpenFlag() && CharSequenceUtil.isEmpty(caToken)) {
            // 调用单点的接口获取loginCode，远程接口校验用户级密码正确性。
            String remoteLoginCode = getRemoteLoginCode(loginRequest);
            return new LoginResponse(remoteLoginCode);
        }

        // 6. 校验用户密码是否正确
        if (validatePassword) {
            Boolean checkResult = passwordStoredEncryptApi.checkPassword(loginRequest.getPassword(),
                    userValidateInfo.getUserPasswordHexed());
            if (!checkResult) {
                // 更新登录次数
                userByAccount.setLoginCount(userByAccount.getLoginCount() + 1);
                sysUserService.update(userByAccount);
                // 登录失败日志
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "帐号或密码错误", loginRequest.getUsername()));
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        }

        // 7. 校验用户是否异常（不是正常状态）
        if (UserStatusEnum.ENABLE != userValidateInfo.getUserStatus()) {
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR, userValidateInfo.getUserStatus().getMessage());
        }

        // 9. 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                loginRequest.getRememberMe(), caToken);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        // 如果包含租户编码，则放到loginUser中
        loginUser.setTenantCode(loginRequest.getTenantCode());

        synchronized (SESSION_OPERATE_LOCK) {

            // 9.1 获取ws-url 保存到用户信息中
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 10. 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser, loginRequest.getCreateCookie());

            // 11. 如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        // 12. 更新用户登录时间和ip
        userClientServiceApi.updateUserLoginInfo(loginUser.getUserId(), LocalDateTime.now(), ip);

        // 重置登录次数
        userByAccount.setLoginCount(1);
        sysUserService.update(userByAccount);

        // 13.登录成功日志
        loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_SUCCESS, null,
                loginRequest.getUsername()));

        // 14. 组装返回结果
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(loginUser.getUserId());
        loginResponse.setToken(jwtToken);
        loginResponse.setExpireAt(defaultJwtPayload.getExpirationDate());
        loginResponse.setSsoLogin(false);
        loginResponse.setSsoLoginCode(null);

        return loginResponse;
    }

    /**
     * 调用远程接口获取loginCode
     *
     * @date 2021/2/26 15:15
     */
    private String getRemoteLoginCode(LoginRequest loginRequest) {

        // 获取sso的地址
        String ssoUrl = AuthConfigExpander.getSsoUrl();

        // 请求sso服务获取loginCode
        HttpRequest httpRequest = HttpRequest.post(ssoUrl + AuthConstants.SYS_AUTH_SSO_GET_LOGIN_CODE);
        httpRequest.body(JSON.toJSONString(loginRequest));
        HttpResponse httpResponse = httpRequest.execute();

        // 获取返回结果的message
        String body = httpResponse.body();
        JSONObject jsonObject = new JSONObject();
        if (CharSequenceUtil.isNotBlank(body)) {
            jsonObject = JSON.parseObject(body);
        }

        // 如果返回结果是失败的
        if (httpResponse.getStatus() != 200) {
            String message = jsonObject.getString("message");
            throw new AuthException(AuthExceptionEnum.SSO_LOGIN_CODE_GET_ERROR, message);
        }

        // 从body中获取loginCode
        String loginCode = jsonObject.getString("data");

        // loginCode为空
        if (loginCode == null) {
            throw new AuthException(AuthExceptionEnum.SSO_LOGIN_CODE_GET_ERROR, "loginCode为空");
        }

        return loginCode;
    }

    @Override
    public void cancelFreeze(LoginRequest loginRequest) {
        SysUser sysUser = sysUserService.getUserByAccount(loginRequest.getUsername());
        sysUser.setLoginCount(1);
        // 修改数据库中的登录次数
        sysUserService.update(sysUser);
        // 删除缓存中的数据
        loginCacheOperatorApi.remove(sysUser.getUserId().toString());
    }

}

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
 * ?????????????????????
 *
 * @date 2020/10/20 10:25
 */
public class AuthServiceImpl implements AuthServiceApi {

    /**
     * ??????????????????????????????
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

        // ??????jwt token????????????
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setJwtSecret(AuthConfigExpander.getSsoJwtSecret());
        jwtProperties.setExpiredSeconds(0L);

        // jwt??????????????????
        JwtTokenOperator jwtTokenOperator = new JwtTokenOperator(jwtProperties);

        // ??????token??????????????????
        Claims payload = null;
        try {
            payload = jwtTokenOperator.getJwtPayloadClaims(loginWithTokenRequest.getToken());
        }
        catch (Exception exception) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_PARSE_ERROR, exception.getMessage());
        }

        // ?????????????????????
        Object userInfoEncryptString = payload.get("userInfo");
        if (ObjectUtil.isEmpty(userInfoEncryptString)) {
            throw new AuthException(AuthExceptionEnum.SSO_TOKEN_GET_USER_ERROR);
        }

        // ????????????????????????caToken???caToken??????????????????????????????????????????
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

        // ???????????????????????????
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
        // ??????token?????????????????????
        sessionManagerApi.removeSession(token);
    }

    /**
     * ???????????????????????????
     * @param loginRequest ????????????
     * @param validatePassword ?????????????????????true-???????????????false-??????????????????
     * @param caToken ???????????????????????????token????????????32???uuid
     * @date 2020/10/21 16:59
     */
    private LoginResponse loginAction(LoginRequest loginRequest, Boolean validatePassword, String caToken) {
        SysUser userByAccount = sysUserService.getUserByAccount(loginRequest.getUsername());
        // ????????????????????????????????????
        if (LoginConfigExpander.getAccountErrorDetectionFlag()) {
            // ????????????????????????????????????????????????
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

        // 5. ????????????????????????????????????????????????
        UserLoginInfoDTO userValidateInfo = userClientServiceApi.getUserLoginInfo(loginRequest.getUsername());

        // 8. ??????LoginUser????????????????????????
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 1.??????????????????
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
        // 2. ??????????????????????????????????????????????????????????????????????????????
        if (SecurityConfigExpander.getCaptchaOpen()) {
            String verKey = loginRequest.getVerKey();
            String verCode = loginRequest.getVerCode();

            if (CharSequenceUtil.isEmpty(verKey) || CharSequenceUtil.isEmpty(verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!captchaApi.validateCaptcha(verKey, verCode)) {
                // ??????????????????
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "???????????????", loginUser.getAccount()));
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_ERROR);
            }
        }

        // 2.1 ?????????????????????
        if (SecurityConfigExpander.getDragCaptchaOpen()) {
            String verKey = loginRequest.getVerKey();
            String verXLocationValue = loginRequest.getVerCode();

            if (CharSequenceUtil.isEmpty(verKey) || CharSequenceUtil.isEmpty(verXLocationValue)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!dragCaptchaApi.validateCaptcha(verKey, Convert.toInt(verXLocationValue))) {
                // ??????????????????
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "?????????????????????", loginRequest.getUsername()));
                throw new AuthException(ValidatorExceptionEnum.DRAG_CAPTCHA_ERROR);
            }
        }

        // 2.2 ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        long resourceCount = resourceServiceApi.getResourceCount();
        if (resourceCount == 0) {
            throw new ScannerException(ScannerExceptionEnum.SYSTEM_RESOURCE_URL_NOT_INIT);
        }

        // 3. ?????????????????????
        // String decryptPassword =
        // passwordTransferEncryptApi.decrypt(loginRequest.getPassword());

        // 4. ????????????????????????????????????CaToken????????????????????????????????????loginCode
        if (ssoProperties.isOpenFlag() && CharSequenceUtil.isEmpty(caToken)) {
            // ???????????????????????????loginCode????????????????????????????????????????????????
            String remoteLoginCode = getRemoteLoginCode(loginRequest);
            return new LoginResponse(remoteLoginCode);
        }

        // 6. ??????????????????????????????
        if (validatePassword) {
            Boolean checkResult = passwordStoredEncryptApi.checkPassword(loginRequest.getPassword(),
                    userValidateInfo.getUserPasswordHexed());
            if (!checkResult) {
                // ??????????????????
                userByAccount.setLoginCount(userByAccount.getLoginCount() + 1);
                sysUserService.update(userByAccount);
                // ??????????????????
                loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_FAIL,
                        "?????????????????????", loginRequest.getUsername()));
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        }

        // 7. ????????????????????????????????????????????????
        if (UserStatusEnum.ENABLE != userValidateInfo.getUserStatus()) {
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR, userValidateInfo.getUserStatus().getMessage());
        }

        // 9. ???????????????token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                loginRequest.getRememberMe(), caToken);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        // ????????????????????????????????????loginUser???
        loginUser.setTenantCode(loginRequest.getTenantCode());

        synchronized (SESSION_OPERATE_LOCK) {

            // 9.1 ??????ws-url ????????????????????????
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 10. ?????????????????????????????????
            sessionManagerApi.createSession(jwtToken, loginUser, loginRequest.getCreateCookie());

            // 11. ????????????????????????????????????????????????????????????????????????
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        // 12. ???????????????????????????ip
        userClientServiceApi.updateUserLoginInfo(loginUser.getUserId(), LocalDateTime.now(), ip);

        // ??????????????????
        userByAccount.setLoginCount(1);
        sysUserService.update(userByAccount);

        // 13.??????????????????
        loginLogServiceApi.add(createLogRequest(loginUser.getUserId(), ip, LoginEventType.LOGIN_IN_SUCCESS, null,
                loginRequest.getUsername()));

        // 14. ??????????????????
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(loginUser.getUserId());
        loginResponse.setToken(jwtToken);
        loginResponse.setExpireAt(defaultJwtPayload.getExpirationDate());
        loginResponse.setSsoLogin(false);
        loginResponse.setSsoLoginCode(null);

        return loginResponse;
    }

    /**
     * ????????????????????????loginCode
     *
     * @date 2021/2/26 15:15
     */
    private String getRemoteLoginCode(LoginRequest loginRequest) {

        // ??????sso?????????
        String ssoUrl = AuthConfigExpander.getSsoUrl();

        // ??????sso????????????loginCode
        HttpRequest httpRequest = HttpRequest.post(ssoUrl + AuthConstants.SYS_AUTH_SSO_GET_LOGIN_CODE);
        httpRequest.body(JSON.toJSONString(loginRequest));
        HttpResponse httpResponse = httpRequest.execute();

        // ?????????????????????message
        String body = httpResponse.body();
        JSONObject jsonObject = new JSONObject();
        if (CharSequenceUtil.isNotBlank(body)) {
            jsonObject = JSON.parseObject(body);
        }

        // ??????????????????????????????
        if (httpResponse.getStatus() != 200) {
            String message = jsonObject.getString("message");
            throw new AuthException(AuthExceptionEnum.SSO_LOGIN_CODE_GET_ERROR, message);
        }

        // ???body?????????loginCode
        String loginCode = jsonObject.getString("data");

        // loginCode??????
        if (loginCode == null) {
            throw new AuthException(AuthExceptionEnum.SSO_LOGIN_CODE_GET_ERROR, "loginCode??????");
        }

        return loginCode;
    }

    @Override
    public void cancelFreeze(LoginRequest loginRequest) {
        SysUser sysUser = sysUserService.getUserByAccount(loginRequest.getUsername());
        sysUser.setLoginCount(1);
        // ?????????????????????????????????
        sysUserService.update(sysUser);
        // ????????????????????????
        loginCacheOperatorApi.remove(sysUser.getUserId().toString());
    }

}

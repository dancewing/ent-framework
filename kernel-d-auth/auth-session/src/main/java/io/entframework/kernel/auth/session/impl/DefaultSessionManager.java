/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.session.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.config.AuthConfigProperties;
import io.entframework.kernel.auth.api.cookie.SessionCookieCreator;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.jwt.api.context.JwtContext;
import io.entframework.kernel.jwt.api.exception.JwtException;
import io.entframework.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import io.entframework.kernel.rule.util.HttpServletUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * 基于redis的会话管理
 *
 * @date 2019-09-28-14:43
 */
public class DefaultSessionManager implements SessionManagerApi {

    /**
     * 登录用户缓存
     * <p>
     * key是 LOGGED_TOKEN_PREFIX + 用户的token
     */
    private final CacheOperatorApi<LoginUser> loginUserCache;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     * <p>
     * 没开启单端限制的话，一个用户可能有多个token，因为一个用户可能在多个地点或打开多个浏览器访问系统
     * <p>
     * key是 LOGGED_USERID_PREFIX + 用户id
     * <p>
     * 这个缓存应该定时刷新下，因为有过期token的用户，所以这个里边的值set得清理
     */
    private final CacheOperatorApi<Set<String>> allUserLoginTokenCache;

    /**
     * session的超时时间
     */
    private final Long sessionExpiredSeconds;

    /**
     * cookie的创建器，用在session创建时，给response添加cookie
     */
    private final SessionCookieCreator sessionCookieCreator;

    private final AuthConfigProperties authConfigProperties;

    public DefaultSessionManager(CacheOperatorApi<LoginUser> loginUserCache,
            CacheOperatorApi<Set<String>> allUserLoginTokenCache, Long sessionExpiredSeconds,
            SessionCookieCreator sessionCookieCreator, AuthConfigProperties authConfigProperties) {
        this.loginUserCache = loginUserCache;
        this.allUserLoginTokenCache = allUserLoginTokenCache;
        this.sessionExpiredSeconds = sessionExpiredSeconds;
        this.sessionCookieCreator = sessionCookieCreator;
        this.authConfigProperties = authConfigProperties;
    }

    @Override
    public void createSession(String token, LoginUser loginUser, Boolean createCookie) {

        // 装配用户信息的缓存
        loginUserCache.put(token, loginUser, sessionExpiredSeconds);

        // 装配用户token的缓存
        Set<String> theUserTokens = allUserLoginTokenCache.get(loginUser.getUserId().toString());
        if (theUserTokens == null) {
            theUserTokens = new HashSet<>();
        }
        theUserTokens.add(token);
        allUserLoginTokenCache.put(loginUser.getUserId().toString(), theUserTokens);

        // 如果开启了cookie存储会话信息，则需要给HttpServletResponse添加一个cookie
        if (createCookie) {
            String sessionCookieName = authConfigProperties.getSessionCookieName();
            Cookie cookie = sessionCookieCreator.createCookie(sessionCookieName, token,
                    Convert.toInt(sessionExpiredSeconds));
            HttpServletResponse response = HttpServletUtil.getResponse();
            response.addCookie(cookie);
        }

    }

    @Override
    public void updateSession(String token, LoginUser loginUser) {
        LoginUser session = this.getSession(token);

        // 该用户session为空不能更新
        if (session == null) {
            return;
        }

        loginUserCache.put(token, loginUser, sessionExpiredSeconds);
    }

    @Override
    public LoginUser getSession(String token) {
        return loginUserCache.get(token);
    }

    @Override
    public void removeSession(String token) {

        LoginUser loginUser = loginUserCache.get(token);

        // 删除本token用户信息的缓存
        loginUserCache.remove(token);

        // 删除多端登录信息
        if (loginUser != null) {
            Long userId = loginUser.getUserId();
            Set<String> userTokens = allUserLoginTokenCache.get(userId.toString());
            if (userTokens != null) {

                // 清除对应的token的信息
                userTokens.remove(token);
                allUserLoginTokenCache.put(userId.toString(), userTokens);

                // 如果删除后size为0，则把整个key删掉
                if (userTokens.size() == 0) {
                    allUserLoginTokenCache.remove(userId.toString());
                }
            }
        }
    }

    @Override
    public void removeSessionExcludeToken(String token) {

        // 获取token对应的会话
        LoginUser session = this.getSession(token);

        // 如果会话为空，直接返回
        if (session == null) {
            return;
        }

        // 获取用户id
        Long userId = session.getUserId();

        // 获取这个用户多余的几个登录信息
        Set<String> thisUserTokens = allUserLoginTokenCache.get(userId.toString());
        for (String thisUserToken : thisUserTokens) {
            if (!thisUserToken.equals(token)) {
                loginUserCache.remove(thisUserToken);
            }
        }

        // 设置用户id对应的token列表为参数token
        HashSet<String> tokenSet = new HashSet<>();
        tokenSet.add(token);
        allUserLoginTokenCache.put(userId.toString(), tokenSet);
    }

    @Override
    public boolean haveSession(String token) {
        return loginUserCache.contains(token);
    }

    @Override
    public void refreshSession(String token) {
        LoginUser loginUser = loginUserCache.get(token);
        if (loginUser != null) {
            loginUserCache.expire(token, sessionExpiredSeconds);
        }
    }

    @Override
    public void destroySessionCookie() {
        // 如果开启了cookie存储会话信息，则需要给HttpServletResponse添加一个cookie
        String sessionCookieName = authConfigProperties.getSessionCookieName();
        Cookie cookie = sessionCookieCreator.createCookie(sessionCookieName, null, 0);
        HttpServletResponse response = HttpServletUtil.getResponse();
        response.addCookie(cookie);
    }

    @Override
    public List<LoginUser> onlineUserList() {
        Map<String, LoginUser> allKeyValues = loginUserCache.getAllKeyValues();

        ArrayList<LoginUser> loginUsers = new ArrayList<>();
        for (Map.Entry<String, LoginUser> userEntry : allKeyValues.entrySet()) {
            String token = userEntry.getKey();
            LoginUser loginUser = userEntry.getValue();
            LoginUser tempUser = new LoginUser();
            BeanUtil.copyProperties(loginUser, tempUser);
            tempUser.setToken(token);
            loginUsers.add(tempUser);
        }

        return loginUsers;
    }

    @Override
    public void validateToken(String token) throws AuthException {
        try {
            // 1. 先校验jwt token本身是否有问题
            JwtContext.me().validateTokenWithException(token);

            // 2. 判断session里是否有这个token
            LoginUser session = this.getSession(token);
            if (session == null) {
                throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
            }
        }
        catch (JwtException jwtException) {
            // jwt token本身过期的话，返回 AUTH_EXPIRED_ERROR
            if (JwtExceptionEnum.JWT_EXPIRED_ERROR.getErrorCode().equals(jwtException.getErrorCode())) {
                throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
            }
            else {
                // 其他情况为返回jwt解析错误
                throw new AuthException(AuthExceptionEnum.TOKEN_PARSE_ERROR);
            }
        }
        catch (io.jsonwebtoken.JwtException jwtSelfException) {
            // 其他jwt解析错误
            throw new AuthException(AuthExceptionEnum.TOKEN_PARSE_ERROR);
        }
    }

    @Override
    public void checkAuth(String token, String requestUrl) {

        // 1. 校验token是否传参
        if (CharSequenceUtil.isEmpty(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }

        // 2. 校验用户token是否正确，校验失败会抛出异常
        this.validateToken(token);

    }

}

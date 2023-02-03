/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api;

import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;

import java.util.List;

/**
 * 用户会话管理
 * <p>
 * 会话指的是用户登录后和服务器一直保持一个交互状态的维护
 * <p>
 * 会话具有时效性，反之，当用户不再访问系统的时候，会话应该自动失效
 *
 * @date 2020/10/19 16:47
 */
public interface SessionManagerApi {

    /**
     * 创建会话
     *
     * @param token     用户登录的token
     * @param loginUser 登录的用户
     * @param loginUser 登录的用户
     * @date 2020/10/19 16:47
     */
    void createSession(String token, LoginUser loginUser, Boolean createCookie);

    /**
     * 更新当前会话的loginUser对象的内容
     *
     * @param token     用户的当前token
     * @param loginUser 新的登录用户信息
     * @date 2021/1/9 10:43
     */
    void updateSession(String token, LoginUser loginUser);

    /**
     * 通过token获取会话
     *
     * @param token 用户token
     * @return token对应用户的详细信息
     * @date 2020/10/19 16:48
     */
    LoginUser getSession(String token);

    /**
     * 根据token删除一个会话
     *
     * @param token 用户token
     * @date 2020/10/19 16:48
     */
    void removeSession(String token);

    /**
     * 删除用户所有的会话，但除了参数传的token的会话
     * <p>
     * 用在单端登录中，一个账号只能在一个浏览器登录
     *
     * @param token 用户token
     * @date 2020/10/21 16:18
     */
    void removeSessionExcludeToken(String token);

    /**
     * 判断一个token是否还存在会话状态
     *
     * @param token 用户token
     * @return true-存在会话，false-不存在会话或者失效了
     * @date 2020/10/19 16:49
     */
    boolean haveSession(String token);

    /**
     * 刷新会话的过期时间，刷新后用户当前的过期时间将重置
     *
     * @param token 用户的token
     * @date 2020/10/19 16:50
     */
    void refreshSession(String token);

    /**
     * 销毁当前用户对应的会话cookie
     * <p>
     * 一般用在单体不分离版本中
     *
     * @date 2021/1/2 20:25
     */
    void destroySessionCookie();

    /**
     * 获取在线用户列表
     *
     * @date 2021/1/9 10:41
     */
    List<LoginUser> onlineUserList();

    /**
     * 校验jwt token的正确性，调用jwt工具类相关方法校验
     * <p>
     * 结果有三种，第一是jwt过期了，第二是用户随便写的错误token，第三种是token正确，token正确不会抛出异常
     *
     * @param token 某个用户的登录token
     * @throws AuthException 认证异常，如果token错误或过期，会有相关的异常抛出
     * @date 2020/10/19 14:16
     */
    void validateToken(String token) throws AuthException;

    /**
     * 校验用户是否认证通过，认证是校验token的过程，校验失败会抛出异常
     *
     * @param token      用户登陆的token
     * @param requestUrl 被校验的url
     * @date 2020/10/22 16:03
     */
    void checkAuth(String token, String requestUrl);

}

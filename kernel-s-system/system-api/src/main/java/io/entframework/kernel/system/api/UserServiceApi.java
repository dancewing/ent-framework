/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api;

import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.system.api.pojo.user.OnlineUserDTO;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;

import java.util.List;
import java.util.Set;

/**
 * 用户管理服务接口
 *
 * @date 2020/10/20 13:20
 */
public interface UserServiceApi extends UserClientServiceApi {


    /**
     * 获取刷新后的登录用户（用在用户登录之后调用）
     * <p>
     * 以往用户登录后直接从session缓存中获取用户信息，不能及时更新，需要退出才可以获取最新信息
     * <p>
     * 本方法解决了实时获取当前登录用户不准确的问题
     *
     * @date 2021/7/29 22:03
     */
    LoginUser getEffectiveLoginUser(LoginUser loginUser);

    /**
     * 根据机构id集合删除对应的用户-数据范围关联信息
     *
     * @param organizationIds 组织架构id集合
     * @date 2020/10/20 16:59
     */
    void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds);

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @date 2020/11/5 下午3:57
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 根据角色id删除对应的用户-角色表关联信息
     *
     * @param roleId 角色id
     * @date 2020/11/5 下午3:58
     */
    void deleteUserRoleListByRoleId(Long roleId);

    /**
     * 获取用户单独绑定的数据范围，sys_user_data_scope表中的数据范围
     *
     * @param userId 用户id
     * @return 用户数据范围
     * @date 2020/11/21 12:15
     */
    List<Long> getUserBindDataScope(Long userId);

    /**
     * 获取在线用户列表
     *
     * @date 2021/1/10 9:56
     */
    List<OnlineUserDTO> onlineUserList(OnlineUserRequest onlineUserRequest);


    /**
     * 获取用户的头像url
     *
     * @date 2021/12/29 17:27
     */
    String getUserAvatarUrlByUserId(Long userId);

}

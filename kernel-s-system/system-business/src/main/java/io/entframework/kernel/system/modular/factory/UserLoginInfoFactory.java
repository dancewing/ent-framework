/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import io.entframework.kernel.auth.api.prop.LoginUserPropExpander;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.pojo.login.CurrentUserInfoResponse;
import io.entframework.kernel.system.api.pojo.organization.DataScopeDTO;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.service.SysUserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组装当前登录用户的信息
 *
 * @date 2020/11/26 22:25
 */
public class UserLoginInfoFactory {

    private UserLoginInfoFactory() {
    }

    /**
     * 组装登录用户信息对象
     * @param sysUser 用户信息
     * @param roleResponseList 角色信息
     * @param dataScopeResponse 数据范围信息
     * @param resourceUrlsListByCodes 用户的所有资源url
     * @param roleButtonCodes 用户的所拥有的按钮编码
     * @date 2020/12/26 17:53
     */
    public static UserLoginInfoDTO userLoginInfoDTO(SysUser sysUser, List<SysRoleResponse> roleResponseList,
            DataScopeDTO dataScopeResponse, Set<String> resourceUrlsListByCodes, Set<String> roleButtonCodes) {

        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();

        // 设置用户加密的密码和状态
        userLoginInfoDTO.setUserPasswordHexed(sysUser.getPassword());
        userLoginInfoDTO.setUserStatus(sysUser.getStatusFlag());

        // 创建登录用户对象
        LoginUser loginUser = new LoginUser();

        // 填充用户账号，账号id，管理员类型
        loginUser.setAccount(sysUser.getAccount());
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setSuperAdmin(YesOrNotEnum.Y == sysUser.getSuperAdminFlag());

        // 填充用户基本信息
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        BeanUtil.copyProperties(sysUser, simpleUserInfo);
        loginUser.setSimpleUserInfo(simpleUserInfo);

        // 填充用户角色信息
        if (!roleResponseList.isEmpty()) {
            ArrayList<SimpleRoleInfo> simpleRoleInfos = new ArrayList<>();
            for (SysRoleResponse sysRoleResponse : roleResponseList) {
                SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
                BeanUtil.copyProperties(sysRoleResponse, simpleRoleInfo);
                simpleRoleInfos.add(simpleRoleInfo);
            }
            loginUser.setSimpleRoleInfoList(simpleRoleInfos);
        }

        // 设置用户数据范围
        if (dataScopeResponse != null) {
            loginUser.setDataScopeTypeEnums(dataScopeResponse.getDataScopeTypeEnums());
            loginUser.setDataScopeOrganizationIds(dataScopeResponse.getOrganizationIds());
            loginUser.setDataScopeUserIds(dataScopeResponse.getUserIds());
        }

        // 设置用户拥有的资源
        loginUser.setResourceUrls(resourceUrlsListByCodes);

        // 基于接口拓展用户登录信息
        Map<String, LoginUserPropExpander> beansOfLoginUserExpander = SpringUtil
                .getBeansOfType(LoginUserPropExpander.class);
        if (beansOfLoginUserExpander != null && beansOfLoginUserExpander.size() > 0) {
            for (Map.Entry<String, LoginUserPropExpander> stringLoginUserPropExpanderEntry : beansOfLoginUserExpander
                    .entrySet()) {
                stringLoginUserPropExpanderEntry.getValue().expandAction(loginUser);
            }
        }

        // 填充用户拥有的按钮编码
        loginUser.setButtonCodes(roleButtonCodes);

        // 设置用户的登录时间
        loginUser.setLoginTime(new Date());

        // 响应dto
        userLoginInfoDTO.setLoginUser(loginUser);
        return userLoginInfoDTO;
    }

    /**
     * 转化为当前登陆用户信息的详情
     *
     * @date 2021/3/25 10:06
     */
    public static CurrentUserInfoResponse parseUserInfo(LoginUser loginUser) {

        SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);
        CurrentUserInfoResponse currentUserInfoResponse = new CurrentUserInfoResponse();

        // 设置用户id
        currentUserInfoResponse.setUserId(loginUser.getUserId());

        // 设置组织id
        currentUserInfoResponse.setOrganizationId(loginUser.getOrganizationId());

        // 登录人的ws-url
        currentUserInfoResponse.setWsUrl(loginUser.getWsUrl());

        // 设置用户昵称
        currentUserInfoResponse.setNickname(loginUser.getSimpleUserInfo().getNickName());

        // 姓名
        currentUserInfoResponse.setRealName(loginUser.getSimpleUserInfo().getRealName());

        // 设置头像，并获取头像的url
        Long avatarFileId = loginUser.getSimpleUserInfo().getAvatar();
        String userAvatarUrl = sysUserService.getUserAvatarUrl(avatarFileId, loginUser.getToken());
        currentUserInfoResponse.setAvatar(userAvatarUrl);

        // 设置角色
        List<SimpleRoleInfo> simpleRoleInfoList = loginUser.getSimpleRoleInfoList();
        Set<String> roleCodes = simpleRoleInfoList.stream().map(SimpleRoleInfo::getRoleCode)
                .collect(Collectors.toSet());
        currentUserInfoResponse.setRoles(roleCodes);

        // 设置用户拥有的按钮权限
        currentUserInfoResponse.setAuthorities(loginUser.getButtonCodes());

        return currentUserInfoResponse;
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.util;

import cn.hutool.core.collection.CollUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.organization.DataScopeExceptionEnum;

import java.util.Set;

/**
 * 数据范围相关的工具类，快捷方法
 *
 * @date 2020/11/5 15:31
 */
public class DataScopeUtil {

    /**
     * 一句话获取当前登录用户的数据范围信息
     *
     * @date 2020/11/5 16:07
     */
    public static String getDataScopeTip() {

        StringBuilder tips = new StringBuilder();

        // 获取当前登录用户
        LoginUser loginUser;
        try {
            loginUser = LoginContext.me().getLoginUser();
        }
        catch (AuthException e) {
            return tips.append("空：获取不到当前用户").toString();
        }

        Set<DataScopeTypeEnum> dataScopeTypes = loginUser.getDataScopeTypeEnums();
        if (dataScopeTypes == null) {
            return tips.append("空：数据范围为空").toString();
        }

        tips.append("用户数据范围类型：");
        if (!dataScopeTypes.isEmpty()) {
            for (DataScopeTypeEnum dataScopeType : dataScopeTypes) {
                tips.append(dataScopeType.getLabel()).append(",");
            }
        }

        Set<Long> userDataScope = loginUser.getDataScopeUserIds();
        tips.append("用户userId数据范围：");
        if (userDataScope != null && !userDataScope.isEmpty()) {
            for (Long id : userDataScope) {
                tips.append(id).append(",");
            }
        }

        Set<Long> organizationDataScope = loginUser.getDataScopeOrganizationIds();
        tips.append("用户organizationId数据范围：");
        if (organizationDataScope != null && !organizationDataScope.isEmpty()) {
            for (Long id : organizationDataScope) {
                tips.append(id).append(",");
            }
        }

        return tips.toString();
    }

    /**
     * 判断当前登录用户是否有某个组织架构id的数据范围
     * @param organizationId 被校验的组织机构id
     * @date 2020/11/5 15:31
     */
    public static boolean validateDataScopeByOrganizationId(Long organizationId) {

        // 获取当前登录用户
        LoginUser loginUser;
        try {
            loginUser = LoginContext.me().getLoginUser();
        }
        catch (AuthException e) {
            return false;
        }

        // 超级管理员包含所有数据范围
        if (loginUser.getSuperAdmin()) {
            return true;
        }

        // 获取用户的数据范围类型，user数据范围，组织机构数据范围
        Set<DataScopeTypeEnum> dataScopeTypes = loginUser.getDataScopeTypeEnums();
        Set<Long> organizationDataScope = loginUser.getDataScopeOrganizationIds();

        // 如果数据范围类型为空，则返回没权限
        if (dataScopeTypes == null || dataScopeTypes.isEmpty()) {
            return false;
        }

        // 如果数据范围类型里有全部数据，则返回成功
        if (dataScopeTypes.contains(DataScopeTypeEnum.ALL)) {
            return true;
        }

        // 如果仅有本人的权限
        if (dataScopeTypes.size() == 1) {
            DataScopeTypeEnum dataScopeTypeEnum = CollUtil.newArrayList(dataScopeTypes).get(0);
            if (dataScopeTypeEnum.equals(DataScopeTypeEnum.SELF)) {
                return false;
            }
        }

        // 如果部门范围为空，返回失败
        if (organizationDataScope == null || organizationDataScope.isEmpty()) {
            return false;
        }

        // 剩下的情况，就判断数据范围里有没有包含 organizationId
        return organizationDataScope.contains(organizationId);
    }

    /**
     * 快速校验用户是否有该组织机构的数据范围，如果没有就抛出异常直接
     * @param organizationId 被校验的组织机构id
     * @date 2020/11/5 15:31
     */
    public static void quickValidateDataScope(Long organizationId) {
        boolean validateResult = validateDataScopeByOrganizationId(organizationId);
        if (!validateResult) {
            throw new SystemModularException(DataScopeExceptionEnum.DATA_SCOPE_ERROR, DataScopeUtil.getDataScopeTip());
        }
    }

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.db.api.DbOperatorApi;
import io.entframework.kernel.db.mds.repository.GeneralRepository;
import io.entframework.kernel.system.api.DataScopeApi;
import io.entframework.kernel.system.api.RoleServiceApi;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.organization.DataScopeExceptionEnum;
import io.entframework.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import io.entframework.kernel.system.api.pojo.organization.DataScopeDTO;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.service.SysUserDataScopeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据范围服务
 *
 * @date 2020/11/6 12:01
 */
@Service
public class DataScopeService implements DataScopeApi {

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private DbOperatorApi dbOperatorApi;

    @Resource
    private GeneralRepository generalRepository;

    @Override
    public DataScopeDTO getDataScope(Long userId, List<SysRoleResponse> sysRoles) {

        // 初始化返回结果
        DataScopeDTO dataScopeResponse = new DataScopeDTO();
        Set<Long> userIds = new HashSet<>();
        Set<Long> organizationIds = new HashSet<>();

        if (userId == null) {
            throw new SystemModularException(DataScopeExceptionEnum.USER_ID_EMPTY_ERROR);
        }

        SysUser user = generalRepository.get(SysUser.class, userId);
        if (user == null) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, userId);
        }

        // 获取角色中的数据范围类型
        Set<DataScopeTypeEnum> dataScopeTypeEnums = sysRoles.stream().map(SysRoleResponse::getDataScopeTypeEnum).collect(Collectors.toSet());
        dataScopeResponse.setDataScopeTypeEnums(dataScopeTypeEnums);

        // 1.根据数据范围类型的不同，填充角色拥有的 organizationIds 和 userIds 范围
        // 1.1 包含全部数据类型的，直接不用设置组织机构，直接放开
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.ALL)) {
            return dataScopeResponse;
        }

        // 1.2 包含指定部门数据，将指定数据范围增加到Set中
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEFINE)) {

            // 获取角色对应的组织机构范围
            List<Long> roleIds = sysRoles.stream().map(SysRoleResponse::getRoleId).toList();
            List<Long> orgIds = roleServiceApi.getRoleDataScopes(roleIds);
            organizationIds.addAll(orgIds);
        }

        // 1.3 本部门和本部门以下，查出用户的主要部门，并且查询该部门本部门及以下的组织机构id列表
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT_WITH_CHILD)) {

            // 获取部门及以下部门的id列表
            Long organizationId = user.getOrgId();
            Set<Long> subOrgIds = dbOperatorApi.findSubListByParentId("hr_organization", "org_pids", "org_id", organizationId);
            organizationIds.add(organizationId);
            organizationIds.addAll(subOrgIds);
        }

        // 1.4 如果是本部门，则查出本部门并添加到组织机构列表
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT)) {

            // 获取本部门的id
            Long organizationId = user.getOrgId();
            organizationIds.add(organizationId);
        }

        // 1.5 如果只是本用户数据，将用户本身装进userId数据范围
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.SELF)) {
            userIds.add(userId);
        }

        // 2. 获取用户单独绑定的组织机构id
        List<Long> userBindDataScope = sysUserDataScopeService.findOrgIdsByUserId(userId);
        organizationIds.addAll(userBindDataScope);

        // 3. 组装返回结果
        dataScopeResponse.setUserIds(userIds);
        dataScopeResponse.setOrganizationIds(organizationIds);

        return dataScopeResponse;
    }

}

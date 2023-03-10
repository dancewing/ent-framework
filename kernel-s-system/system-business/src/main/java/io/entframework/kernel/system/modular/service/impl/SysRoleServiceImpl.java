/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.role.SysRoleExceptionEnum;
import io.entframework.kernel.system.api.pojo.request.SysRoleDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleResourceRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleDataScopeResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleResourceResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.api.util.DataScopeUtil;
import io.entframework.kernel.system.modular.entity.SysRole;
import io.entframework.kernel.system.modular.entity.SysRoleDynamicSqlSupport;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;
import io.entframework.kernel.system.modular.service.*;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ????????????service???????????????
 *
 * @date 2020/11/5 ??????11:33
 */
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleRequest, SysRoleResponse, SysRole>
        implements SysRoleService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;

    @Resource
    private SysRoleMenuService roleMenuService;

    @Resource
    private SysMenuResourceService sysMenuResourceService;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private CacheOperatorApi<SysRole> roleInfoCacheApi;

    @Resource(name = "roleResourceCacheApi")
    private CacheOperatorApi<List<String>> roleResourceCacheApi;

    @Resource(name = "roleDataScopeCacheApi")
    private CacheOperatorApi<List<Long>> roleDataScopeCacheApi;

    public SysRoleServiceImpl() {
        super(SysRoleRequest.class, SysRoleResponse.class, SysRole.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysRoleRequest sysRoleRequest) {

        SysRole sysRole = this.converterService.convert(sysRoleRequest, getEntityClass());
        // ?????????????????????
        sysRole.setStatusFlag(StatusEnum.ENABLE);

        // ???????????????????????????
        sysRole.setRoleSystemFlag(YesOrNotEnum.N);

        // ??????????????????
        sysRole.setDataScopeType(DataScopeTypeEnum.SELF);

        this.getRepository().insert(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ???????????????????????????
        if (YesOrNotEnum.Y == sysRole.getRoleSystemFlag()) {
            throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
        }

        // ?????????????????????????????????
        sysRole.setDelFlag(YesOrNotEnum.Y);

        this.getRepository().updateByPrimaryKey(sysRole);

        Long roleId = sysRole.getRoleId();

        // ????????????????????????????????????-????????????????????????
        sysRoleDataScopeService.delByRoleId(roleId);

        // ????????????????????????????????????-?????????????????????
        SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
        sysUserRoleRequest.setRoleId(roleId);
        sysUserRoleService.deleteBy(sysUserRoleRequest);

        // ????????????????????????????????????-?????????????????????
        sysRoleResourceService.deleteRoleResourceListByRoleId(roleId);

        // ????????????????????????
        roleInfoCacheApi.remove(String.valueOf(roleId));

        // ?????????????????????????????????
        roleDataScopeCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleResponse update(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ?????????????????????????????????
        if (SystemConstants.SUPER_ADMIN_ROLE_CODE.equals(sysRole.getRoleCode())
                && !sysRole.getRoleCode().equals(sysRoleRequest.getRoleCode())) {
            throw new SystemModularException(SysRoleExceptionEnum.SUPER_ADMIN_ROLE_CODE_ERROR);
        }

        // ????????????
        this.converterService.copy(sysRoleRequest, sysRole);

        // ??????????????????????????????????????????????????????
        sysRole.setStatusFlag(null);

        this.getRepository().updateByPrimaryKey(sysRole);

        // ????????????????????????
        roleInfoCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
        return this.converterService.convert(sysRole, getResponseClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        sysRole.setStatusFlag(sysRoleRequest.getStatusFlag());
        this.getRepository().updateByPrimaryKey(sysRole);
    }

    @Override
    public SysRoleResponse detail(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        SysRoleResponse roleResponse = this.converterService.convert(sysRole, getResponseClass());
        // ??????????????????????????????
        roleResponse.setDataScopeTypeEnum(sysRole.getDataScopeType());

        return roleResponse;
    }

    @Override
    public PageResult<SysRoleResponse> findPage(SysRoleRequest sysRoleRequest) {

        return this.page(sysRoleRequest);
    }

    @Override
    public List<SimpleDict> findList(SysRoleRequest sysRoleParam) {
        List<SimpleDict> dictList = CollUtil.newArrayList();

        // ????????????????????? // ???????????????????????????????????????

        // ????????????????????????????????????????????????
        this.getRepository().select(getEntityClass(),
                c -> c.where(SysRoleDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
                        .and(SysRoleDynamicSqlSupport.roleName,
                                SqlBuilder.isLike(sysRoleParam.getRoleName()).filter(ObjectUtil::isNotNull),
                                SqlBuilder.or(SysRoleDynamicSqlSupport.roleCode,
                                        SqlBuilder.isLike(sysRoleParam.getRoleName()).filter(ObjectUtil::isNotNull)))
                        .orderBy(SysRoleDynamicSqlSupport.roleSort))
                .forEach(sysRole -> {
                    SimpleDict simpleDict = new SimpleDict();
                    simpleDict.setId(sysRole.getRoleId());
                    simpleDict.setName(sysRole.getRoleName() + SymbolConstant.LEFT_SQUARE_BRACKETS
                            + sysRole.getRoleCode() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
                    dictList.add(simpleDict);
                });
        return dictList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantMenu(SysRoleRequest sysRoleMenuRequest) {

        // ??????????????????????????????
        roleMenuService.deleteByRoleId(sysRoleMenuRequest.getRoleId());

        // ????????????
        CheckedKeys<Long> menuIdList = sysRoleMenuRequest.getGrantMenuIdList();
        if (ObjectUtil.isNotNull(menuIdList)) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

            // ??????ID
            Long roleId = sysRoleMenuRequest.getRoleId();

            for (Long menuId : menuIdList.getChecked()) {
                SysRoleMenu item = new SysRoleMenu();
                item.setRoleId(roleId);
                item.setMenuId(menuId);
                sysRoleMenus.add(item);
            }

            for (Long menuId : menuIdList.getHalfChecked()) {
                SysRoleMenu item = new SysRoleMenu();
                item.setRoleId(roleId);
                item.setMenuId(menuId);
                sysRoleMenus.add(item);
            }
            roleMenuService.batchCreateEntity(sysRoleMenus);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ??????????????????????????????????????????
        boolean superAdmin = LoginContext.me().getSuperAdminFlag();

        // ???????????????????????????????????????
        DataScopeTypeEnum dataScopeType = sysRoleRequest.getDataScopeType();

        // ?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {

            // ?????????????????????????????????????????????
            if (DataScopeTypeEnum.ALL == dataScopeType) {
                throw new AuthException(AuthExceptionEnum.ONLY_SUPER_ERROR);
            }

            // ?????????????????????????????????????????????????????????????????????????????????
            if (DataScopeTypeEnum.DEFINE == dataScopeType) {
                if (ObjectUtil.isEmpty(sysRoleRequest.getGrantOrgIdList())) {
                    throw new SystemModularException(SysRoleExceptionEnum.PLEASE_FILL_DATA_SCOPE);
                }
                for (Long orgId : sysRoleRequest.getGrantOrgIdList()) {
                    DataScopeUtil.quickValidateDataScope(orgId);
                }
            }
        }

        sysRole.setDataScopeType(sysRoleRequest.getDataScopeType());
        this.getRepository().updateByPrimaryKey(sysRole);

        // ??????????????????????????????
        sysRoleDataScopeService.grantDataScope(sysRoleRequest);

        // ?????????????????????????????????
        roleDataScopeCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
    }

    @Override
    public List<SimpleDict> dropDown() {
        List<SimpleDict> dictList = CollUtil.newArrayList();

        Set<Long> roleIds = new HashSet<>();
        // ??????????????????????????????????????????????????????????????????????????????
        if (!LoginContext.me().getSuperAdminFlag()) {

            // ?????????????????????
            List<SimpleRoleInfo> roles = LoginContext.me().getLoginUser().getSimpleRoleInfoList();

            // ??????????????????id
            Set<Long> loginUserRoleIds = roles.stream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toSet());
            if (ObjectUtil.isEmpty(loginUserRoleIds)) {
                return dictList;
            }
            roleIds.addAll(loginUserRoleIds);
        }

        // ?????????????????????
        SelectDSLCompleter completer = c -> c
                .where(SysRoleDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
                .and(SysRoleDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                .and(SysRoleDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIds).filter(ObjectUtil::isNotEmpty));

        this.getRepository().select(getEntityClass(), completer).forEach(sysRole -> {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysRole.getRoleId());
            simpleDict.setCode(sysRole.getRoleCode());
            simpleDict.setName(sysRole.getRoleName());
            dictList.add(simpleDict);
        });
        return dictList;
    }

    @Override
    public List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        return sysRoleDataScopeService.getRoleDataScopeIdList(CollUtil.newArrayList(sysRole.getRoleId()));
    }

    @Override
    public List<SysRoleResponse> getRolesByIds(List<Long> roleIds) {
        ArrayList<SysRoleResponse> sysRoleResponses = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysRoleRequest sysRoleRequest = new SysRoleRequest();
            sysRoleRequest.setRoleId(roleId);
            SysRoleResponse detail = this.detail(sysRoleRequest);
            sysRoleResponses.add(detail);
        }
        return sysRoleResponses;
    }

    @Override
    public List<Long> getRoleDataScopes(List<Long> roleIds) {

        ArrayList<Long> result = new ArrayList<>();

        if (ObjectUtil.isEmpty(roleIds)) {
            return result;
        }

        for (Long roleId : roleIds) {
            // ???????????????????????????
            String key = String.valueOf(roleId);
            List<Long> scopes = roleDataScopeCacheApi.get(key);
            if (scopes != null) {
                result.addAll(scopes);
            }

            SysRoleDataScopeRequest sysRoleDataScopeRequest = new SysRoleDataScopeRequest();
            sysRoleDataScopeRequest.setRoleId(roleId);
            // ??????????????????????????????
            List<SysRoleDataScopeResponse> list = this.sysRoleDataScopeService.select(sysRoleDataScopeRequest);
            if (!list.isEmpty()) {
                List<Long> realScopes = list.stream().map(SysRoleDataScopeResponse::getOrganizationId).toList();
                result.addAll(realScopes);

                // ????????????????????????
                roleDataScopeCacheApi.put(key, realScopes);
            }
        }

        return result;
    }

    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        // ???????????????????????????
        List<SysRoleMenu> roleMenus = this.roleMenuService.getRoleMenus(roleIds);
        if (roleMenus == null || roleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        return roleMenus.stream().map(SysRoleMenu::getMenuId).toList();
    }

    @Override
    public Set<String> getRoleResourceCodeList(List<Long> roleIdList) {

        HashSet<String> result = new HashSet<>();

        for (Long roleId : roleIdList) {

            // ??????????????????????????????????????????
            String key = String.valueOf(roleId);
            List<String> resourceCodesCache = roleResourceCacheApi.get(key);
            if (ObjectUtil.isNotEmpty(resourceCodesCache)) {
                result.addAll(resourceCodesCache);
                continue;
            }

            SysRoleResourceRequest sysRoleResourceRequest = new SysRoleResourceRequest();
            sysRoleResourceRequest.setRoleId(roleId);
            // ???????????????????????????????????????
            List<SysRoleResourceResponse> sysRoleResources = sysRoleResourceService.select(sysRoleResourceRequest);

            List<String> sysResourceCodes = sysRoleResources.parallelStream()
                    .map(SysRoleResourceResponse::getResourceCode).toList();
            if (ObjectUtil.isNotEmpty(sysResourceCodes)) {
                result.addAll(sysResourceCodes);
                roleResourceCacheApi.put(key, sysResourceCodes);
            }
        }

        // ???????????????????????????
        List<SysRoleMenu> list = this.roleMenuService.getRoleMenus(roleIdList);
        List<Long> menuIds = list.stream().map(SysRoleMenu::getMenuId).toList();

        // ??????????????????????????????????????????
        ArrayList<Long> businessIds = new ArrayList<>(menuIds);

        // ?????????????????????
        List<String> menuButtonResources = this.sysMenuResourceService.getResourceCodesByBusinessId(businessIds);
        result.addAll(menuButtonResources);
        return result;
    }

    @Override
    public List<SysRoleResourceResponse> getRoleResourceList(List<Long> roleIdList) {
        return sysRoleResourceService.getRoleResources(roleIdList);
    }

    @Override
    public Set<String> getRoleButtonCodes(List<Long> roleIdList) {
        /*
         * LambdaQueryWrapper<SysRoleMenuButton> queryWrapper = new
         * LambdaQueryWrapper<>(); queryWrapper.in(SysRoleMenuButton::getRoleId,
         * roleIdList); queryWrapper.select(SysRoleMenuButton::getButtonCode);
         * List<SysRoleMenuButton> list = sysRoleMenuButtonService.list(queryWrapper);
         * return
         * list.stream().map(SysRoleMenuButton::getButtonCode).collect(Collectors.toSet(
         * ));
         */

        // TODO
        return Collections.EMPTY_SET;
    }

    // TODO
    @Override
    public List<SysRoleMenuResponse> getRoleMenuList(List<Long> roleIdList) {
        List<SysRoleMenu> roleMenus = roleMenuService.getRoleMenus(roleIdList);
        return roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuResponse.class))
                .toList();
    }

    // TODO
    @Override
    public List<SysRoleMenuResponse> getRoleMenuButtonList(List<Long> roleIdList) {
        List<SysRoleMenu> roleMenus = roleMenuService.getRoleMenus(roleIdList);
        return roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuResponse.class))
                .toList();
    }

    /**
     * ??????????????????
     * @param sysRoleRequest ????????????
     * @date 2020/11/5 ??????4:12
     */
    private SysRole querySysRole(SysRoleRequest sysRoleRequest) {

        // ??????????????????????????????
        String key = String.valueOf(sysRoleRequest.getRoleId());
        SysRole sysRoleCache = roleInfoCacheApi.get(key);
        if (sysRoleCache != null) {
            return sysRoleCache;
        }

        Optional<SysRole> sysRole = this.getRepository().selectByPrimaryKey(getEntityClass(),
                sysRoleRequest.getRoleId());
        if (sysRole.isEmpty()) {
            throw new SystemModularException(SysRoleExceptionEnum.ROLE_NOT_EXIST);
        }

        // ????????????
        roleInfoCacheApi.put(key, sysRole.get());

        return sysRole.get();
    }

    @Override
    public List<ResourceTreeNode> getRoleResourceTree(Long roleId, Boolean treeBuildFlag) {

        // ?????????????????????????????????
        List<SysRoleResourceResponse> resourceList = this.getRoleResourceList(Collections.singletonList(roleId));

        // ????????????????????????
        List<String> alreadyList = new ArrayList<>();
        for (SysRoleResourceResponse sysRoleResponse : resourceList) {
            alreadyList.add(sysRoleResponse.getResourceCode());
        }

        Set<String> restrictCodes = new HashSet<>();
        LoginUserApi loginUserApi = LoginContext.me();
        if (!loginUserApi.getSuperAdminFlag()) {
            // ??????????????????
            List<Long> roleIds = loginUserApi.getLoginUser().getSimpleRoleInfoList().parallelStream()
                    .map(SimpleRoleInfo::getRoleId).toList();
            restrictCodes = this.getRoleResourceCodeList(roleIds);
        }

        return this.resourceServiceApi.getResourceList(alreadyList, restrictCodes, treeBuildFlag);
    }

}

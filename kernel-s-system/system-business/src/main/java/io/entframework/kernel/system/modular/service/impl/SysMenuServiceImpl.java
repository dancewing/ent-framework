/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import io.entframework.kernel.db.api.DbOperatorApi;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.rule.constants.TreeConstants;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import io.entframework.kernel.system.api.AppServiceApi;
import io.entframework.kernel.system.api.RoleServiceApi;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.menu.SysMenuExceptionEnum;
import io.entframework.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import io.entframework.kernel.system.api.pojo.menu.AntdMenuDTO;
import io.entframework.kernel.system.api.pojo.menu.MenuSelectTreeNode;
import io.entframework.kernel.system.api.pojo.menu.MenuTreeResponse;
import io.entframework.kernel.system.api.pojo.request.SysMenuRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.entity.SysMenuDynamicSqlSupport;
import io.entframework.kernel.system.modular.factory.MenusFactory;
import io.entframework.kernel.system.modular.service.SysMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuRequest, SysMenuResponse, SysMenu> implements SysMenuService {
    public SysMenuServiceImpl() {
        super(SysMenuRequest.class, SysMenuResponse.class, SysMenu.class);
    }

    @Resource
    private DbOperatorApi dbOperatorApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private AppServiceApi appServiceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMenuRequest sysMenuRequest) {

        if (sysMenuRequest.getMenuParentId() == null && sysMenuRequest.getAppId() == null) {
            throw new ServiceException(SysMenuExceptionEnum.PARENT_MENU_AND_APP_ALL_NULL);
        }

        // 如果父节点为空，则填充为默认的父节点id
        if (sysMenuRequest.getMenuParentId() == null) {
            sysMenuRequest.setMenuParentId(TreeConstants.DEFAULT_PARENT_ID);
        }

        // 如果父节点不为空，并且不是-1，则判断父节点存不存在，防止脏数据
        else {
            if (!TreeConstants.DEFAULT_PARENT_ID.equals(sysMenuRequest.getMenuParentId())) {
                SysMenuRequest tempParam = new SysMenuRequest();
                tempParam.setMenuId(sysMenuRequest.getMenuParentId());
                SysMenu parent = this.querySysMenu(tempParam);
                sysMenuRequest.setAppId(parent.getAppId());
            }
        }

        SysMenu sysMenu = this.converterService.convert(sysMenuRequest, getEntityClass());
        // 组装pids
        String newPids = createPids(sysMenuRequest.getMenuParentId());
        sysMenu.setMenuPids(newPids);

        // 设置启用状态
        sysMenu.setStatusFlag(StatusEnum.ENABLE);
        sysMenu.setDelFlag(YesOrNotEnum.N);

        this.getRepository().update(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(SysMenuRequest sysMenuRequest) {

        Long id = sysMenuRequest.getMenuId();

        // 获取所有子级的节点id
        Set<Long> childIdList = this.dbOperatorApi.findSubListByParentId("sys_menu", "menu_pids", "menu_id", id);
        childIdList.add(id);

        // 逻辑删除，设置删除标识
        this.getRepository().update(getEntityClass(), c -> c.set(SysMenuDynamicSqlSupport.delFlag).equalTo(YesOrNotEnum.Y)
                .where(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(childIdList)));

        // 删除该菜单下的按钮
        //sysMenuButtonService.deleteMenuButtonByMenuId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenuResponse update(SysMenuRequest sysMenuRequest) {

        // 获取库中的菜单信息
        SysMenu oldMenu = this.querySysMenu(sysMenuRequest);
        //如果选择上级菜单，appId为空的话，从上次菜单获取appId
        if (sysMenuRequest.getMenuParentId() != null && sysMenuRequest.getMenuParentId() > 0 && sysMenuRequest.getAppId() == null) {
            sysMenuRequest.setAppId(oldMenu.getAppId());
        }

        // 更新子节点以及子节点的子节点的appCode和层级关系（pids）
        String newPids = updateChildrenAppAndLevel(sysMenuRequest, oldMenu);

        // 拷贝参数到实体中
        this.converterService.copy(sysMenuRequest, oldMenu);

        // 设置新的pids
        oldMenu.setMenuPids(newPids);

        // 不能修改状态，用修改状态接口修改状态
        oldMenu.setStatusFlag(null);

        this.getRepository().updateByPrimaryKeySelective(oldMenu);
        return this.converterService.convert(oldMenu, getResponseClass());
    }

    @Override
    public SysMenuResponse detail(SysMenuRequest sysMenuRequest) {
        SysMenuResponse sysMenu = this.converterService.convert(this.querySysMenu(sysMenuRequest), getResponseClass());

        // 设置父级菜单名称
        Long menuParentId = sysMenu.getMenuParentId();
        if (TreeConstants.DEFAULT_PARENT_ID.equals(menuParentId)) {
            sysMenu.setMenuParentName("顶级");
        } else {
            Long parentId = sysMenu.getMenuParentId();
            Optional<SysMenu> parentMenu = this.getRepository().selectByPrimaryKey(getEntityClass(), parentId);
            if (!parentMenu.isPresent()) {
                sysMenu.setMenuParentName("无");
            } else {
                sysMenu.setMenuParentName(parentMenu.get().getMenuName());
            }
        }
        return sysMenu;
    }

    @Override
    public List<SysMenuResponse> findList(SysMenuRequest sysMenuRequest) {
        return this.select(sysMenuRequest);
    }

    @Override
    public List<SysMenuResponse> findListWithTreeStructure(SysMenuRequest sysMenuRequest) {

        List<SysMenuResponse> sysMenuList = this.select(sysMenuRequest);

        // 遍历菜单，设置是否是叶子节点属性
        MenusFactory.fillLeafFlag(sysMenuList);

        // 将结果集处理成树
        return new DefaultTreeBuildFactory<SysMenuResponse>().doTreeBuild(sysMenuList);
    }

    @Override
    public List<MenuSelectTreeNode> tree(SysMenuRequest sysMenuRequest) {
        List<MenuSelectTreeNode> menuTreeNodeList = CollUtil.newArrayList();

        // 添加根节点
        MenuSelectTreeNode rootNode = MenusFactory.createRootNode();
        menuTreeNodeList.add(rootNode);

        this.select(sysMenuRequest).forEach(sysMenu -> {
            MenuSelectTreeNode menuTreeNode = MenusFactory.parseMenuBaseTreeNode(sysMenu);
            menuTreeNodeList.add(menuTreeNode);
        });

        // -2是根节点的上级
        return new DefaultTreeBuildFactory<MenuSelectTreeNode>("-2").doTreeBuild(menuTreeNodeList);
    }

    @Override
    public List<AntdMenuDTO> getLeftMenus(SysMenuRequest sysMenuRequest) {

        // 不分应用查询菜单
        List<SysMenuResponse> currentUserMenus = this.getCurrentUserMenus(null);

        // 获取当前激活的应用
        SysAppResponse activeApp = appServiceApi.getActiveApp();

        // 将菜单按应用编码分类，激活的应用放在最前边
        Map<Long, List<SysMenuResponse>> sortedUserMenus = MenusFactory.sortUserMenusByAppCode(currentUserMenus);

        return MenusFactory.createTotalMenus(sortedUserMenus, activeApp.getAppId(), false);
    }

    @Override
    public List<MenuTreeResponse> getRoleMenuAndButtons(SysRoleRequest sysRoleRequest) {
        List<MenuTreeResponse> menuTreeNodeList = CollUtil.newArrayList();


        List<Long> menuWhereIdList = new ArrayList<>();
        // 非超级管理员则获取自己拥有的菜单
        if (!LoginContext.me().getSuperAdminFlag()) {
            List<Long> menuIdList = getCurrentUserMenuIds();
            menuWhereIdList.addAll(menuIdList);
        }

        // 查询所有菜单列表
        List<SysMenu> sysMenuList = this.getRepository().select(getEntityClass(), c -> c.where(SysMenuDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                .and(SysMenuDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
                .and(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(menuWhereIdList).filter(ObjectUtil::isNotEmpty)));

        // 获取菜单的id集合
        //List<Long> menuIdList = sysMenuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());

        // 获取角色绑定的菜单的id
        List<SysRoleMenuResponse> roleMenuList = roleServiceApi.getRoleMenuList(Collections.singletonList(sysRoleRequest.getRoleId()));

        // 转化 --->>> 菜单列表自转化为响应的节点类型
        List<MenuTreeResponse> menuTreeRespons = MenusFactory.parseMenuAndButtonTreeResponse(sysMenuList, roleMenuList);

        // 查询这些菜单对应的所有按钮

        // 菜单列表转化为一棵树
        return new DefaultTreeBuildFactory<MenuTreeResponse>().doTreeBuild(menuTreeRespons);
    }

    @Override
    public List<SysMenuResponse> getCurrentUserMenus(String appCode) {


        // 如果应用编码不为空，则拼接应用编码
        if (CharSequenceUtil.isNotBlank(appCode)) {
            // queryWrapper.eq(SysMenu::getAppId, appCode);
        }
        // 菜单查询条件
        SelectDSLCompleter completer = c -> c
                .where(SysMenuDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
                .and(SysMenuDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                //.and(SysMenuDynamicSqlSupport.appId, isEqualTo(appCode).filter(Objects::nonNull))
                .orderBy(SysMenuDynamicSqlSupport.menuSort.descending());

        // 如果是超级管理员，则获取所有的菜单
        if (LoginContext.me().getSuperAdminFlag()) {
            List<SysMenu> menus = this.getRepository().select(getEntityClass(), completer);
            return menus.stream().map(sysMenu -> this.converterService.convert(sysMenu, getResponseClass())).toList();
        }

        // 非超级管理员，获取当前用户所有的菜单id
        List<Long> menuIdList = getCurrentUserMenuIds();
        if (menuIdList.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_HAVE_MENUS);
        }
        completer = c -> c
                .where(SysMenuDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
                .and(SysMenuDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                .and(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(menuIdList).filter(ObjectUtil::isNotEmpty))
                //.and(SysMenuDynamicSqlSupport.appId, isEqualTo(appCode).filter(Objects::nonNull))
                .orderBy(SysMenuDynamicSqlSupport.menuSort.descending());
        List<SysMenu> menus = this.getRepository().select(getEntityClass(), completer);
        return menus.stream().map(sysMenu -> this.converterService.convert(sysMenu, getResponseClass())).toList();
    }

    /**
     * 获取系统菜单
     *
     * @date 2020/3/27 9:13
     */
    private SysMenu querySysMenu(SysMenuRequest sysMenuRequest) {
        Optional<SysMenu> sysMenuOptional = this.getRepository().selectByPrimaryKey(getEntityClass(), sysMenuRequest.getMenuId());
        if (sysMenuOptional.isEmpty()) {
            throw new SystemModularException(SysMenuExceptionEnum.MENU_NOT_EXIST, sysMenuRequest.getMenuId());
        }
        SysMenu sysMenu = sysMenuOptional.get();
        if (YesOrNotEnum.Y == sysMenu.getDelFlag()) {
            throw new SystemModularException(SysMenuExceptionEnum.MENU_NOT_EXIST, sysMenuRequest.getMenuId());
        }
        return sysMenu;
    }

    /**
     * 创建pids的值
     * <p>
     * 如果pid是0顶级节点，pids就是 [-1],
     * <p>
     * 如果pid不是顶级节点，pids就是父菜单的pids + [pid] + ,
     *
     * @date 2020/3/26 11:28
     */
    private String createPids(Long pid) {
        if (pid.equals(TreeConstants.DEFAULT_PARENT_ID)) {
            return SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
        } else {
            //获取父菜单
            SysMenuRequest sysMenuRequest = new SysMenuRequest();
            sysMenuRequest.setMenuId(pid);
            SysMenu parentMenu = this.querySysMenu(sysMenuRequest);

            // 组装pids
            return parentMenu.getMenuPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + pid + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
        }
    }

    /**
     * 获取当前用户的菜单id集合
     *
     * @date 2020/11/22 23:15
     */
    private List<Long> getCurrentUserMenuIds() {

        // 获取当前用户的角色id集合
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<Long> roleIdList = loginUser.getSimpleRoleInfoList().stream().map(SimpleRoleInfo::getRoleId).toList();

        // 当前用户角色为空，则没菜单
        if (ObjectUtil.isEmpty(roleIdList)) {
            return CollUtil.newArrayList();
        }

        // 获取角色拥有的菜单id集合
        List<Long> menuIdList = roleServiceApi.getMenuIdsByRoleIds(roleIdList);
        if (ObjectUtil.isEmpty(menuIdList)) {
            return CollUtil.newArrayList();
        }

        return menuIdList;
    }

    /**
     * 更新子节点以及子节点的子节点的appCode和层级关系（pids）
     *
     * @date 2020/11/23 22:05
     */
    private String updateChildrenAppAndLevel(SysMenuRequest sysMenuRequest, SysMenu oldMenu) {

        // 本菜单旧的pids
        Long oldPid = oldMenu.getMenuParentId();
        String oldPids = oldMenu.getMenuPids();

        // 生成新的pid和pids
        Long newPid = sysMenuRequest.getMenuParentId();
        String newPids = this.createPids(sysMenuRequest.getMenuParentId());

        // 是否更新子应用的标识
        boolean updateSubAppsFlag = false;

        // 是否更新子节点的pids的标识
        boolean updateSubPidsFlag = false;

        // 如果应用有变化，不能把非一级菜单转移应用
        if (!sysMenuRequest.getAppId().equals(oldMenu.getAppId())) {
            if (!oldPid.equals(TreeConstants.DEFAULT_PARENT_ID)) {
                throw new ServiceException(SysMenuExceptionEnum.CANT_MOVE_APP);
            }
            updateSubAppsFlag = true;
        }

        // 父节点有变化
        if (!newPid.equals(oldPid)) {
            updateSubPidsFlag = true;
        }

        // 开始更新所有子节点的配置
        if (updateSubAppsFlag || updateSubPidsFlag) {

            // 查找所有叶子节点，包含子节点的子节点
//            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.like(SysMenu::getMenuPids, oldMenu.getMenuId());
            SelectDSLCompleter dslCompleter = c -> c.where(SysMenuDynamicSqlSupport.menuPids, SqlBuilder.isLike(String.valueOf(oldMenu.getMenuId())));

            List<SysMenu> list = this.getRepository().select(getEntityClass(), dslCompleter);

            // 更新所有子节点的应用为当前菜单的应用
            if (ObjectUtil.isNotEmpty(list)) {

                // 更新所有子节点的application
                if (updateSubAppsFlag) {
                    list.forEach(child -> child.setAppId(sysMenuRequest.getAppId()));
                }

                // 更新所有子节点的pids
                if (updateSubPidsFlag) {
                    list.forEach(child -> {
                        // 子节点pids组成 = 当前菜单新pids + 当前菜单id + 子节点自己的pids后缀
                        String oldParentCodesPrefix = oldPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                        String oldParentCodesSuffix = child.getMenuPids().substring(oldParentCodesPrefix.length());
                        String menuParentCodes = newPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA + oldParentCodesSuffix;
                        child.setMenuPids(menuParentCodes);
                    });
                }

                list.forEach(this.getRepository()::updateByPrimaryKey);
            }
        }
        return newPids;
    }

    public List<SysMenu> getMenuStatInfoByMenuIds(List<Long> menuIds) {
        if (ObjectUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        return this.getRepository().select(getEntityClass(), c -> c.where(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(menuIds)).orderBy(SysMenuDynamicSqlSupport.menuId.descending()));
    }

    @Override
    public SortSpecification defaultOrderBy() {
        return SimpleSortSpecification.of("menu_sort").descending();
    }
}
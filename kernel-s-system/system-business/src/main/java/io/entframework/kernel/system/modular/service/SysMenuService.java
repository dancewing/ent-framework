/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.menu.AntdMenuDTO;
import io.entframework.kernel.system.api.pojo.menu.MenuSelectTreeNode;
import io.entframework.kernel.system.api.pojo.menu.MenuTreeResponse;
import io.entframework.kernel.system.api.pojo.request.SysMenuRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.modular.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends BaseService<SysMenuRequest, SysMenuResponse, SysMenu> {

    /**
     * 添加系统菜单
     * @param sysMenuRequest 添加参数
     * @date 2020/3/27 9:03
     */
    void add(SysMenuRequest sysMenuRequest);

    /**
     * 删除系统菜单
     * @param sysMenuRequest 删除参数
     * @date 2020/3/27 9:03
     */
    void del(SysMenuRequest sysMenuRequest);

    /**
     * 编辑系统菜单
     * @param sysMenuRequest 编辑参数
     * @date 2020/3/27 9:03
     */
    SysMenuResponse update(SysMenuRequest sysMenuRequest);

    /**
     * 查看系统菜单
     * @param sysMenuRequest 查看参数
     * @return 系统菜单
     * @date 2020/3/27 9:03
     */
    SysMenuResponse detail(SysMenuRequest sysMenuRequest);

    /**
     * 系统菜单列表，树形结构，用于菜单管理界面的列表展示
     * @param sysMenuRequest 查询参数
     * @return 菜单树表列表
     * @date 2020/3/26 10:19
     */
    List<SysMenuResponse> findList(SysMenuRequest sysMenuRequest);

    /**
     * 获取菜单列表（layui版本）
     *
     * @date 2021/1/6 17:10
     */
    List<SysMenuResponse> findListWithTreeStructure(SysMenuRequest sysMenuRequest);

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点（antd vue版本，用在新增和编辑菜单选择上级菜单）
     * @param sysMenuRequest 查询参数
     * @return 菜单树列表
     * @date 2020/3/27 15:56
     */
    List<MenuSelectTreeNode> tree(SysMenuRequest sysMenuRequest);

    /**
     * 获取系统所有菜单（适用于登录后获取左侧菜单）（适配antd vue版本）
     * @return 系统所有菜单信息
     * @date 2021/1/7 15:24
     */
    List<AntdMenuDTO> getLeftMenus(SysMenuRequest sysMenuRequest);

    /**
     * 获取角色绑定菜单和按钮权限的树
     *
     * @date 2021/8/10 22:23
     */
    List<MenuTreeResponse> getRoleMenuAndButtons(SysRoleRequest sysRoleRequest);

    /**
     * 获取当前用户的某个应用下的菜单
     * @param appCode 应用编码
     * @date 2020/12/27 18:11
     */
    List<SysMenuResponse> getCurrentUserMenus(String appCode);

    List<SysMenu> getMenuStatInfoByMenuIds(List<Long> menuIds);

}
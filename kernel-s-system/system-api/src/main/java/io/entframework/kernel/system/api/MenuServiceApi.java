/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api;

import java.util.List;
import java.util.Set;

/**
 * 菜单api
 *
 * @date 2020/11/24 21:37
 */
public interface MenuServiceApi {

    /**
     * 根据应用id判断该机构下是否有状态为正常的菜单
     *
     * @param appId 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @date 2020/11/24 21:37
     */
    boolean hasMenu(Long appId);

    /**
     * 获取当前用户所拥有菜单对应的appId列表
     *
     * @date 2021/4/21 15:40
     */
    List<Long> getUserAppIdList();

    /**
     * 获取菜单所有的父级菜单ID
     *
     * @param menuIds 菜单列表
     * @return {@link java.util.Set<java.lang.Long>}
     * @date 2021/6/22 上午10:11
     **/
    Set<Long> getMenuAllParentMenuId(Set<Long> menuIds);
}

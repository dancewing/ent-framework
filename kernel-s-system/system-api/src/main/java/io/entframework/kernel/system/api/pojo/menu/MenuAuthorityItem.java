/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.menu;

import lombok.Data;

import java.util.List;

/**
 * 菜单被哪个权限绑定的详情
 *
 * @date 2021/1/7 18:16
 */
@Data
public class MenuAuthorityItem {

    /**
     * 权限编码
     */
    private List<String> permission;

    /**
     * 角色编码
     */
    private List<String> role;

}

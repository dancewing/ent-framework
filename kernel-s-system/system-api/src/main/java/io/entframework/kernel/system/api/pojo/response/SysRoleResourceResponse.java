/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色资源关联
 *
 * @date 2020/11/5 下午4:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleResourceResponse extends BaseEntity {

    /**
     * 主键
     */
    private Long roleResourceId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源编码
     */
    private String resourceCode;

}

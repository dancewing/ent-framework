/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表
 *
 * @author jeff_qian
 * @date 2020/11/24 21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAppResponse extends BaseEntity {

    /**
     * 主键id
     */
    @ChineseDescription("主键id")
    private Long appId;

    /**
     * 应用名称
     */
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 编码
     */
    @ChineseDescription("编码")
    private String appCode;

    /**
     * 应用图标
     */
    @ChineseDescription("应用图标")
    private String appIcon;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @ChineseDescription("是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开")
    private YesOrNotEnum activeFlag;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    private StatusEnum statusFlag;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @ChineseDescription("是否删除：Y-已删除，N-未删除")
    private YesOrNotEnum delFlag;

    /**
     * 排序-升序
     */
    @ChineseDescription("排序-升序")
    private Integer appSort;

}

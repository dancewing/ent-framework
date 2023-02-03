/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.validator.api.validators.status.StatusValue;
import io.entframework.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 系统应用参数
 *
 * @date 2020/3/24 20:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAppRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "appId不能为空", groups = {update.class, delete.class, detail.class, updateActiveFlag.class, updateStatus.class})
    @ChineseDescription("主键")
    private Long appId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, update.class})
    @TableUniqueValue(
            message = "名称存在重复",
            groups = {add.class, update.class},
            tableName = "sys_app",
            columnName = "app_name",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("名称不能为空")
    private String appName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, update.class})
    @TableUniqueValue(
            message = "编码存在重复",
            groups = {add.class, update.class},
            tableName = "sys_app",
            columnName = "app_code",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("应用编码")
    private String appCode;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, update.class})
    @TableUniqueValue(
            message = "编码存在重复",
            groups = {add.class, update.class},
            tableName = "sys_app",
            columnName = "entry_path",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("入口路径")
    private String entryPath;

    /**
     * 应用图标
     */
    @NotBlank(message = "应用图标不能为空", groups = {add.class, update.class})
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
    @NotNull(message = "状态为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("状态：1-启用，2-禁用")
    private StatusEnum statusFlag;

    /**
     * 排序-升序
     */
    @ChineseDescription("排序-升序")
    private Integer appSort;


    /**
     * 设置为默认状态
     */
    public @interface updateActiveFlag {
    }

}

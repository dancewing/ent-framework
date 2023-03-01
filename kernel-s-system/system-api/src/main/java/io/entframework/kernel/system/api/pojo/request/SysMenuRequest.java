/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.system.api.enums.LinkOpenTypeEnum;
import io.entframework.kernel.system.api.enums.MenuTypeEnum;
import io.entframework.kernel.validator.api.validators.flag.FlagValue;
import io.entframework.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 系统菜单参数
 *
 * @date 2020/3/26 20:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "menuId不能为空", groups = { update.class, delete.class, detail.class })
    @ChineseDescription("主键")
    private Long menuId;

    /**
     * 父id
     */
    // @NotNull(message = "menuParentId不能为空", groups = {add.class, update.class})
    @ChineseDescription("父id")
    private Long menuParentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空", groups = { add.class, update.class })
    @TableUniqueValue(message = "菜单名称存在重复", groups = { add.class, update.class }, tableName = "sys_menu",
            columnName = "menu_name", idFieldName = "menu_id", excludeLogicDeleteItems = true)
    @ChineseDescription("菜单名称")
    private String menuName;

    @ChineseDescription("菜单类型")
    private MenuTypeEnum menuType;

    /**
     * 菜单的编码
     */
    @NotBlank(message = "菜单的编码不能为空", groups = { add.class, update.class })
    @TableUniqueValue(message = "菜单的编码存在重复", groups = { add.class, update.class }, tableName = "sys_menu",
            columnName = "menu_code", idFieldName = "menu_id", excludeLogicDeleteItems = true)
    @ChineseDescription("菜单的编码")
    private String menuCode;

    /**
     * 所属应用
     */
    // @NotNull(message = "appId不能为空", groups = {add.class, update.class,
    // getAppMenusAntdVue.class})
    @ChineseDescription("应用分类（应用编码）")
    private Long appId;

    /**
     * 是否可见（Y-是，N-否）
     */
    @FlagValue(message = "是否可见格式错误，正确格式应该Y或者N", groups = { add.class, update.class }, required = false)
    @ChineseDescription("是否可见（Y-是，N-否）")
    private YesOrNotEnum visible;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { add.class, update.class })
    @ChineseDescription("排序")
    private BigDecimal menuSort;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 路由地址，浏览器显示的URL，例如/menu
     */
    @ChineseDescription("路由地址，浏览器显示的URL，例如/menu")
    private String router;

    /**
     * 图标
     */
    @ChineseDescription("图标")
    private String icon;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链（适用于antd vue版本）
     */
    @ChineseDescription("外部链接打开方式：1-内置打开外链，2-新页面外链")
    private LinkOpenTypeEnum linkOpenType;

    /**
     * 外部链接地址（适用于antd vue版本）
     */
    @ChineseDescription("外部链接地址")
    private String linkUrl;

    /**
     * 获取主页左侧菜单列表（适配Antd Vue的版本）
     */
    public @interface getAppMenusAntdVue {

    }

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.theme;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统主题模板属性参数
 *
 * @date 2021/12/17 10:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysThemeTemplateFieldRequest extends BaseRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { update.class, delete.class, detail.class })
    @ChineseDescription("主键ID")
    private Long fieldId;

    /**
     * 模板ID
     */
    @ChineseDescription("模板ID")
    private Long templateId;

    /**
     * 属性名称
     */
    @NotBlank(message = "属性名称不能为空", groups = { add.class, update.class })
    @ChineseDescription("属性名称")
    private String fieldName;

    @NotNull(message = "属性编码不能为空", groups = { add.class, update.class })
    @ChineseDescription("属性编码")
    private String fieldCode;

    /**
     * 属性展示类型(字典维护)
     */
    @NotBlank(message = "属性展示类型不能为空", groups = { add.class, update.class })
    @ChineseDescription("属性展示类型")
    private ThemeFieldTypeEnum fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @ChineseDescription("是否必填")
    private YesOrNotEnum fieldRequired;

    /**
     * 属性长度
     */
    @ChineseDescription("属性长度")
    private Integer fieldLength;

    /**
     * 属性描述
     */
    @ChineseDescription("属性描述")
    private String fieldDescription;

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.pojo.request;

import java.math.BigDecimal;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.validator.api.validators.status.StatusValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 字典 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysDictRequest extends BaseRequest {

    /**
     * 字典id
     */
    @NotNull(message = "id不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
    @ChineseDescription("字典id")
    private Long dictId;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空", groups = { add.class, update.class, validateAvailable.class })
    @ChineseDescription("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = { add.class, update.class })
    @ChineseDescription("字典名称")
    private String dictName;

    /**
     * 字典名称拼音
     */
    @ChineseDescription("字典名称拼音")
    private String dictNamePinYin;

    /**
     * 字典编码
     */
    @ChineseDescription("字典编码")
    private String dictEncode;

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空", groups = { add.class, update.class, treeList.class, dictZTree.class })
    @ChineseDescription("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典简称
     */
    @ChineseDescription("字典简称")
    private String dictShortName;

    /**
     * 字典简称的编码
     */
    @ChineseDescription("字典简称的编码")
    private String dictShortCode;

    /**
     * 上级字典的id
     * <p>
     * 字典列表是可以有树形结构的，但是字典类型没有树形结构
     * <p>
     * 如果没有上级字典id，则为-1
     */
    @ChineseDescription("上级字典的id")
    private Long dictParentId;

    /**
     * 状态(1:启用,2:禁用)，参考 StatusEnum
     */
    @NotNull(message = "状态不能为空", groups = { updateStatus.class })
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("状态")
    private StatusEnum statusFlag;

    /**
     * 排序，带小数点
     */
    @ChineseDescription("排序")
    @NotNull(message = "排序不能为空", groups = { add.class, update.class })
    private BigDecimal dictSort;

    /**
     * 所有的父级id,逗号分隔
     */
    @ChineseDescription("所有的父级id")
    private String dictPids;

    /**
     * 字典类型id，用在作为查询条件
     */
    @ChineseDescription("字典类型id")
    private Long dictTypeId;

    /**
     * 获取树形列表
     */
    public @interface treeList {

    }

    /**
     * 校验编码是否重复
     */
    public @interface validateAvailable {

    }

    /**
     * 校验ztree必备参数
     */
    public @interface dictZTree {

    }

}
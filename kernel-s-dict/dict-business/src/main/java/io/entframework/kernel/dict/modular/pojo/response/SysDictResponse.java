/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 字典 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysDictResponse extends BaseResponse {

    /**
     * 字典id
     */
    @ChineseDescription("字典id")
    private Long dictId;

    /**
     * 字典编码
     */
    @ChineseDescription("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ChineseDescription("字典名称")
    private String dictName;

    /**
     * 字典名称首字母
     */
    @ChineseDescription("字典名称首字母")
    private String dictNamePinyin;

    /**
     * 字典编码
     */
    @ChineseDescription("字典编码")
    private String dictEncode;

    /**
     * 字典类型的编码
     */
    @ChineseDescription("字典类型的编码")
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
     * 上级字典的id(如果没有上级字典id，则为-1)
     */
    @ChineseDescription("上级字典的id(如果没有上级字典id，则为-1)")
    private Long dictParentId;

    /**
     * 状态：(1-启用,2-禁用),参考 StatusEnum
     */
    @ChineseDescription("状态：(1-启用,2-禁用),参考 StatusEnum")
    private StatusEnum statusFlag;

    /**
     * 排序，带小数点
     */
    @ChineseDescription("排序，带小数点")
    private BigDecimal dictSort;

    /**
     * 父id集合
     */
    @ChineseDescription("父id集合")
    private String dictPids;

    /**
     * 是否删除，Y-被删除，N-未删除
     */
    @ChineseDescription("是否删除，Y-被删除，N-未删除")
    private YesOrNotEnum delFlag;

}
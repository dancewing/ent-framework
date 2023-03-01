/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.pojo.request;

import io.entframework.kernel.dict.api.enums.DictTypeClassEnum;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 字典类型 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysDictTypeRequest extends BaseRequest {

    /**
     * 字典类型id
     */
    @NotBlank(message = "字典类型id不能为空", groups = { add.class, update.class })
    @ChineseDescription("字典类型id")
    private Long dictTypeId;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @ChineseDescription("字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum")
    private DictTypeClassEnum dictTypeClass;

    /**
     * 字典类型业务编码
     */
    @ChineseDescription("字典类型业务编码")
    private String dictTypeBusCode;

    /**
     * 字典类型编码
     */
    @ChineseDescription("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @ChineseDescription("字典类型名称")
    private String dictTypeName;

    /**
     * 字典类型名称首字母拼音
     */
    @ChineseDescription("字典类型名称首字母拼音")
    private String dictTypeNamePinyin;

    /**
     * 字典类型描述
     */
    @ChineseDescription("字典类型描述")
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @ChineseDescription("字典类型的状态：1-启用，2-禁用，参考 StatusEnum")
    private StatusEnum statusFlag;

    /**
     * 排序，带小数点
     */
    @ChineseDescription("排序，带小数点")
    private BigDecimal dictTypeSort;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @ChineseDescription("是否删除：Y-被删除，N-未删除")
    private YesOrNotEnum delFlag;

}
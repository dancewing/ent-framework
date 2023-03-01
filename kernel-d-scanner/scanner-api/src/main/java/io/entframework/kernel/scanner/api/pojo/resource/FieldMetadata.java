/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.pojo.resource;

import com.alibaba.fastjson.annotation.JSONField;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 字典描述类
 *
 * @date 2020/12/8 18:25
 */
@Data
public class FieldMetadata implements Serializable {

    /**
     * 生成给前端用
     * <p>
     * uuid，标识一个字段的唯一性
     */
    @JSONField(serialize = false)
    @ChineseDescription("生成给前端用")
    private String metadataId;

    /**
     * 字段中文名称，例如：创建用户
     */
    @ChineseDescription("字段中文名称")
    private String chineseName;

    /**
     * 字段类型，例如：String
     */
    @ChineseDescription("字段类型")
    private String fieldClassType;

    /**
     * 字段类型路径，例如：java.long.String
     */
    @ChineseDescription("字段类型路径")
    private String fieldClassPath;

    /**
     * 字段名称，例如：createUser
     */
    @ChineseDescription("字段名称")
    private String fieldName;

    /**
     * 字段的注解，例如：NotBlack，NotNull
     */
    @ChineseDescription("字段的注解")
    private Set<String> annotations;

    /**
     * 按校验组分的注解集合
     * <p>
     * 例如： key = add, value = [不能为空，最大多少位，邮箱类型]
     */
    @ChineseDescription("按校验组分的注解集合")
    private Map<String, Set<String>> groupValidationMessage;

    /**
     * 校验信息的提示信息
     */
    @ChineseDescription("校验信息的提示信息")
    private String validationMessages;

    /**
     * 泛型或object类型的字段的描述类型(1-字段，2-泛型)
     */
    @ChineseDescription("泛型或object类型的字段的描述(1-字段，2-泛型)")
    private Integer genericFieldMetadataType;

    /**
     * 字段类型：详情在 FieldTypeEnum
     */
    @ChineseDescription("字段类型：详情在 FieldTypeEnum")
    private Integer fieldType;

    /**
     * 请求参数传值类型，详情在 ParamTypeEnum
     */
    @ChineseDescription("请求参数传值类型，详情在 ParamTypeEnum")
    private Integer requestParamType;

    /**
     * 泛型或object类型的字段的描述
     */
    @ChineseDescription("泛型或object类型的字段的描述")
    private Set<FieldMetadata> genericFieldMetadata;

}

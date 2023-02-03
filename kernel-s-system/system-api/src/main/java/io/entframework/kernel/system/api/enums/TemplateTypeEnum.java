/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 字段类型枚举
 *
 * @date 2022/1/1 22:29
 */
@Getter
@EnumHandler
public enum TemplateTypeEnum implements SupperEnum<Integer> {

    /**
     * 字符串类型
     */
    STRING(1, "系统类型"),

    /**
     * 文件类型
     */
    FILE(2, "业务类型");

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String label;

    TemplateTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
    @JsonCreator
    public static TemplateTypeEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(TemplateTypeEnum.class, value);
    }
}

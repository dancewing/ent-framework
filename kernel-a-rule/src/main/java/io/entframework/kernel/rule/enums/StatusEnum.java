/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 公共状态，一般用来表示开启和关闭
 *
 * @date 2020/10/14 21:31
 */
@Getter
@EnumHandler
public enum StatusEnum implements SupperEnum<Integer> {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用");

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据code获取枚举
     *
     * @date 2020/10/29 18:59
     */
    @JsonCreator
    public static StatusEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(StatusEnum.class, value);
    }

}

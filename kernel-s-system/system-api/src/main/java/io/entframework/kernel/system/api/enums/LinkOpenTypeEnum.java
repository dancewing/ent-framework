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
 * 菜单链接打开方式
 *
 * @date 2020/11/23 21:30
 */
@Getter
@EnumHandler
public enum LinkOpenTypeEnum implements SupperEnum<Integer> {

    /**
     * 内部链接（内置iframe页面打开链接）
     */
    COMPONENT(1, "内链"),

    /**
     * 外部链接方式打开链接，新标签页打开
     */
    INNER(2, "外链");

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String label;

    LinkOpenTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static LinkOpenTypeEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(LinkOpenTypeEnum.class, value);
    }

}

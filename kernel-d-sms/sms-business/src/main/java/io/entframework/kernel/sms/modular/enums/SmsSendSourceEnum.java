/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.modular.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 短信发送业务枚举
 *
 * @date 2020/10/26 21:29
 */
@Getter
@EnumHandler
public enum SmsSendSourceEnum implements SupperEnum<Integer> {

    /**
     * APP
     */
    APP(1, "APP"),

    /**
     * PC
     */
    PC(2, "PC"),

    /**
     * OTHER
     */
    OTHER(3, "OTHER");

    @JsonValue
    @EnumValue
    private final Integer value;
    private final String label;

    SmsSendSourceEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static SmsSendSourceEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(SmsSendSourceEnum.class, value);
    }
}

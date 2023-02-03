/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息阅读状态
 *
 * @date 2021/1/4 22:26
 */
@Getter
@EnumHandler
public enum MessageReadFlagEnum {

    /**
     * 未读
     */
    UNREAD(0, "未读"),

    /**
     * 已读
     */
    READ(1, "已读");

    @JsonValue
    @EnumValue
    private final Integer code;

    private final String name;

    MessageReadFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        if (code == null) {
            return null;
        }
        for (MessageReadFlagEnum flagEnum : MessageReadFlagEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum.name;
            }
        }
        return null;
    }

}

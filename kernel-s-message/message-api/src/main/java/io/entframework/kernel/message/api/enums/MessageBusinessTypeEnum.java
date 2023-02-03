/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息业务类型枚举
 *
 * @date 2021/1/4 22:26
 */
@Getter
@EnumHandler
public enum MessageBusinessTypeEnum {

    /**
     * 已读
     */
    SYS_NOTICE("sys_notice", "通知", "/sysNotice/detail");
    @JsonValue
    @EnumValue
    private final String code;

    private final String name;

    private final String url;


    MessageBusinessTypeEnum(String code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public static MessageBusinessTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (MessageBusinessTypeEnum flagEnum : MessageBusinessTypeEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum;
            }
        }
        return null;
    }

    public static String getName(String code) {
        if (code == null) {
            return null;
        }
        for (MessageBusinessTypeEnum flagEnum : MessageBusinessTypeEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum.name;
            }
        }
        return null;
    }

}

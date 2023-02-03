/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.timer.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 定时任务的状态
 *
 * @date 2020/6/30 20:44
 */
@Getter
@EnumHandler
public enum TimerJobStatusEnum implements SupperEnum<Integer> {

    /**
     * 启动状态
     */
    RUNNING(1, "RUNNING"),

    /**
     * 停止状态
     */
    STOP(2, "STOP");

    @JsonValue
    @EnumValue
    private final Integer value;
    private final String label;

    TimerJobStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static TimerJobStatusEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(TimerJobStatusEnum.class, value);
    }
}

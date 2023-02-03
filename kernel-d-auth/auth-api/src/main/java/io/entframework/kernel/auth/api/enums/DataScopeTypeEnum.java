/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据范围类型枚举，数据范围的值越小，数据权限越小
 *
 * @date 2020/11/5 15:22
 */
@Getter
@EnumHandler
public enum DataScopeTypeEnum implements SupperEnum<Integer> {

    /**
     * 仅本人数据
     */
    SELF(10, "仅本人数据"),

    /**
     * 本部门数据
     */
    DEPT(20, "本部门数据"),

    /**
     * 本部门及以下数据
     */
    DEPT_WITH_CHILD(30, "本部门及以下数据"),

    /**
     * 指定部门数据
     */
    DEFINE(40, "指定部门数据"),

    /**
     * 全部数据
     */
    ALL(50, "全部数据");

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String label;

    DataScopeTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据code获取枚举
     *
     * @date 2020/10/29 18:59
     */
    @JsonCreator
    public static DataScopeTypeEnum codeToEnum(Integer code) {
        if (null != code) {
            return SupperEnum.fromValue(DataScopeTypeEnum.class, code);
        }
        return null;
    }

}

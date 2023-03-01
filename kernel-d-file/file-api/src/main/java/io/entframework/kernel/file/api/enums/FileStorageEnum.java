/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文件存储类型
 *
 * @author jeff_qian
 * @date 2020/6/7 22:24
 */
@Getter
@EnumHandler
public enum FileStorageEnum implements SupperEnum<Integer> {

    /**
     * 阿里云
     */
    ALIYUN(1, "阿里云"),

    /**
     * 腾讯云
     */
    TENCENT(2, "腾讯云"),

    /**
     * minio服务器
     */
    MINIO(3, "Minio"),

    /**
     * 本地
     */
    LOCAL(4, "Local"),

    /**
     * 数据库中
     */
    DB(5, "DB");

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String label;

    FileStorageEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static FileStorageEnum getStorage(Integer code) {
        return SupperEnum.fromValue(FileStorageEnum.class, code);
    }

}

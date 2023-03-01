/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.exception.enums;

import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 文件操作相关的异常枚举
 *
 * @date 2020/10/26 11:29
 */
@Getter
public enum FileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 附件IDS为空
     */
    FILE_IDS_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "01",
            "附件IDS为空!"),

    /**
     * 下载的文件中包含私有文件
     */
    SECRET_FLAG_INFO_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "02",
            "下载的文件中包含私有文件，具体文件为：{}"),

    /**
     * 阿里云文件操作异常
     */
    ALIYUN_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "03",
            "阿里云文件操作异常，具体信息为：{}"),

    /**
     * 腾讯云文件操作异常
     */
    TENCENT_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "04",
            "腾讯云文件操作异常，具体信息为：{}"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "05",
            "本地文件不存在，具体信息为：{}"),

    /**
     * MinIO文件操作异常
     */
    MINIO_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "06",
            "MinIO文件操作异常，具体信息为：{}"),

    /**
     * 上传文件操作异常
     */
    ERROR_FILE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "07",
            "上传文件操作异常，具体信息为：{}"),

    /**
     * 该条文件信息记录不存在
     */
    NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "08",
            "该条文件信息记录不存在，文件id为：{}"),

    /**
     * 获取文件流错误
     */
    FILE_STREAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "09",
            "获取文件流异常，具体信息为：{}"),

    /**
     * 下载文件错误
     */
    DOWNLOAD_FILE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "10",
            "下载文件错误，具体信息为：{}"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_NOT_SUPPORT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "11",
            "预览文件异常，您预览的文件类型不支持或文件出现错误"),

    /**
     * 预览文件参数存在空值
     */
    PREVIEW_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "12",
            "预览文件参数存在空值，请求参数为：{}"),

    /**
     * 渲染文件流字节出错
     */
    WRITE_BYTES_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "13",
            "渲染文件流字节出错，具体信息为：{}"),

    /**
     * 文件id不能为空
     */
    FILE_ID_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "14",
            "文件ID不能为空!"),

    /**
     * 文件Code不能为空
     */
    FILE_CODE_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "15",
            "文件CODE不能为空!"),

    /**
     * 文件不允许被访问
     */
    FILE_DENIED_ACCESS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16",
            "文件不允许被访问，文件加密等级不符合"),

    /**
     * 文件不允许被访问
     */
    FILE_PERMISSION_DENIED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "17",
            "文件不允许被访问，请登录后访问"),

    /**
     * 文件存储类型没有找到实现
     */
    FILE_STORAGE_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "18",
            "文件存储类型{}没有找到对应的API实现"),

    /**
     * 文件存储类型不能为空
     */
    FILE_STORAGE_TYPE_NOT_NULL(
            RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "19",
            "API的文件存储类型不能为null");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FileExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.api.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件存储信息 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysFileStorageResponse extends BaseResponse {
    /**
     * 文件主键id，关联file_info表的主键
     */
    @ChineseDescription("文件主键id，关联file_info表的主键")
    private Long fileId;

    /**
     * 具体文件的字节信息
     */
    @ChineseDescription("具体文件的字节信息")
    private byte[] fileBytes;
}
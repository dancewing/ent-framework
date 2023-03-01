/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.file.modular.factory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.web.multipart.MultipartFile;

import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.enums.FileStatusEnum;
import io.entframework.kernel.file.api.exception.FileException;
import io.entframework.kernel.file.api.exception.enums.FileExceptionEnum;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.modular.entity.SysFileInfo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;

/**
 * 文件信息组装工厂
 *
 * @date 2020/12/30 22:16
 */
public class FileInfoFactory {

    /**
     * 封装附件信息
     *
     * @date 2020/12/27 12:55
     */
    public static SysFileInfo createSysFileInfo(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        FileOperatorFactory fileOperatorFactory = SpringUtil.getBean(FileOperatorFactory.class);

        FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfoRequest.getFileLocation());

        // 生成文件的唯一id
        Long fileId = IdWorker.getId();

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀（不包含点）
        String fileSuffix = null;
        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = CharSequenceUtil.subAfter(originalFilename, FileConstants.FILE_POSTFIX_SEPARATOR, true);
        }

        // 生成文件的最终名称
        String finalFileName = fileId + FileConstants.FILE_POSTFIX_SEPARATOR + fileSuffix;

        // 桶名
        String fileBucket = sysFileInfoRequest.getFileBucket();

        // 存储文件
        byte[] bytes;
        try {
            bytes = file.getBytes();
            if (CharSequenceUtil.isEmpty(fileBucket)) {
                fileBucket = fileOperatorApi.getBucket(fileId);
            }
            fileOperatorApi.storageFile(fileBucket, finalFileName, bytes);
        }
        catch (IOException e) {
            throw new FileException(FileExceptionEnum.ERROR_FILE, e.getMessage());
        }

        // 计算文件大小kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024))
                .setScale(0, RoundingMode.HALF_UP));

        // 计算文件大小信息
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // 封装存储文件信息（上传替换公共信息）
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(fileId);
        sysFileInfo.setFileLocation(fileOperatorApi.getFileLocationEnum());
        sysFileInfo.setFileBucket(fileBucket);
        sysFileInfo.setFileObjectName(finalFileName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        sysFileInfo.setFileStatus(FileStatusEnum.NEW);
        sysFileInfo.setSecretFlag(sysFileInfoRequest.getSecretFlag());

        // 返回结果
        return sysFileInfo;
    }

}

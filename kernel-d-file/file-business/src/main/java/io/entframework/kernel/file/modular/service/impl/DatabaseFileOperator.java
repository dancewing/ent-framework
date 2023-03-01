/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.modular.service.impl;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.enums.BucketAuthEnum;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.file.modular.service.SysFileStorageService;
import jakarta.annotation.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库的文件存储实现 bucket即时文件的fileId
 *
 * @author jeff_qian
 */
public class DatabaseFileOperator implements FileOperatorApi {

    @Resource
    private SysFileStorageService sysFileStorageService;

    @Override
    public String getBucket(Long fileId) {
        return String.valueOf(fileId);
    }

    @Override
    public void initClient() {
        // Database client don't need to init client
    }

    @Override
    public void destroyClient() {
        // Database client don't need to destroy client
    }

    @Override
    public Object getClient() {
        return null;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return false;
    }

    @Override
    public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
        // Don't have this operation in database client
    }

    @Override
    public boolean isExistingFile(String bucketName, String key) {
        return false;
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {
        this.sysFileStorageService.saveFile(Long.parseLong(bucketName), bytes);
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {
        // Don't have this operation in database client
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {
        return new byte[0];
    }

    @Override
    public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
        // Don't have this operation in database client
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {
        // Don't have this operation in database client
    }

    @Override
    public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis, String token) {
        return this.sysFileStorageService.getFileAuthUrl(bucketName, token);
    }

    @Override
    public String getFileUnAuthUrl(String bucketName, String key) {
        return this.sysFileStorageService.getFileUnAuthUrl(bucketName);
    }

    @Override
    public void deleteFile(String bucketName, String key) {
        // Don't have this operation in database client
    }

    @Override
    public FileStorageEnum getFileLocationEnum() {
        return FileStorageEnum.DB;
    }

    @Override
    public void close() throws IOException {
        // Don't have this operation in database client
    }

}

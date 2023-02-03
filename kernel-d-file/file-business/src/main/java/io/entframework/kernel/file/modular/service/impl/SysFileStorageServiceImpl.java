/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.modular.service.impl;


import io.entframework.kernel.db.mds.repository.BaseRepository;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.pojo.request.SysFileStorageRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileStorageResponse;
import io.entframework.kernel.file.modular.entity.SysFileStorage;
import io.entframework.kernel.file.modular.service.SysFileStorageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件存储信息业务实现层
 *
 * @date 2022/01/08 15:53
 */
public class SysFileStorageServiceImpl extends BaseServiceImpl<SysFileStorageRequest, SysFileStorageResponse, SysFileStorage> implements SysFileStorageService {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Resource
    private FileServerProperties fileServerProperties;

    public SysFileStorageServiceImpl(BaseRepository<SysFileStorage> baseRepository) {
        super(baseRepository, SysFileStorageRequest.class, SysFileStorageResponse.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(Long fileId, byte[] bytes) {
        SysFileStorage sysFileStorage = new SysFileStorage();
        sysFileStorage.setFileId(fileId);
        sysFileStorage.setFileBytes(bytes);
        this.getRepository().insert(sysFileStorage);
    }

    @Override
    public String getFileAuthUrl(String fileId, String token) {
        return fileServerProperties.getDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
    }

    @Override
    public String getFileUnAuthUrl(String fileId) {
        return fileServerProperties.getDeployHost() + contextPath + FileConstants.FILE_PUBLIC_PREVIEW_URL + "?fileId=" + fileId;
    }

}

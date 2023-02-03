/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.web.service;

import io.entframework.kernel.migration.api.pojo.MigrationAggregationPOJO;
import io.entframework.kernel.migration.web.pojo.MigrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 迁移服务接口
 *
 * @date 2021/7/7 9:34
 */
public interface MigrationService {

    /**
     * 获取所有可备份数据列表
     *
     * @return {@link List<  MigrationRequest >}
     * @date 2021/7/7 9:36
     **/
    List<MigrationRequest> getAllMigrationList();

    /**
     * 备份指定数据列表
     *
     * @date 2021/7/7 9:37
     **/
    String migrationSelectData(MigrationAggregationPOJO migrationAggregationPOJO);

    /**
     * 恢复备份数据
     *
     * @date 2021/7/7 11:14
     **/
    void restoreData(MultipartFile file, String type);
}

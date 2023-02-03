/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import io.entframework.kernel.migration.aggregation.scheduling.SchedulingCenter;
import io.entframework.kernel.migration.api.constants.MigrationConstants;
import io.entframework.kernel.migration.api.pojo.MigrationAggregationPOJO;
import io.entframework.kernel.migration.web.pojo.MigrationRequest;
import io.entframework.kernel.migration.web.service.MigrationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 迁移服务实现类
 *
 * @date 2021/7/7 9:34
 */
@Service
public class MigrationServiceImpl implements MigrationService {

    @Override
    public List<MigrationRequest> getAllMigrationList() {
        // 结构：应用-模块列表
        Map<String, MigrationRequest> migrationRequestList = new HashMap<>();

        // 获取所有应用和模块的名称列表
        List<String> allMigrationInfo = SchedulingCenter.getAllMigrationInfo();

        // 应用，模块映射关系
        for (String migration : allMigrationInfo) {

            // 分割应用名称和模块名称
            String[] appAndModuleName = migration.split(MigrationConstants.NAME_SEPARATOR);

            // 获取应用名称
            String appName = appAndModuleName[0];

            // 获取模块名称
            String moduleName = appAndModuleName[1];

            // 查找该应用
            MigrationRequest migrationRequest = migrationRequestList.get(appName);
            if (ObjectUtil.isEmpty(migrationRequest)) {
                migrationRequest = new MigrationRequest();
                migrationRequest.setAppName(appName);
                migrationRequestList.put(appName, migrationRequest);
            }

            // 该应用是否有模块
            List<String> moduleNames = migrationRequest.getModuleNames();
            if (ObjectUtil.isEmpty(moduleNames)) {
                moduleNames = new ArrayList<>();
                migrationRequest.setModuleNames(moduleNames);
            }

            moduleNames.add(moduleName);
        }
        return new ArrayList<>(migrationRequestList.values());
    }

    @Override
    public String migrationSelectData(MigrationAggregationPOJO migrationAggregationPOJO) {
        // 执行导出逻辑
        SchedulingCenter.exportData(migrationAggregationPOJO);

        // 转换为Json字符串
        return JSON.toJSONString(migrationAggregationPOJO);
    }

    @Override
    public void restoreData(MultipartFile file, String type) {
        String jsonStr = null;
        try {
            // 转换文件为String类型
            jsonStr = new String(file.getBytes(), StandardCharsets.UTF_8);

            // 把字符串转为java对象
            MigrationAggregationPOJO migrationAggregationPOJO = JSON.parseObject(jsonStr, MigrationAggregationPOJO.class);

            // 交给调度中心去调度
            SchedulingCenter.importData(migrationAggregationPOJO, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

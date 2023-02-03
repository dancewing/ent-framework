/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.starter.spring;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.migration.aggregation.scheduling.SchedulingCenter;
import io.entframework.kernel.migration.api.AccessMigrationApi;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring boot启动完成的回调
 * <p>
 * 用于扫描所有的迁移实现类
 *
 * @date 2021/7/7 9:49
 */
@Component
public class MigrationApplicationRunnerImpl implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 获取所有的实现类
        Map<String, AccessMigrationApi> accessMigrationApiMap = SpringUtil.getBeansOfType(AccessMigrationApi.class);
        for (AccessMigrationApi accessMigrationApi : accessMigrationApiMap.values()) {
            // 加入调度中心
            SchedulingCenter.addMigration(accessMigrationApi);
        }
    }
}

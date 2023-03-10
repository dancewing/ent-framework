/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.api;

import io.entframework.kernel.migration.api.enums.MigrationAggregationTypeEnum;
import io.entframework.kernel.migration.api.pojo.MigrationInfo;

/**
 * 接入迁移模块Api
 * <p>
 * 1.所有需要接入迁移模块的业务，只需要实现本类即可
 *
 * @date 2021/7/6 15:17
 */
public interface AccessMigrationApi {

    /**
     * 获取应用名称
     * @return {@link java.lang.String}
     * @date 2021/7/6 17:15
     **/
    String getAppName();

    /**
     * 获取模块名称
     * @return {@link java.lang.String}
     * @date 2021/7/6 17:15
     **/
    String getModuleName();

    /**
     * 导出数据
     * @return {@link MigrationInfo}
     * @date 2021/7/6 15:20
     **/
    MigrationInfo exportData();

    /**
     * 导入数据
     * @param type 导入类型-参考{@link MigrationAggregationTypeEnum} 枚举
     * @param data 导出的数据
     * @return {@link boolean}
     * @date 2021/7/6 15:23
     **/
    boolean importData(String type, MigrationInfo data);

}

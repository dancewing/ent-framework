/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.api.pojo;

import lombok.Data;

/**
 * 迁移数据信息
 *
 * @date 2021/7/6 16:30
 */
@Data
public class MigrationInfo {

    /**
     * 版本
     */
    private String version;

    /**
     * 数据
     */
    private Object data;

}

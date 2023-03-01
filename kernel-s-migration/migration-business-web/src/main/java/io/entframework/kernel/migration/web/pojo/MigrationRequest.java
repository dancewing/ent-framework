/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.web.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 迁移数据请求对象
 *
 * @date 2021/7/7 9:15
 */
@Data
public class MigrationRequest {

    /**
     * 应用名称
     */
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 模块列表
     */
    @ChineseDescription("模块列表")
    private List<String> moduleNames;

}

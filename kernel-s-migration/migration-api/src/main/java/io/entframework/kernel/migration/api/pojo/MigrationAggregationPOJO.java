/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.migration.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 数据迁移聚合类
 *
 * @date 2021/7/6 16:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MigrationAggregationPOJO extends BaseRequest {

    /**
     * 应用和模块名称列表
     */
    @NotNull(message = "模块名称不能为空", groups = { export.class, restore.class })
    @ChineseDescription("应用和模块名称列表")
    private List<String> appAndModuleNameList;

    /**
     * 数据集
     */
    @NotNull(message = "数据集不能为空", groups = { restore.class })
    @ChineseDescription("数据集")
    private Map<String, MigrationInfo> data;

    /**
     * 导出
     */
    public @interface export {

    }

    /**
     * 恢复
     */
    public @interface restore {

    }

}

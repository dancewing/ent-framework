/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.config.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初始化系统配置参数
 *
 * @date 2021/7/8 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigInitItem {

    /**
     * 配置中文名称
     */
    @ChineseDescription("配置中文名称")
    private String configName;

    /**
     * 配置编码
     */
    @ChineseDescription("配置编码")
    private String configCode;

    /**
     * 配置的初始化值
     */
    @ChineseDescription("配置初始化值")
    private String configValue;

    /**
     * 配置的描述
     */
    @ChineseDescription("配置的描述")
    private String configDescription;

}

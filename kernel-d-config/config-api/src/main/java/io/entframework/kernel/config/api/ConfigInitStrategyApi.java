/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.config.api;

import io.entframework.kernel.config.api.pojo.ConfigInitItem;

import java.util.List;

/**
 * 配置初始化的策略
 *
 * @date 2021/7/8 17:33
 */
public interface ConfigInitStrategyApi {

    /**
     * 获取需要被初始化的配置集合
     *
     * @return 需要被初始化的配置集合
     * @date 2021/7/8 17:40
     */
    List<ConfigInitItem> getInitConfigs();

}

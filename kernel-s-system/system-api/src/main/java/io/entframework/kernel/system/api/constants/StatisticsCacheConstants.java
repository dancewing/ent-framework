/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.api.constants;

/**
 * 接口统计缓存前缀相关
 *
 * @date 2022/2/9 16:42
 */
public interface StatisticsCacheConstants {

    /**
     * 前缀
     */
    String INTERFACE_STATISTICS_PREFIX = "inter:";

    /**
     * 超时时间
     */
    Long INTERFACE_STATISTICS_CACHE_TIMEOUT_SECONDS = 3600L;

    /**
     * AOP统计执行的顺序
     */
    int INTERFACE_STATISTICS_AOP_ORDER = 600;
}

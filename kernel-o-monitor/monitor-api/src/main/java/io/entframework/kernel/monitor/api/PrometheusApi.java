/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api;

import io.entframework.kernel.monitor.api.pojo.prometheus.PromResultInfo;

import java.util.List;

/**
 * 监控管理prometheus
 *
 * @date 2021/1/10 16:09
 */
public interface PrometheusApi {

    /**
     * prometheus采集监控指标
     *
     * @param promURL prometheus地址
     * @param promQL  prometheus查询表达式
     * @param isRate  prometheus是否需要计算周期内数值函数
     * @param promQL  prometheus需要计算的指标
     * @return Map<String, Object>  指标名称与指标数据的集合
     * @date 2021/1/10 17:37
     */
    List<PromResultInfo> getMetricInfo(String promURL, String promQL, String isRate, String rateMetric);

    /**
     * 配置prometheus无效链接则关闭prometheus相关菜单
     *
     * @date 2021/3/3 16:08
     */
    void closePrometheusMenu();

    /**
     * 配置prometheus有效链接无效则显示prometheus相关菜单
     *
     * @date 2021/3/3 16:08
     */
    void displayPrometheusMenu();

}

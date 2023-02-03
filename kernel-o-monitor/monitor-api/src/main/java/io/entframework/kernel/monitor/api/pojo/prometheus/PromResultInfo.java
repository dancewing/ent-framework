/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.pojo.prometheus;

import lombok.Data;

/**
 * prometheus结果
 *
 * @date 2021/1/10 18:58
 */
@Data
public class PromResultInfo {

    /**
     * prometheus指标属性
     */
    private PromMetricInfo metric;

    /**
     * prometheus指标值
     */
    private String[] values;

}

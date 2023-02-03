/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.pojo.prometheus;

import lombok.Data;

/**
 * prometheus http响应信息
 *
 * @date 2021/1/10 19:00
 */
@Data
public class PromResponseInfo {

    /**
     * 状态:
     * <p>
     * 成功-success
     */
    private String status;

    /**
     * prometheus指标属性和值
     */
    private PromDataInfo data;

}

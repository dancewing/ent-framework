/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.api.constants;

/**
 * 支付模块的常量
 *
 * @date 2021/04/20 20:43
 */
public interface PayConstants {

    /**
     * mongodb模块的名称
     */
    String PAY_MODULE_NAME = "kernel-d-pay";

    /**
     * 异常枚举的步进值
     */
    String PAY_EXCEPTION_STEP_CODE = "80";

    /**
     * 退款成功的返回码
     */
    String REFUND_SUCCESS_CODE = "10000";

    /**
     * 退款失败的返回码
     */
    String REFUND_ERROR_CODE = "40000";

}

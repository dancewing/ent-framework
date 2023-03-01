/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.api;

import io.entframework.kernel.pay.api.pojo.TradeRefundResponse;

/**
 * 支付的api
 *
 * @date 2021/04/20 20:43
 */
public interface PayApi {

	/**
	 * PC网页支付
	 * @param orderName 订单名称
	 * @param outTradeNo 商家订单编号
	 * @param total 金额
	 * @param returnUrl 付款完成后跳转页面
	 * @return 支付页面
	 * @date 2021/04/20 20:43
	 */
	String page(String orderName, String outTradeNo, String total, String returnUrl);

	/**
	 * 手机支付
	 * @param orderName 订单名称
	 * @param outTradeNo 商家订单编号
	 * @param total 金额
	 * @param quitUrl 中途退出时返回的页面
	 * @param returnUrl 付款完成后跳转页面
	 * @return 支付页面
	 * @date 2021/04/20 20:43
	 */
	String wap(String orderName, String outTradeNo, String total, String quitUrl, String returnUrl);

	/**
	 * 退款
	 * @param outTradeNo 商家订单编号
	 * @param refundAmount 退款金额
	 * @return 退款结果
	 * @date 2021/04/20 20:43
	 */
	TradeRefundResponse refund(String outTradeNo, String refundAmount);

}

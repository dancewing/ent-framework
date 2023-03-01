/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.api.pojo;

import io.entframework.kernel.pay.api.constants.PayConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款响应
 *
 * @date 2021/04/20 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeRefundResponse {

	/**
	 * 退款状态码
	 */
	private String code;

	/**
	 * 状态描述
	 */
	private String msg;

	/**
	 * 商家订单号
	 */
	private String outTradeNo;

	/**
	 * 退款金额
	 */
	private String refundFee;

	/**
	 * 各厂商系统中的交易流水号
	 */
	private String tradeNo;

	/**
	 * 退款实际的发生时间
	 */
	private String gmtRefundPay;

	/**
	 * 买家账号
	 */
	private String buyerLogonId;

	/**
	 * 买家在各厂商系统中的用户id
	 */
	private String buyerUserId;

	/**
	 * 各厂商响应值
	 */
	private Object data;

	/**
	 * 初始化一个新创建的 TradeRefundResponse对象
	 * @param code 状态码
	 * @param msg 描述
	 * @param data 对象值
	 * @date 2021/04/20 20:43
	 */
	public TradeRefundResponse(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 返回错误信息
	 * @param msg 描述
	 * @param data 对象值
	 * @return TradeRefundResponse对象
	 * @date 2021/04/20 20:43
	 */
	public static TradeRefundResponse error(String msg, Object data) {
		return new TradeRefundResponse(PayConstants.REFUND_ERROR_CODE, msg, data);
	}

	/**
	 * 返回错误信息
	 * @param msg 描述
	 * @return TradeRefundResponse对象
	 * @date 2021/04/20 20:43
	 */
	public static TradeRefundResponse error(String msg) {
		return error(msg, null);
	}

}

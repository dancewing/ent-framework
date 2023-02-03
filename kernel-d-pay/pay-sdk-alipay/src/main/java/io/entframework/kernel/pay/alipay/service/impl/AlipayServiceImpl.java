/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.alipay.service.impl;

import io.entframework.kernel.pay.alipay.constants.AlipayConstants;
import io.entframework.kernel.pay.alipay.service.AlipayService;
import io.entframework.kernel.pay.api.PayApi;
import io.entframework.kernel.pay.api.constants.PayConstants;
import io.entframework.kernel.pay.api.pojo.TradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里支付接口实现
 *
 * @date 2021/04/20 20:43
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService, PayApi {

    @Override
    public String page(String orderName, String outTradeNo, String total, String returnUrl) {
        try {
            return Factory.Payment.Page().pay(orderName, outTradeNo, total, returnUrl).body;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String wap(String orderName, String outTradeNo, String total, String quitUrl, String returnUrl) {
        try {
            return Factory.Payment.Wap().pay(orderName, outTradeNo, total, quitUrl, returnUrl).body;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public TradeRefundResponse refund(String outTradeNo, String refundAmount) {
        try {
            AlipayTradeRefundResponse response = Factory.Payment.Common().refund(outTradeNo, refundAmount);
            if (AlipayConstants.ALIPAY_REFUND_SUCCESS_CODE.equals(response.getCode())) {
                return TradeRefundResponse.builder()
                        .code(PayConstants.REFUND_SUCCESS_CODE)
                        .msg(response.getMsg())
                        .outTradeNo(response.getOutTradeNo())
                        .refundFee(response.getRefundFee())
                        .tradeNo(response.getTradeNo())
                        .gmtRefundPay(response.getGmtRefundPay())
                        .buyerLogonId(response.buyerLogonId)
                        .buyerUserId(response.buyerUserId)
                        .data(response)
                        .build();
            }
            return TradeRefundResponse.error(response.msg, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return TradeRefundResponse.error(e.getMessage());
        }
    }

}

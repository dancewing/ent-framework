/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.tencent;

import cn.hutool.core.util.ArrayUtil;
import io.entframework.kernel.sms.api.SmsSenderApi;
import io.entframework.kernel.sms.api.exception.SmsException;
import io.entframework.kernel.sms.api.exception.enums.SmsExceptionEnum;
import io.entframework.kernel.sms.api.pojo.TencentSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * 腾讯云短信发送
 *
 * @date 2020/5/24 17:58
 */
public class TencentSmsSender implements SmsSenderApi {

    private TencentSmsProperties tencentSmsProperties;

    public TencentSmsSender(TencentSmsProperties tencentSmsProperties) {
        this.tencentSmsProperties = tencentSmsProperties;
    }

    @Override
    public void sendSms(String phone, String templateCode, Map<String, Object> params) {
        try {

            // 实例化一个认证对象
            Credential cred = new Credential(tencentSmsProperties.getSecretId(), tencentSmsProperties.getSecretKey());

            // 实例化一个 http 选项，可选，无特殊需求时可以跳过
            HttpProfile httpProfile = new HttpProfile();
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);

            // 实例化 SMS 的 client 对象
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

            // 构建请求参数
            SendSmsRequest req = new SendSmsRequest();

            // 设置应用id
            req.setSmsSdkAppid(tencentSmsProperties.getSdkAppId());

            // 设置签名
            req.setSign(tencentSmsProperties.getSign());

            // 设置模板id
            req.setTemplateID(templateCode);

            // 默认发送一个手机短信
            String[] phoneNumbers = { "+86" + phone };
            req.setPhoneNumberSet(phoneNumbers);

            // 模板参数
            if (params != null && params.size() > 0) {
                LinkedList<String> strings = new LinkedList<>();
                Collection<Object> values = params.values();
                for (Object value : values) {
                    strings.add(value.toString());
                }
                req.setTemplateParamSet(ArrayUtil.toArray(strings, String.class));
            }

            SendSmsResponse res = client.SendSms(req);

            SendStatus[] sendStatusSet = res.getSendStatusSet();
            if (sendStatusSet != null && sendStatusSet.length > 0) {
                if (!"Ok".equals(sendStatusSet[0].getCode())) {
                    throw new SmsException(SmsExceptionEnum.TENCENT_SMS_PARAM_NULL, sendStatusSet[0].getCode(),
                            sendStatusSet[0].getMessage());
                }
            }
        }
        catch (TencentCloudSDKException e) {
            throw new SmsException(SmsExceptionEnum.TENCENT_SMS_PARAM_NULL,
                    SmsExceptionEnum.TENCENT_SMS_PARAM_NULL.getErrorCode(), e.getMessage());
        }
    }

}

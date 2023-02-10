/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.sms.modular.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.security.api.ImageCaptchaApi;
import io.entframework.kernel.sms.api.SmsSenderApi;
import io.entframework.kernel.sms.api.constants.SmsConstants;
import io.entframework.kernel.sms.api.exception.SmsException;
import io.entframework.kernel.sms.api.exception.enums.SmsExceptionEnum;
import io.entframework.kernel.sms.api.expander.SmsConfigExpander;
import io.entframework.kernel.sms.modular.entity.SysSms;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import io.entframework.kernel.sms.modular.enums.SmsTypeEnum;
import io.entframework.kernel.sms.modular.pojo.SysSmsSendParam;
import io.entframework.kernel.sms.modular.pojo.SysSmsVerifyParam;
import io.entframework.kernel.sms.modular.pojo.request.SysSmsRequest;
import io.entframework.kernel.sms.modular.pojo.response.SysSmsResponse;
import io.entframework.kernel.sms.modular.service.SysSmsService;
import io.entframework.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SysSmsServiceImpl extends BaseServiceImpl<SysSmsRequest, SysSmsResponse, SysSms> implements SysSmsService {
    public SysSmsServiceImpl() {
        super(SysSmsRequest.class, SysSmsResponse.class, SysSms.class);
    }

    @Resource
    private SmsSenderApi smsSenderApi;

    @Resource
    private ImageCaptchaApi captchaApi;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendShortMessage(SysSmsSendParam sysSmsSendParam) {
        String verKey = sysSmsSendParam.getVerKey();
        String verCode = sysSmsSendParam.getVerCode();
        if (CharSequenceUtil.isEmpty(verKey) || CharSequenceUtil.isEmpty(verCode)) {
            throw new SmsException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
        }
        if (!captchaApi.validateCaptcha(verKey, verCode)) {
            throw new SmsException(ValidatorExceptionEnum.CAPTCHA_ERROR);
        }

        Map<String, Object> params = sysSmsSendParam.getParams();

        // 1. 如果是纯消息发送，直接发送，校验类短信要把验证码存库
        if (SmsTypeEnum.MESSAGE.equals(sysSmsSendParam.getSmsTypeEnum())) {
            smsSenderApi.sendSms(sysSmsSendParam.getPhone(), sysSmsSendParam.getTemplateCode(), params);
        }

        // 2. 如果参数中有code参数，则获取参数param中的code值
        String validateCode;
        if (params != null && params.get(SmsConstants.SMS_CODE_PARAM_NAME) != null) {
            validateCode = params.get(SmsConstants.SMS_CODE_PARAM_NAME).toString();
        }

        // 3. 如果参数中没有code参数，自动装填一个code参数，用于放随机的验证码
        else {
            validateCode = RandomUtil.randomNumbers(6);
            if (params == null) {
                params = MapUtil.newHashMap();
            }
            params.put(SmsConstants.SMS_CODE_PARAM_NAME, validateCode);
        }

        // 4. 存储短信到数据库
        Long smsId = this.saveSmsInfo(sysSmsSendParam, validateCode);

        log.info("开始发送短信：发送的电话号码= " + sysSmsSendParam.getPhone() + ",发送的模板号=" + sysSmsSendParam.getTemplateCode() + "，发送的参数是：" + JSON.toJSONString(params));

        // 5. 发送短信
        smsSenderApi.sendSms(sysSmsSendParam.getPhone(), sysSmsSendParam.getTemplateCode(), params);

        // 6. 更新短信发送状态
        this.updateSmsInfo(smsId, SmsSendStatusEnum.SUCCESS);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveSmsInfo(SysSmsSendParam sysSmsSendParam, String validateCode) {

        // 获取当前时间
        Date nowDate = new Date();

        // 计算短信失效时间
        Integer invalidedSeconds = SmsConfigExpander.getSmsValidateExpiredSeconds();
        long invalidateTime = nowDate.getTime() + invalidedSeconds * 1000;
        Date invalidate = new Date(invalidateTime);

        SysSms sysSms = new SysSms();
        sysSms.setInvalidTime(LocalDateTime.now());
        sysSms.setPhoneNumber(sysSmsSendParam.getPhone());
        sysSms.setStatusFlag(SmsSendStatusEnum.WAITING);
        sysSms.setSource(sysSmsSendParam.getSmsSendSourceEnum());
        sysSms.setTemplateCode(sysSmsSendParam.getTemplateCode());
        sysSms.setValidateCode(validateCode);

        this.getRepository().insert(sysSms);

        log.info("发送短信，存储短信到数据库，数据为：" + JSON.toJSONString(sysSms));

        return sysSms.getSmsId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSmsInfo(Long smsId, SmsSendStatusEnum smsSendStatusEnum) {
        Optional<SysSms> sysSms = this.getRepository().selectByPrimaryKey(getEntityClass(), smsId);
        if (sysSms.isPresent()) {
            SysSms ss = sysSms.get();
            ss.setStatusFlag(smsSendStatusEnum);
            this.getRepository().updateByPrimaryKey(ss);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void validateSmsInfo(SysSmsVerifyParam sysSmsVerifyParam) {

        // 查询有没有这条记录
        SysSms query = new SysSms();
        query.setPhoneNumber(sysSmsVerifyParam.getPhone());
        query.setSource(sysSmsVerifyParam.getSmsSendSourceEnum());
        query.setTemplateCode(sysSmsVerifyParam.getTemplateCode());
        List<SysSms> sysSmsList = this.getRepository().selectBy(query);

        log.info("验证短信Provider接口，查询到sms记录：" + JSON.toJSONString(sysSmsList));

        // 如果找不到记录，提示验证失败
        if (ObjectUtil.isEmpty(sysSmsList)) {
            throw new SmsException(SmsExceptionEnum.SMS_VALIDATE_ERROR_NOT_EXISTED_RECORD);
        }

        // 获取最近发送的第一条
        SysSms sysSms = sysSmsList.get(0);

        // 先判断状态是不是失效的状态
        if (SmsSendStatusEnum.INVALID == sysSms.getStatusFlag()) {
            throw new SmsException(SmsExceptionEnum.SMS_VALIDATE_ERROR_INVALIDATE_STATUS);
        }

        // 如果验证码和传过来的不一致
        if (!sysSmsVerifyParam.getCode().equals(sysSms.getValidateCode())) {
            throw new SmsException(SmsExceptionEnum.SMS_VALIDATE_ERROR_INVALIDATE_CODE);
        }

        // 判断是否超时
        LocalDateTime invalidTime = sysSms.getInvalidTime();
        if (ObjectUtil.isEmpty(invalidTime) || LocalDateTime.now().isAfter(invalidTime)) {
            throw new SmsException(SmsExceptionEnum.SMS_VALIDATE_ERROR_INVALIDATE_TIME);
        }

        // 验证成功把短信设置成失效
        sysSms.setStatusFlag(SmsSendStatusEnum.INVALID);
        this.getRepository().updateByPrimaryKey(sysSms);
    }

    @Override
    public PageResult<SysSmsResponse> page(SysSmsRequest sysSmsRequest) {
        return super.page(sysSmsRequest);
    }
}
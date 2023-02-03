/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.sms.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.service.BaseService;
import io.entframework.kernel.sms.modular.entity.SysSms;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import io.entframework.kernel.sms.modular.pojo.SysSmsSendParam;
import io.entframework.kernel.sms.modular.pojo.SysSmsVerifyParam;
import io.entframework.kernel.sms.modular.pojo.request.SysSmsRequest;
import io.entframework.kernel.sms.modular.pojo.response.SysSmsResponse;

public interface SysSmsService extends BaseService<SysSmsRequest, SysSmsResponse, SysSms> {

    /**
     * 发送短信
     *
     * @param sysSmsSendParam 短信发送参数
     * @return true-成功，false-失败
     * @date 2020/10/26 22:16
     */
    boolean sendShortMessage(SysSmsSendParam sysSmsSendParam);

    /**
     * 存储短信验证信息
     *
     * @param sysSmsSendParam 发送参数
     * @param validateCode    验证码
     * @return 短信记录id
     * @date 2020/10/26 22:16
     */
    Long saveSmsInfo(SysSmsSendParam sysSmsSendParam, String validateCode);

    /**
     * 更新短息发送状态
     *
     * @param smsId             短信记录id
     * @param smsSendStatusEnum 发送状态枚举
     * @date 2020/10/26 22:16
     */
    void updateSmsInfo(Long smsId, SmsSendStatusEnum smsSendStatusEnum);

    /**
     * 校验验证码是否正确
     * <p>
     * 如果校验失败，或者短信超时，则会抛出异常
     *
     * @param sysSmsVerifyParam 短信校验参数
     * @date 2020/10/26 22:16
     */
    void validateSmsInfo(SysSmsVerifyParam sysSmsVerifyParam);

    /**
     * 短信发送记录查询
     *
     * @param sysSmsRequest 查询参数
     * @return 查询分页结果
     * @date 2020/10/26 22:17
     */
    PageResult<SysSmsResponse> page(SysSmsRequest sysSmsRequest);
}
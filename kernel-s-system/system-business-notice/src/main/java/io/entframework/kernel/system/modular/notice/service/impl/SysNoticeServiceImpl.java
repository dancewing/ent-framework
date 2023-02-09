/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.message.api.MessageApi;
import io.entframework.kernel.message.api.enums.MessageBusinessTypeEnum;
import io.entframework.kernel.message.api.pojo.request.MessageSendRequest;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.notice.NoticeExceptionEnum;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeRequest;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeResponse;
import io.entframework.kernel.system.modular.notice.entity.SysNotice;
import io.entframework.kernel.system.modular.notice.service.SysNoticeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 通知表 服务实现类
 *
 * @date 2021/1/8 22:45
 */
@Slf4j
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeRequest, SysNoticeResponse, SysNotice> implements SysNoticeService {

    private static final String NOTICE_SCOPE_ALL = "all";

    @Resource
    private MessageApi messageApi;

    public SysNoticeServiceImpl() {
        super(SysNoticeRequest.class, SysNoticeResponse.class, SysNotice.class);
    }

    @Override
    public void add(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = new SysNotice();

        // 拷贝属性
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 没传递通知范围，则默认发给所有人
        if (CharSequenceUtil.isBlank(sysNotice.getNoticeScope())) {
            sysNotice.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 如果保存成功调用发送消息
        this.getRepository().insert(sysNotice);
        sendMessage(sysNotice);
    }

    @Override
    public void del(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);
        // 逻辑删除
        sysNotice.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysNotice);
    }

    @Override
    public SysNoticeResponse update(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);

        // 通知范围不允许修改， 如果通知范围不同抛出异常
        if (!sysNoticeRequest.getNoticeScope().equals(sysNotice.getNoticeScope())) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_SCOPE_NOT_EDIT);
        }

        // 获取通知范围，如果为空则设置为all
        String noticeScope = sysNotice.getNoticeScope();
        if (CharSequenceUtil.isBlank(noticeScope)) {
            sysNoticeRequest.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 更新属性
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 修改成功后发送信息
        this.getRepository().updateByPrimaryKey(sysNotice);
        sendMessage(sysNotice);
        return this.converterService.convert(sysNotice, getResponseClass());
    }

    @Override
    public SysNoticeResponse detail(SysNoticeRequest sysNoticeRequest) {
        return this.selectOne(sysNoticeRequest);
    }

    @Override
    public PageResult<SysNoticeResponse> findPage(SysNoticeRequest sysNoticeRequest) {
        return this.page(sysNoticeRequest);
    }

    @Override
    public List<SysNoticeResponse> findList(SysNoticeRequest sysNoticeRequest) {
        return this.select(sysNoticeRequest);
    }

    /**
     * 获取通知管理
     *
     * @date 2021/1/9 16:56
     */
    private SysNotice querySysNoticeById(SysNoticeRequest sysNoticeRequest) {
        Optional<SysNotice> sysNotice = this.getRepository().selectByPrimaryKey(getEntityClass(), sysNoticeRequest.getNoticeId());
        if (sysNotice.isEmpty()) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_NOT_EXIST, sysNoticeRequest.getNoticeId());
        }
        return sysNotice.get();
    }

    /**
     * 发送消息
     *
     * @date 2021/2/8 19:30
     */
    private void sendMessage(SysNotice sysNotice) {
        MessageSendRequest message = new MessageSendRequest();

        // 消息标题
        message.setMessageTitle(sysNotice.getNoticeTitle());

        // 消息内容
        message.setMessageContent(sysNotice.getNoticeContent());

        // 消息优先级
        message.setPriorityLevel(sysNotice.getPriorityLevel());

        // 消息发送范围
        message.setReceiveUserIds(sysNotice.getNoticeScope());

        // 消息业务类型
        message.setBusinessType(MessageBusinessTypeEnum.SYS_NOTICE.getCode());
        message.setBusinessTypeValue(MessageBusinessTypeEnum.SYS_NOTICE.getName());

        message.setBusinessId(sysNotice.getNoticeId());
        message.setMessageSendTime(new Date());

        try {
            messageApi.sendMessage(message);
        } catch (Exception exception) {
            // 发送失败打印异常
            log.error("发送消息失败:", exception);
        }
    }

}

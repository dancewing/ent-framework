/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;
import io.entframework.kernel.message.db.entity.SysMessage;
import io.entframework.kernel.message.db.service.SysMessageService;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统消息 service接口实现类
 *
 * @date 2020/12/31 20:09
 */
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessageRequest, SysMessageResponse, SysMessage> implements SysMessageService {

    public SysMessageServiceImpl() {
        super(SysMessageRequest.class, SysMessageResponse.class, SysMessage.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMessageRequest sysMessageRequest) {
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(sysMessageRequest, sysMessage);
        this.getRepository().insert(sysMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysMessageRequest sysMessageRequest) {
        SysMessage sysMessage = this.getRepository().get(getEntityClass(), sysMessageRequest.getMessageId());
        // 逻辑删除
        sysMessage.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMessageResponse update(SysMessageRequest sysMessageRequest) {
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(sysMessageRequest, sysMessage);
        this.getRepository().updateByPrimaryKey(sysMessage);
        return this.converterService.convert(sysMessage, getResponseClass());
    }

    @Override
    public SysMessageResponse detail(SysMessageRequest sysMessageRequest) {
        return this.selectOne(sysMessageRequest);
    }

    @Override
    public PageResult<SysMessageResponse> findPage(SysMessageRequest sysMessageRequest) {
        return this.page(sysMessageRequest);
    }

    @Override
    public List<SysMessageResponse> findList(SysMessageRequest sysMessageRequest) {
        return this.list(sysMessageRequest);
    }

    @Override
    public long findCount(SysMessageRequest sysMessageRequest) {
        return this.countBy(sysMessageRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMessage sysMessage) {
        this.getRepository().update(sysMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysMessage> batchCreateEntity(List<SysMessage> records) {
        return this.getRepository().insertMultiple(records);
    }
}

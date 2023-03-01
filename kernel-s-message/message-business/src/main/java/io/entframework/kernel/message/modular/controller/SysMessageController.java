/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.modular.controller;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.message.api.MessageApi;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.message.api.pojo.request.MessageSendRequest;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 系统消息控制器
 *
 * @date 2021/1/1 22:30
 */
@RestController
@ApiResource(name = "系统消息控制器")
public class SysMessageController {

    /**
     * 系统消息api
     */
    @Resource
    private MessageApi messageApi;

    /**
     * 发送系统消息
     *
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "发送系统消息", path = "/sys-message/send-message")
    public ResponseData<Void> sendMessage(
            @RequestBody @Validated(BaseRequest.add.class) MessageSendRequest messageSendRequest) {
        messageSendRequest.setMessageSendTime(new Date());
        messageApi.sendMessage(messageSendRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 批量更新系统消息状态
     *
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "批量更新系统消息状态", path = "/sys-message/batch-update-read-flag")
    public ResponseData<Void> batchUpdateReadFlag(
            @RequestBody @Validated(SysMessageRequest.updateReadFlag.class) SysMessageRequest sysMessageRequest) {
        List<Long> messageIdList = sysMessageRequest.getMessageIdList();
        messageApi.batchReadFlagByMessageIds(CharSequenceUtil.join(",", messageIdList), MessageReadFlagEnum.READ);
        return ResponseData.OK_VOID;
    }

    /**
     * 系统消息全部修改已读
     *
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息全部修改已读", path = "/sys-message/all-message-read-flag")
    public ResponseData<Void> allMessageReadFlag() {
        messageApi.allMessageReadFlag();
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统消息
     *
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "删除系统消息", path = "/sys-message/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) SysMessageRequest sysMessageRequest) {
        messageApi.deleteByMessageId(sysMessageRequest.getMessageId());
        return ResponseData.OK_VOID;
    }

    /**
     * 查看系统消息
     *
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "查看系统消息", path = "/sys-message/detail")
    public ResponseData<SysMessageResponse> detail(
            @Validated(BaseRequest.detail.class) SysMessageRequest sysMessageRequest) {
        return ResponseData.ok(messageApi.messageDetail(sysMessageRequest));
    }

    /**
     * 分页查询系统消息列表
     *
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "分页查询系统消息列表", path = "/sys-message/page")
    public ResponseData<PageResult<SysMessageResponse>> page(SysMessageRequest sysMessageRequest) {
        return ResponseData.ok(messageApi.queryPageCurrentUser(sysMessageRequest));
    }

    /**
     * 系统消息列表
     *
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息列表", path = "/sys-message/list")
    public ResponseData<List<SysMessageResponse>> list(SysMessageRequest sysMessageRequest) {
        return ResponseData.ok(messageApi.queryListCurrentUser(sysMessageRequest));
    }

    /**
     * 查询所有未读系统消息列表
     *
     * @date 2021/6/12 17:42
     */
    @GetResource(name = "查询所有未读系统消息列表", path = "/sys-message/un-read-message-list", requiredPermission = false)
    public ResponseData<List<SysMessageResponse>> unReadMessageList(SysMessageRequest sysMessageRequest) {
        sysMessageRequest.setReadFlag(MessageReadFlagEnum.UNREAD);
        List<SysMessageResponse> sysMessageRespons = messageApi.queryListCurrentUser(sysMessageRequest);
        return ResponseData.ok(sysMessageRespons);
    }

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.notice;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 通知管理 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysNoticeResponse extends BaseResponse {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long noticeId;

    /**
     * 通知标题
     */
    @ChineseDescription("通知标题")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @ChineseDescription("通知摘要")
    private String noticeSummary;

    /**
     * 通知内容
     */
    @ChineseDescription("通知内容")
    private String noticeContent;

    /**
     * 优先级
     */
    @ChineseDescription("优先级")
    private String priorityLevel;

    /**
     * 开始时间
     */
    @ChineseDescription("开始时间")
    private LocalDateTime noticeBeginTime;

    /**
     * 结束时间
     */
    @ChineseDescription("结束时间")
    private LocalDateTime noticeEndTime;

    /**
     * 通知范围（用户id字符串）
     */
    @ChineseDescription("通知范围（用户id字符串）")
    private String noticeScope;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @ChineseDescription("是否删除：Y-被删除，N-未删除")
    private YesOrNotEnum delFlag;

}
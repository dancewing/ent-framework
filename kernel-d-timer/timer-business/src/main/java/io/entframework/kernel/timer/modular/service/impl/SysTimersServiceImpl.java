/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HtmlUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.timer.api.TimerAction;
import io.entframework.kernel.timer.api.TimerExeService;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import io.entframework.kernel.timer.api.exception.TimerException;
import io.entframework.kernel.timer.api.exception.enums.TimerExceptionEnum;
import io.entframework.kernel.timer.api.pojo.SysTimersRequest;
import io.entframework.kernel.timer.api.pojo.SysTimersResponse;
import io.entframework.kernel.timer.modular.entity.SysTimers;
import io.entframework.kernel.timer.modular.service.SysTimersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 定时任务 服务实现类
 *
 * @date 2020/6/30 18:26
 */
@Service
public class SysTimersServiceImpl extends BaseServiceImpl<SysTimersRequest, SysTimersResponse, SysTimers> implements SysTimersService {

    @Resource
    private TimerExeService timerExeService;

    public SysTimersServiceImpl() {
        super(SysTimersRequest.class, SysTimersResponse.class, SysTimers.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysTimersRequest sysTimersRequest) {
        // 还原被转义的HTML特殊字符
        unescapeHtml(sysTimersRequest);

        // 将dto转为实体
        SysTimers sysTimers = this.converterService.convert(sysTimersRequest, getEntityClass());
        // 设置为停止状态，点击启动时启动任务
        sysTimers.setJobStatus(TimerJobStatusEnum.STOP);

        this.getRepository().insert(sysTimers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysTimersRequest sysTimersRequest) {

        // 先停止id为参数id的定时器
        CronUtil.remove(String.valueOf(sysTimersRequest.getTimerId()));

        SysTimers sysTimers = this.getRepository().get(getEntityClass(), sysTimersRequest.getTimerId());
        sysTimers.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysTimers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysTimersResponse update(SysTimersRequest sysTimersRequest) {
        // 还原被转义的HTML特殊字符
        unescapeHtml(sysTimersRequest);

        // 更新库中记录
        SysTimers oldTimer = this.querySysTimers(sysTimersRequest);
        this.converterService.copy(sysTimersRequest, oldTimer);
        this.getRepository().updateByPrimaryKey(oldTimer);

        // 查看被编辑的任务的状态
        TimerJobStatusEnum jobStatus = oldTimer.getJobStatus();

        // 如果任务正在运行，则停掉这个任务，从新运行任务
        if (TimerJobStatusEnum.RUNNING == jobStatus) {
            CronUtil.remove(String.valueOf(oldTimer.getTimerId()));
            timerExeService.startTimer(String.valueOf(sysTimersRequest.getTimerId()), sysTimersRequest.getCron(), sysTimersRequest.getActionClass(), sysTimersRequest.getParams());
        }

        return this.converterService.convert(oldTimer, getResponseClass());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void start(SysTimersRequest sysTimersRequest) {

        // 更新库中的状态
        SysTimers sysTimers = this.getRepository().get(getEntityClass(), sysTimersRequest.getTimerId());
        sysTimers.setJobStatus(TimerJobStatusEnum.RUNNING);
        this.getRepository().updateByPrimaryKey(sysTimers);

        // 添加定时任务调度
        sysTimers = this.querySysTimers(sysTimersRequest);
        timerExeService.startTimer(String.valueOf(sysTimers.getTimerId()), sysTimers.getCron(), sysTimers.getActionClass(), sysTimers.getParams());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void stop(SysTimersRequest sysTimersRequest) {

        // 更新库中的状态
        SysTimers sysTimers = this.getRepository().get(getEntityClass(), sysTimersRequest.getTimerId());
        sysTimers.setJobStatus(TimerJobStatusEnum.STOP);
        this.getRepository().updateByPrimaryKey(sysTimers);

        // 关闭定时任务调度
        sysTimers = this.querySysTimers(sysTimersRequest);
        timerExeService.stopTimer(String.valueOf(sysTimers.getTimerId()));
    }

    @Override
    public SysTimers detail(SysTimersRequest sysTimersRequest) {
        return this.querySysTimers(sysTimersRequest);
    }

    @Override
    public PageResult<SysTimersResponse> findPage(SysTimersRequest sysTimersRequest) {
        return this.page(sysTimersRequest);
    }

    @Override
    public List<SysTimersResponse> findList(SysTimersRequest sysTimersRequest) {
        return this.select(sysTimersRequest);
    }

    @Override
    public List<String> getActionClasses() {

        // 获取spring容器中的这类bean
        Map<String, TimerAction> timerActionMap = SpringUtil.getBeansOfType(TimerAction.class);
        if (ObjectUtil.isNotEmpty(timerActionMap)) {
            Collection<TimerAction> values = timerActionMap.values();
            return values.stream().map(i -> i.getClass().getName()).toList();
        } else {
            return CollUtil.newArrayList();
        }
    }

    /**
     * 获取定时任务详情
     *
     * @date 2020/6/30 18:26
     */
    private SysTimers querySysTimers(SysTimersRequest sysTimersRequest) {
        SysTimers sysTimers = this.getRepository().get(getEntityClass(), sysTimersRequest.getTimerId());
        if (ObjectUtil.isEmpty(sysTimers) || YesOrNotEnum.Y == sysTimers.getDelFlag()) {
            throw new TimerException(TimerExceptionEnum.JOB_DETAIL_NOT_FOUND, sysTimersRequest.getTimerId());
        }
        return sysTimers;
    }

    /**
     * 还原被转义的HTML特殊字符
     *
     * @param sysTimersRequest 定时任务参数
     * @date 2021/04/09 21:40
     */
    private void unescapeHtml(SysTimersRequest sysTimersRequest) {
        String params = sysTimersRequest.getParams();
        params = HtmlUtil.unescape(params);
        sysTimersRequest.setParams(params);
    }

}

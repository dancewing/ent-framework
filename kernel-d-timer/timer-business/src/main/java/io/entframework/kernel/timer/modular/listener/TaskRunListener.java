/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.modular.listener;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.listener.ApplicationReadyListener;
import io.entframework.kernel.timer.api.TimerExeService;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import io.entframework.kernel.timer.api.pojo.SysTimersRequest;
import io.entframework.kernel.timer.api.pojo.SysTimersResponse;
import io.entframework.kernel.timer.modular.service.SysTimersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 项目启动，将数据库所有定时任务开启
 *
 * @date 2021/1/12 20:40
 */
@Slf4j
public class TaskRunListener extends ApplicationReadyListener implements Ordered {

    @Override
    public void eventCallback(ApplicationReadyEvent event) {

        SysTimersService sysTimersService = SpringUtil.getBean(SysTimersService.class);
        TimerExeService timerExeService = SpringUtil.getBean(TimerExeService.class);

        log.info("开启任务调度");
        // 开启任务调度
        timerExeService.start();

        // 获取数据库所有开启状态的任务
        SysTimersRequest sysTimersRequest = new SysTimersRequest();
        sysTimersRequest.setDelFlag(YesOrNotEnum.N);
        sysTimersRequest.setJobStatus(TimerJobStatusEnum.RUNNING);
        List<SysTimersResponse> list = sysTimersService.findList(sysTimersRequest);

        // 添加定时任务到调度器
        for (SysTimersResponse sysTimers : list) {
            try {
                timerExeService.startTimer(String.valueOf(sysTimers.getTimerId()), sysTimers.getCron(), sysTimers.getActionClass(), sysTimers.getParams());
            } catch (Exception exception) {
                // 遇到错误直接略过这个定时器（可能多个项目公用库）
                log.error("定时器初始化遇到错误，略过该定时器！", exception);
            }
        }

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 300;
    }

}

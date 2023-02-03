/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.timer;

import io.entframework.kernel.system.modular.home.service.HomePageService;
import io.entframework.kernel.timer.api.TimerAction;

import jakarta.annotation.Resource;

/**
 * 定时刷新接口访问次数统计
 *
 * @date 2022/2/9 16:08
 */
public class InterfaceStatisticsTimer implements TimerAction {

    @Resource
    private HomePageService homePageService;

    @Override
    public void action(String params) {
        homePageService.saveStatisticsCacheToDb();
    }

}

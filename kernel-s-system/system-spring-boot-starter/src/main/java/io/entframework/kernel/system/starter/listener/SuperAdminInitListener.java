/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.starter.listener;

import io.entframework.kernel.rule.listener.ApplicationReadyListener;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.starter.init.InitAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 项目启动后初始化超级管理员
 *
 * @date 2020/12/17 21:44
 */
@Component
@Slf4j
public class SuperAdminInitListener extends ApplicationReadyListener implements Ordered {

    @Resource
    private InitAdminService initAdminService;

    @Override
    public void eventCallback(ApplicationReadyEvent event) {
        initAdminService.initSuperAdmin();
    }

    @Override
    public int getOrder() {
        return SystemConstants.SUPER_ADMIN_INIT_LISTENER_SORT;
    }

}

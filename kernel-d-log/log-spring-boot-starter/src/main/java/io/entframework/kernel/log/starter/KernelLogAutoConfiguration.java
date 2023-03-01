/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.starter;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.log.api.LogManagerApi;
import io.entframework.kernel.log.api.LogRecordApi;
import io.entframework.kernel.log.api.config.SysLogProperties;
import io.entframework.kernel.log.api.enums.LogSaveTypeEnum;
import io.entframework.kernel.log.api.threadpool.LogManagerThreadPool;
import io.entframework.kernel.log.db.service.DbLogManagerServiceImpl;
import io.entframework.kernel.log.db.service.DbLogRecordServiceImpl;
import io.entframework.kernel.log.db.service.SysLogService;
import io.entframework.kernel.log.file.FileLogManagerServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 17:12
 */
@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.log", "io.entframework.kernel.system.modular.loginlog" })
@EnableConfigurationProperties({ SysLogProperties.class })
public class KernelLogAutoConfiguration {

    /**
     * 日志管理器
     * @param sysLogProperties 系统日志配置文件
     * @date 2020/12/20 18:53
     */
    @Bean
    public LogManagerApi logManagerApi(SysLogProperties sysLogProperties) {

        // 如果类型是文件
        if (CharSequenceUtil.isNotBlank(sysLogProperties.getType())
                && LogSaveTypeEnum.FILE.getCode().equals(sysLogProperties.getType())) {

            return new FileLogManagerServiceImpl(sysLogProperties.getFileSavePath());
        }

        // 其他情况用数据库存储日志
        return new DbLogManagerServiceImpl();
    }

    /**
     * 日志记录的api
     *
     * @date 2021/3/4 22:16
     */
    @Bean
    public LogRecordApi logRecordApi(SysLogService sysLogService) {
        return new DbLogRecordServiceImpl(new LogManagerThreadPool(), sysLogService);
    }

    @Bean
    public KernelLogModuleRegister kernelLogModuleRegister() {
        return new KernelLogModuleRegister();
    }

}

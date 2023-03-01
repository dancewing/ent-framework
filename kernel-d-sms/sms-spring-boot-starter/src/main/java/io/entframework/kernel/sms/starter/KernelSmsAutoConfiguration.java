/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.sms.starter;

import io.entframework.kernel.sms.aliyun.AliyunSmsSender;
import io.entframework.kernel.sms.aliyun.msign.impl.MapBasedMultiSignManager;
import io.entframework.kernel.sms.api.SmsSenderApi;
import io.entframework.kernel.sms.api.pojo.AliyunSmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 短信的自动配置类
 *
 * @author jeff_qian
 * @date 2020/12/1 21:18
 */
@Configuration
@ComponentScan("io.entframework.kernel.sms")
@EnableConfigurationProperties({ AliyunSmsProperties.class })
public class KernelSmsAutoConfiguration {

    /**
     * 短信发送器的配置
     *
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(SmsSenderApi.class)
    public SmsSenderApi smsSenderApi(AliyunSmsProperties aliyunSmsProperties) {

        // 配置默认从系统配置表读取
        return new AliyunSmsSender(new MapBasedMultiSignManager(), aliyunSmsProperties);
    }

    @Bean
    public KernelSmsModuleRegister kernelSmsModuleRegister() {
        return new KernelSmsModuleRegister();
    }

}

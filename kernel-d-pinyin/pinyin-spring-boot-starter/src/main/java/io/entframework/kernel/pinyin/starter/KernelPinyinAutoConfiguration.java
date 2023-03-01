/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pinyin.starter;

import io.entframework.kernel.pinyin.PinyinServiceImpl;
import io.entframework.kernel.pinyin.api.PinYinApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拼音的自动配置
 *
 * @date 2020/12/4 15:28
 */
@Configuration
public class KernelPinyinAutoConfiguration {

    /**
     * 拼音工具接口的封装
     *
     * @date 2020/12/4 15:29
     */
    @Bean
    @ConditionalOnMissingBean(PinYinApi.class)
    public PinYinApi pinYinApi() {
        return new PinyinServiceImpl();
    }

}

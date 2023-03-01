/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.starter;

import io.entframework.kernel.security.database.algorithm.EncryptAlgorithmApi;
import io.entframework.kernel.security.database.algorithm.impl.AesEncryptAlgorithmApiImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加密算法自动配置
 *
 * @date 2021/7/5 10:06
 */
@Configuration
public class EncryptAlgorithmAutoConfiguration {

    /**
     * 数据库加密算法
     * @return {@link EncryptAlgorithmApi}
     * @date 2021/7/5 10:16
     **/
    @Bean
    @ConditionalOnMissingBean(EncryptAlgorithmApi.class)
    public EncryptAlgorithmApi encryptAlgorithmApi() {
        return new AesEncryptAlgorithmApiImpl();
    }

}

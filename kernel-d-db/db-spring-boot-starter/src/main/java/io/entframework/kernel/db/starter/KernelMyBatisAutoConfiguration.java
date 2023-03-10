/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.dao.mybatis.interceptor.MybatisDynamicInterceptor;
import io.entframework.kernel.db.dao.mybatis.interceptor.RecordableAutoFillInterceptor;
import io.entframework.kernel.db.dao.mybatis.interceptor.ShowSqlInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * Mybatis dynamic sql 插件
 */
@Configuration
@Slf4j
public class KernelMyBatisAutoConfiguration {

    // @Bean
    public ShowSqlInterceptor showSqlInterceptor() {
        return new ShowSqlInterceptor();
    }

    // @Bean
    public RecordableAutoFillInterceptor recordableAutoFillInterceptor() {
        return new RecordableAutoFillInterceptor();
    }

    // @Bean
    public MybatisDynamicInterceptor mybatisDynamicInterceptor() {
        return new MybatisDynamicInterceptor();
    }

    @Bean(name = "KernelMyBatisConfigurationCustomizer")
    public KernelMyBatisConfigurationCustomizer configurationCustomizer() {
        return new KernelMyBatisConfigurationCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean(DatabaseIdProvider.class)
    public DatabaseIdProvider databaseIdProvider() {
        return new VendorDatabaseIdProvider();
    }

}

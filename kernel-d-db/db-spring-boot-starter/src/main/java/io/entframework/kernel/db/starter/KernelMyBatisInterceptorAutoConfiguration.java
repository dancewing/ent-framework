/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.api.interceptor.ShowSqlInterceptor;
import io.entframework.kernel.db.mds.interceptor.MybatisDynamicInterceptor;
import io.entframework.kernel.db.mds.interceptor.RecordableAutoFillInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/***
 * Mybatis dynamic sql 插件
 */
@Configuration
@Slf4j
public class KernelMyBatisInterceptorAutoConfiguration {

    //@Bean
    public ShowSqlInterceptor showSqlInterceptor() {
        return new ShowSqlInterceptor();
    }

    //@Bean
    public RecordableAutoFillInterceptor recordableAutoFillInterceptor() {
        return new RecordableAutoFillInterceptor();
    }

    //@Bean
    public MybatisDynamicInterceptor mybatisDynamicInterceptor() {
        return new MybatisDynamicInterceptor();
    }
}

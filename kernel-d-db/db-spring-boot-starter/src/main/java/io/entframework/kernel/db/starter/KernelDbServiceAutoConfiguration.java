/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.api.DbOperatorApi;
import io.entframework.kernel.db.mds.dboperator.DbOperatorImpl;
import io.entframework.kernel.db.mds.mapper.MapperManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库连接池的配置
 * <p>
 * 如果系统中没有配DataSource，则系统默认加载Druid连接池，并开启Druid的监控
 *
 * @date 2020/11/30 22:24
 */
@Configuration
public class KernelDbServiceAutoConfiguration {

    @Bean
    @Primary
    public MapperManager mapperManager() {
        return new MapperManager();
    }

    @Bean
    @ConditionalOnMissingBean(DbOperatorApi.class)
    public DbOperatorApi dbOperatorApi() {
        return new DbOperatorImpl();
    }
}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.starter;

import com.alibaba.druid.pool.DruidDataSource;
import io.entframework.kernel.db.api.DbOperatorApi;
import io.entframework.kernel.db.api.config.DruidProperties;
import io.entframework.kernel.db.api.factory.DruidDatasourceFactory;
import io.entframework.kernel.db.dao.dboperator.DbOperatorImpl;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 数据库连接池的配置
 * <p>
 * 如果系统中没有配DataSource，则系统默认加载Druid连接池，并开启Druid的监控
 *
 * @author jeff_qian
 * @date 2020/11/30 22:24
 */
@Configuration
@EnableConfigurationProperties({DruidProperties.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class KernelDataSourceAutoConfiguration {

    /**
     * druid数据库连接池
     *
     * @date 2020/11/30 22:37
     */
    @Bean(initMethod = "init")
    public DruidDataSource dataSource(DruidProperties druidProperties) {
        return DruidDatasourceFactory.createDruidDataSource(druidProperties);
    }

    @Bean
    @ConditionalOnMissingBean(DbOperatorApi.class)
    public DbOperatorApi dbOperatorApi() {
        return new DbOperatorImpl();
    }
}

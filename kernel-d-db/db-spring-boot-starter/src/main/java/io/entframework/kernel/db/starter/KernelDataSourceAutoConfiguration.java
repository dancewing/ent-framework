/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.api.config.DruidProperties;
import io.entframework.kernel.db.api.factory.DruidDatasourceFactory;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.sql.DataSource;

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
@ConditionalOnMissingBean(DataSource.class)
public class KernelDataSourceAutoConfiguration {

    /**
     * druid数据库连接池
     *
     * @date 2020/11/30 22:37
     */
    @Bean(initMethod = "init")
    public DruidDataSource druidDataSource(DruidProperties druidProperties) {
        return DruidDatasourceFactory.createDruidDataSource(druidProperties);
    }

}

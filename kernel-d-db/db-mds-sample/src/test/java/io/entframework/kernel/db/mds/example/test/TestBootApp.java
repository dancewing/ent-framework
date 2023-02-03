/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.converter.config.KernelConverterAutoConfiguration;
import io.entframework.kernel.db.mds.example.config.SampleSpringAutoConfiguration;
import io.entframework.kernel.db.mds.mapper.MapperManager;
import io.entframework.kernel.db.starter.KernelMyBatisHandlerConfiguration;
import io.entframework.kernel.db.starter.KernelMyBatisInterceptorAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import({
        WebMvcAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        SqlInitializationAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        KernelMyBatisInterceptorAutoConfiguration.class,
        KernelMyBatisHandlerConfiguration.class,
        SpringUtil.class,
        SampleSpringAutoConfiguration.class,
        KernelConverterAutoConfiguration.class
})
public class TestBootApp {
    @Bean
    @Primary
    public MapperManager mapperManager() {
        return new MapperManager();
    }
}

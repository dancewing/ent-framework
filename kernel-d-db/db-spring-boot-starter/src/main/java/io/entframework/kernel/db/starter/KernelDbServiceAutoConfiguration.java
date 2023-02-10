/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.starter;


import io.entframework.kernel.db.dao.listener.EntityListener;
import io.entframework.kernel.db.dao.listener.EntityListeners;
import io.entframework.kernel.db.dao.listener.impl.DefaultAuditEntityListener;
import io.entframework.kernel.db.dao.listener.impl.IdAutoGeneratorEntityListener;
import io.entframework.kernel.db.dao.listener.impl.InitialDefaultValueEntityListener;
import io.entframework.kernel.db.dao.repository.DefaultGeneralRepository;
import io.entframework.kernel.db.dao.repository.GeneralRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 数据库连接池的配置
 * <p>
 * 如果系统中没有配DataSource，则系统默认加载Druid连接池，并开启Druid的监控
 *
 * @date 2020/11/30 22:24
 */
@Configuration
@MapperScan("io.entframework.kernel.db.mds.mapper")
public class KernelDbServiceAutoConfiguration {

    @Bean
    public EntityListener idAutoGeneratorEntityListener() {
        return new IdAutoGeneratorEntityListener();
    }

    @Bean
    public EntityListener defaultAuditEntityListener() {
        return new DefaultAuditEntityListener();
    }

    @Bean
    public EntityListener initialDefaultValueEntityListener() {
        return new InitialDefaultValueEntityListener();
    }

    @Bean
    public EntityListeners entityListener(ObjectProvider<EntityListener[]> entityListeners) {
        return new EntityListeners(List.of(entityListeners.getIfAvailable()));
    }

    @Bean
    public GeneralRepository generalRepository() {
        return new DefaultGeneralRepository();
    }
}

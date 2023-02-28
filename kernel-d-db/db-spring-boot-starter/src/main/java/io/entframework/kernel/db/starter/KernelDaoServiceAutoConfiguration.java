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
import io.entframework.kernel.db.dao.persistence.PersistenceManagedTypesScanner;
import io.entframework.kernel.db.dao.repository.DefaultGeneralRepository;
import io.entframework.kernel.db.dao.repository.GeneralRepository;
import io.entframework.kernel.db.mybatis.persistence.PersistenceManagedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 数据库连接池的配置
 * <p>
 * 如果系统中没有配DataSource，则系统默认加载Druid连接池，并开启Druid的监控
 *
 * @date 2020/11/30 22:24
 */
@Configuration
@MapperScan("io.entframework.kernel.db.mybatis.mapper")
public class KernelDaoServiceAutoConfiguration {

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
        EntityListener[] listeners = entityListeners.getIfAvailable();
        return new EntityListeners(listeners == null ? Collections.emptyList() : List.of(listeners));
    }

    @Bean
    public GeneralRepository generalRepository() {
        return new DefaultGeneralRepository();
    }

    @Configuration(proxyBeanMethods = false)
    static class PersistenceManagedTypesConfiguration {
        @Bean
        @Primary
        @ConditionalOnMissingBean
        static PersistenceManagedTypes persistenceManagedTypes(BeanFactory beanFactory, ResourceLoader resourceLoader) {
            String[] packagesToScan = getPackagesToScan(beanFactory);
            return new PersistenceManagedTypesScanner(resourceLoader).scan(packagesToScan);
        }

        private static String[] getPackagesToScan(BeanFactory beanFactory) {
            List<String> packages = EntityScanPackages.get(beanFactory).getPackageNames();
            if (packages.isEmpty() && AutoConfigurationPackages.has(beanFactory)) {
                packages = AutoConfigurationPackages.get(beanFactory);
            }
            return StringUtils.toStringArray(packages);
        }
    }
}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.starter;

import io.entframework.kernel.mongodb.api.MongoFileApi;
import io.entframework.kernel.mongodb.api.MongodbApi;
import io.entframework.kernel.mongodb.entity.KernelMapEntity;
import io.entframework.kernel.mongodb.file.entity.MongoFileEntity;
import io.entframework.kernel.mongodb.file.service.impl.MongoFileServiceImpl;
import io.entframework.kernel.mongodb.service.impl.KernelMapServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mongodb模块自动配置
 *
 * @date 2021/03/20 16:24
 */
@Configuration
public class KernelMongodbAutoConfiguration {

    /**
     * Mongodb 数据存储
     *
     * @date 2021/03/20 16:24
     */
    @Bean
    public MongodbApi<KernelMapEntity, String> mongodbApi() {
        return new KernelMapServiceImpl();
    }

    /**
     * Mongodb 文件管理
     *
     * @date 2021/03/20 16:24
     */
    @Bean
    public MongoFileApi<MongoFileEntity, String> mongoFileApi() {
        return new MongoFileServiceImpl();
    }

}


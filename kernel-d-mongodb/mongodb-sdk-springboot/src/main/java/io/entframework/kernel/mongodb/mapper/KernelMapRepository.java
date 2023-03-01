/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.mapper;

import io.entframework.kernel.mongodb.entity.KernelMapEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongodb 数据存储mapper
 *
 * @date 2021/03/20 16:24
 */
@Configuration
public interface KernelMapRepository extends MongoRepository<KernelMapEntity, String> {

}

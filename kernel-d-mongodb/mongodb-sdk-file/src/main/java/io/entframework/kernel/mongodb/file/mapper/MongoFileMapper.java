/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.file.mapper;

import io.entframework.kernel.mongodb.file.entity.MongoFileEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongodb 文件存储Mapper
 *
 * @date 2021/03/26 17:27
 */
@Configuration
public interface MongoFileMapper extends MongoRepository<MongoFileEntity, String> {

}

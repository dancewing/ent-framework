/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.mongodb.service.impl;

import io.entframework.kernel.mongodb.api.MongodbApi;
import io.entframework.kernel.mongodb.entity.KernelMapEntity;
import io.entframework.kernel.mongodb.mapper.KernelMapRepository;
import io.entframework.kernel.mongodb.service.KernelMapService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Optional;

/**
 * Mongodb 数据存储实现类
 *
 * @date 2021/03/20 16:24
 */
@Service
public class KernelMapServiceImpl implements KernelMapService, MongodbApi<KernelMapEntity, String> {

    @Resource
    private KernelMapRepository kernelMapRepository;

    @Override
    public KernelMapEntity insert(KernelMapEntity kernelMapEntity) {
        return kernelMapRepository.insert(kernelMapEntity);
    }

    @Override
    public KernelMapEntity update(KernelMapEntity kernelMapEntity) {
        return kernelMapRepository.save(kernelMapEntity);
    }

    @Override
    public void deleteById(String id) {
        kernelMapRepository.deleteById(id);
    }

    @Override
    public Optional<KernelMapEntity> findById(String id) {
        return kernelMapRepository.findById(id);
    }

    @Override
    public List<KernelMapEntity> findAll() {
        return kernelMapRepository.findAll();
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.service;

import io.entframework.kernel.mongodb.entity.KernelMapEntity;

import java.util.List;
import java.util.Optional;

/**
 * Mongodb 数据存储接口
 *
 * @date 2021/03/20 16:24
 */
public interface KernelMapService {

    /**
     * 新增数据
     *
     * @param kernelMapEntity 数据参数
     * @return 返回新增数据结果
     * @date 2021/03/20 16:24
     */
    KernelMapEntity insert(KernelMapEntity kernelMapEntity);

    /**
     * 修改数据
     *
     * @param kernelMapEntity 数据参数
     * @return 返回修改数据结果
     * @date 2021/03/20 16:24
     */
    KernelMapEntity update(KernelMapEntity kernelMapEntity);

    /**
     * 根据id删除
     *
     * @param id 集合id
     * @date 2021/03/20 16:24
     */
    void deleteById(String id);

    /**
     * 根据id查询
     *
     * @param id 集合id
     * @return 返回查询到数据的Optional
     * @date 2021/03/20 16:24
     */
    Optional<KernelMapEntity> findById(String id);

    /**
     * 查询所有集合中数据
     *
     * @return 返回所有数据集合
     * @date 2021/03/20 16:24
     */
    List<KernelMapEntity> findAll();

}

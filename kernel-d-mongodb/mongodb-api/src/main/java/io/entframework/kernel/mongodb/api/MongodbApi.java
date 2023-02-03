/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.api;

import java.util.List;
import java.util.Optional;

/**
 * Mongodb数据存储的api，适用于存map类型数据，存储在guns_map集合中
 *
 * @date 2021/03/20 16:24
 */
public interface MongodbApi<T, ID> {

    /**
     * 新增数据
     *
     * @param gunsMapEntity 数据存储的参数
     * @return 返回数据结果
     * @date 2020/10/27 17:38
     */
    T insert(T gunsMapEntity);

    /**
     * 修改数据
     *
     * @param gunsMapEntity 数据存储的参数
     * @return 返回数据结果
     * @date 2020/10/27 17:38
     */
    T update(T gunsMapEntity);

    /**
     * 根据id删除
     *
     * @param id 集合id
     * @date 2020/10/27 17:38
     */
    void deleteById(ID id);

    /**
     * 根据id查询
     *
     * @param id 集合id
     * @return 返回查询到数据的Optional
     * @date 2020/10/27 17:38
     */
    Optional<T> findById(ID id);

    /**
     * 查询所有数据
     *
     * @return 返回所有数据列表
     * @date 2020/10/27 17:38
     */
    List<T> findAll();
}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.seata.api;

/**
 * 仓储 api
 *
 * @date 2021/04/10 16:42
 */
public interface StorageApi {

    /**
     * 扣除存储数量
     *
     * @param commodityCode 商品编码
     * @param count         购买数量
     * @date 2021/4/21 9:44
     */
    void deduct(String commodityCode, Integer count);

}

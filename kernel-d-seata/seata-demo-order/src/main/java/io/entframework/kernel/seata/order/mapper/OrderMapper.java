/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.seata.order.mapper;

import io.entframework.kernel.seata.order.entity.Order;

/**
 * 订单 数据层
 *
 * @date 2021/04/21 08:33
 */
public interface OrderMapper {

    /**
     * 新增订单
     * @param order 订单
     * @date 2021/4/21 9:43
     */
    void insertOrder(Order order);

    /**
     * 根据ID查询订单
     * @param orderId 订单id
     * @date 2021/4/21 9:43
     */
    Order selectById(Long orderId);

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.seata.order.service.impl;

import io.entframework.kernel.seata.order.consumer.StorageConsumer;
import io.entframework.kernel.seata.order.consumer.WalletConsumer;
import io.entframework.kernel.seata.order.entity.Order;
import io.entframework.kernel.seata.order.mapper.OrderMapper;
import io.entframework.kernel.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 订单 业务层
 *
 * @date 2021/04/21 08:33
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private StorageConsumer storageConsumer;

	@Resource
	private WalletConsumer walletConsumer;

	@Resource
	private OrderMapper orderMapper;

	@GlobalTransactional(rollbackFor = Exception.class)
	@Override
	public Order create(String userId, String commodityCode, int orderCount) {
		Order order = new Order();

		// 保存订单
		orderMapper.insertOrder(order);

		// 扣减商品库存
		storageConsumer.deduct(commodityCode, orderCount);

		// 扣用户钱
		walletConsumer.debit(userId, order.getTotalAmount());

		return order;
	}

}

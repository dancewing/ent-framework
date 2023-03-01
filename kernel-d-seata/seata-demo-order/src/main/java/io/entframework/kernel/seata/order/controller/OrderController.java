/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.seata.order.controller;

import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.seata.order.entity.Order;
import io.entframework.kernel.seata.order.service.OrderService;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;

/**
 * 订单接口
 *
 * @date 2021/04/10 16:42
 */
@ApiResource(name = "订单接口（测试seata）")
public class OrderController {

	@Resource
	private OrderService orderService;

	/**
	 * 创建订单
	 *
	 * @date 2021/4/20 20:11
	 */
	@GetResource(name = "创建订单", path = "/order/create", requiredPermission = false, requiredLogin = false)
	public Order create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
			@RequestParam("orderCount") Integer orderCount) {
		return orderService.create(userId, commodityCode, orderCount);
	}

}

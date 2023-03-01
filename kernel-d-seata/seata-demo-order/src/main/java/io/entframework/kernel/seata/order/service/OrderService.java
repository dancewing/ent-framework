/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.seata.order.service;

import io.entframework.kernel.seata.order.entity.Order;

/**
 * 订单 业务层
 *
 * @date 2021/04/21 08:33
 */
public interface OrderService {

	/**
	 * 创建订单
	 * @param userId 用户ID
	 * @param commodityCode 商品编码
	 * @param orderCount 购买数量
	 * @date 2021/4/21 9:43
	 */
	Order create(String userId, String commodityCode, int orderCount);

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.pojo.prometheus;

import lombok.Data;

import java.util.List;

/**
 * prometheus查询返回结果信息
 *
 * @date 2021/1/10 19:10
 */
@Data
public class PromDataInfo {

	/**
	 * prometheus结果类型:
	 * <p>
	 * vector -- 瞬时向量 matrix -- 区间向量 scalar -- 标量 string -- 字符串
	 */
	private String resultType;

	/**
	 * prometheus指标属性和值
	 */
	private List<PromResultInfo> result;

}

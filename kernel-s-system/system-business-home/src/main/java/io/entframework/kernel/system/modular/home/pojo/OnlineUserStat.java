/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.modular.home.pojo;

import lombok.Data;

import java.util.Set;

/**
 * 在线用户统计
 *
 * @date 2022/2/11 10:54
 */
@Data
public class OnlineUserStat {

	/**
	 * 总在线用户
	 */
	private Integer totalNum;

	/**
	 * 总的人员姓名汇总
	 */
	private Set<String> totalUserNames;

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.log.api.pojo.loginlog;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志的dto
 *
 * @date 2021/3/30 20:51
 */
@Data
public class SysLoginLogResponse {

	/**
	 * 主键id
	 */
	private Long llgId;

	/**
	 * 日志名称
	 */
	private String llgName;

	/**
	 * 是否执行成功
	 */
	private String llgSucceed;

	/**
	 * 具体消息
	 */
	private String llgMessage;

	/**
	 * 登录ip
	 */
	private String llgIpAddress;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 登录姓名
	 */
	private String loginAccount;

	/**
	 * 开始时间
	 */
	private LocalDateTime createTime;

}

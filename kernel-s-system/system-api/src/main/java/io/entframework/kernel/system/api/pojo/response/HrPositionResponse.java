/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.response;

import java.math.BigDecimal;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.enums.StatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统职位表
 *
 * @date 2020/11/04 11:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HrPositionResponse extends BaseResponse {

	/**
	 * 主键
	 */
	private Long positionId;

	/**
	 * 职位名称
	 */
	private String positionName;

	/**
	 * 职位编码
	 */
	private String positionCode;

	/**
	 * 排序
	 */
	private BigDecimal positionSort;

	/**
	 * 状态：1-启用，2-禁用
	 */
	private StatusEnum statusFlag;

	/**
	 * 职位备注
	 */
	private String positionRemark;

	/**
	 * 删除标记：Y-已删除，N-未删除
	 */
	private String delFlag;

}

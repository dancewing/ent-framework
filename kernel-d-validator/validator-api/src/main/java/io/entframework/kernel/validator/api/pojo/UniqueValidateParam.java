/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.validator.api.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 校验参数时用的方法参数
 *
 * @date 2020/8/17 21:43
 */
@Data
@Builder
public class UniqueValidateParam {

	/**
	 * 表名称
	 */
	String tableName;

	/**
	 * 列名称
	 */
	String columnName;

	/**
	 * 被参数校验时候的字段的值
	 */
	Object value;

	/**
	 * 校验时，是否排除当前的记录
	 */
	Boolean excludeCurrentRecord;

	/**
	 * 主键id的字段名
	 */
	String idFieldName;

	/**
	 * 当前记录的主键id
	 */
	Long id;

	/**
	 * 排除所有被逻辑删除的记录的控制
	 */
	Boolean excludeLogicDeleteItems;

	/**
	 * 逻辑删除的字段名
	 */
	String logicDeleteFieldName;

	/**
	 * 逻辑删除的字段的值
	 */
	String logicDeleteValue;

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.api.enums;

import lombok.Getter;

/**
 * 迁移类型枚举
 *
 * @date 2021/7/6 16:25
 */
@Getter
public enum MigrationAggregationTypeEnum {

	/**
	 * 全量迁移
	 */
	MIGRATION_FULL("FULL", "全量迁移"),

	/**
	 * 增量迁移
	 */
	MIGRATION_INCREMENTAL("INCREMENTAL", "增量迁移");

	private final String code;

	private final String name;

	MigrationAggregationTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

}

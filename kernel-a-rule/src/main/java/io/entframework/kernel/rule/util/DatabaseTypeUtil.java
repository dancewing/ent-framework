/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.util;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.enums.DbTypeEnum;

/**
 * 判断数据库类型的工具
 *
 * @date 2021/3/27 21:24
 */
public class DatabaseTypeUtil {

	/**
	 * 判断数据库类型
	 *
	 * @date 2021/3/27 21:25
	 */
	public static DbTypeEnum getDbType(String jdbcUrl) {
		if (CharSequenceUtil.isEmpty(jdbcUrl)) {
			return DbTypeEnum.MYSQL;
		}

		// url字符串中包含了dbTypeEnum的name，则判定为该类型
		for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
			if (jdbcUrl.contains(dbTypeEnum.getUrlWords())) {
				return dbTypeEnum;
			}
		}

		return DbTypeEnum.MYSQL;
	}

}

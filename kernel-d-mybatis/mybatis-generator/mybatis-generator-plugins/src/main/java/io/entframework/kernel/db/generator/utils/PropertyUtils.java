package io.entframework.kernel.db.generator.utils;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

public class PropertyUtils {

	public static String getProperty(Context context, String propertyName, String defaultVal) {
		String property = context.getProperty(propertyName);
		if (!StringUtility.stringHasValue(property)) {
			return defaultVal;
		}
		return property;
	}

}

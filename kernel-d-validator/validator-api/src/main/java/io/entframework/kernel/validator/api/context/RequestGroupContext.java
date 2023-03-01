/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.validator.api.context;

/**
 * 保存控制器的方法上的校验组，group class
 *
 * @date 2020/11/4 14:31
 */
public class RequestGroupContext {

	private static final ThreadLocal<Class<?>> GROUP_CLASS_HOLDER = new ThreadLocal<>();

	/**
	 * 设置临时的校验分组
	 *
	 * @date 2020/11/4 14:32
	 */
	public static void set(Class<?> groupValue) {
		GROUP_CLASS_HOLDER.set(groupValue);
	}

	/**
	 * 获取临时校验分组
	 *
	 * @date 2020/11/4 14:32
	 */
	public static Class<?> get() {
		return GROUP_CLASS_HOLDER.get();
	}

	/**
	 * 清除临时缓存的校验分组
	 *
	 * @date 2020/11/4 14:32
	 */
	public static void clear() {
		GROUP_CLASS_HOLDER.remove();
	}

}

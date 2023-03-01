/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pinyin.api.constants;

/**
 * 拼音模块常量
 *
 * @date 2020/12/3 19:24
 */
public interface PinyinConstants {

	/**
	 * 邮件模块的名称
	 */
	String PINYIN_MODULE_NAME = "kernel-d-pinyin";

	/**
	 * 异常枚举的步进值
	 */
	String PINYIN_EXCEPTION_STEP_CODE = "22";

	/**
	 * 中文字符的正则表达式
	 */
	String CHINESE_WORDS_REGEX = "[\u4E00-\u9FA5]+";

}

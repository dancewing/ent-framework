/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pinyin.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.pinyin.api.PinYinApi;

/**
 * 拼音工具类快速获取
 *
 * @date 2020/12/4 9:31
 */
public class PinyinContext {

    /**
     * 获取拼音工具类
     *
     * @date 2020/12/4 9:36
     */
    public static PinYinApi me() {
        return SpringUtil.getBean(PinYinApi.class);
    }

}

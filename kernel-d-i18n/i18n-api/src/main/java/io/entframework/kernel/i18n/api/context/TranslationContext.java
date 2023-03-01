/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.i18n.api.TranslationApi;

/**
 * 翻译上下文获取
 *
 * @date 2021/1/24 19:06
 */
public class TranslationContext {

    private TranslationContext() {
    }

    /**
     * 获取翻译接口
     *
     * @date 2021/1/24 19:06
     */
    public static TranslationApi me() {
        return SpringUtil.getBean(TranslationApi.class);
    }

}

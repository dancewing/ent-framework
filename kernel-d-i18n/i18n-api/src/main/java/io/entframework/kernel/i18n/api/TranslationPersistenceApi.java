/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.api;

import io.entframework.kernel.i18n.api.pojo.TranslationDict;

import java.util.List;

/**
 * 多语言字典持久化api
 *
 * @date 2021/1/24 19:32
 */
public interface TranslationPersistenceApi {

    /**
     * 获取所有的翻译项
     *
     * @date 2021/1/24 19:33
     */
    List<TranslationDict> getAllTranslationDict();

}

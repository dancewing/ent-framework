/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.modular.factory;

import io.entframework.kernel.i18n.api.pojo.TranslationDict;
import io.entframework.kernel.i18n.api.pojo.response.TranslationResponse;

/**
 * 创建翻译字典
 *
 * @date 2021/1/24 21:50
 */
public class TranslationDictFactory {

    private TranslationDictFactory(){}

    /**
     * 创建翻译字典
     *
     * @date 2021/1/24 21:50
     */
    public static TranslationDict createTranslationDict(String translationLanguages, TranslationResponse translation) {
        TranslationDict translationDict = new TranslationDict();
        translationDict.setTranName(translation.getTranName());
        translationDict.setTranCode(translation.getTranCode());
        translationDict.setTranValue(translation.getTranValue());
        translationDict.setTranLanguageCode(translationLanguages);
        return translationDict;
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.listener;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.i18n.api.TranslationApi;
import io.entframework.kernel.i18n.api.TranslationPersistenceApi;
import io.entframework.kernel.i18n.api.pojo.TranslationDict;
import io.entframework.kernel.rule.listener.ApplicationStartedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import java.util.List;

/**
 * 初始化多语言翻译详
 *
 * @date 2021/1/24 19:36
 */
@Slf4j
public class TranslationDictInitListener extends ApplicationStartedListener {

	@Override
	public void eventCallback(ApplicationStartedEvent event) {

		TranslationPersistenceApi tanTranslationPersistenceApi = SpringUtil.getBean(TranslationPersistenceApi.class);
		TranslationApi translationApi = SpringUtil.getBean(TranslationApi.class);

		// 从数据库读取翻译字典
		List<TranslationDict> allTranslationDict = tanTranslationPersistenceApi.getAllTranslationDict();
		if (allTranslationDict != null) {
			translationApi.init(allTranslationDict);
			log.debug("初始化所有的翻译字典" + allTranslationDict.size() + "条！");
		}
	}

}

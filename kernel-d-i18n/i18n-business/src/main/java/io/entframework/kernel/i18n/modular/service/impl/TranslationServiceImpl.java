/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.i18n.modular.service.impl;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.dict.api.DictApi;
import io.entframework.kernel.i18n.api.context.TranslationContext;
import io.entframework.kernel.i18n.api.exception.TranslationException;
import io.entframework.kernel.i18n.api.exception.enums.TranslationExceptionEnum;
import io.entframework.kernel.i18n.api.pojo.TranslationDict;
import io.entframework.kernel.i18n.api.pojo.request.TranslationRequest;
import io.entframework.kernel.i18n.api.pojo.response.TranslationResponse;
import io.entframework.kernel.i18n.modular.entity.Translation;
import io.entframework.kernel.i18n.modular.factory.TranslationDictFactory;
import io.entframework.kernel.i18n.modular.service.TranslationService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 多语言管理业务
 *
 * @date 2021/1/24 19:38
 */
public class TranslationServiceImpl extends BaseServiceImpl<TranslationRequest, TranslationResponse, Translation>
		implements TranslationService {

	@Resource
	private DictApi dictApi;

	public TranslationServiceImpl() {
		super(TranslationRequest.class, TranslationResponse.class, Translation.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(TranslationRequest translationRequest) {
		Translation translation = this.converterService.convert(translationRequest, getEntityClass());
		this.getRepository().insert(translation);
		// 更新翻译的缓存
		this.saveContext(this.converterService.convert(translation, getResponseClass()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TranslationResponse update(TranslationRequest translationRequest) {
		Translation translation = this.queryTranslationById(translationRequest);
		this.converterService.copy(translationRequest, translation);
		this.getRepository().updateByPrimaryKey(translation);

		// 更新翻译的缓存
		TranslationResponse translationResponse = this.converterService.convert(translation, getResponseClass());
		this.saveContext(translationResponse);
		return translationResponse;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(TranslationRequest translationRequest) {
		Translation translation = this.queryTranslationById(translationRequest);
		this.getRepository().deleteByPrimaryKey(getEntityClass(), translationRequest.getTranId());

		// 删除对应缓存
		TranslationContext.me().deleteTranslationDict(translation.getTranLanguageCode(), translation.getTranCode());
	}

	@Override
	public TranslationResponse detail(TranslationRequest translationRequest) {
		return this.converterService.convert(this.queryTranslationById(translationRequest), getResponseClass());
	}

	@Override
	public List<TranslationResponse> findList(TranslationRequest translationRequest) {
		return this.select(translationRequest);
	}

	@Override
	public PageResult<TranslationResponse> findPage(TranslationRequest translationRequest) {
		return this.page(translationRequest);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTranLanguage(TranslationRequest translationRequest) {

		// 删除对应的字典信息
		dictApi.deleteByDictId(translationRequest.getDictId());

		// 删除该语言下的所有翻译项
		this.deleteBy(translationRequest);
	}

	@Override
	public List<TranslationDict> getAllTranslationDict() {
		List<TranslationResponse> list = this.select(new TranslationRequest());
		ArrayList<TranslationDict> translationDictList = new ArrayList<>();
		for (TranslationResponse translation : list) {
			TranslationDict translationDict = TranslationDictFactory
					.createTranslationDict(translation.getTranLanguageCode(), translation);
			translationDictList.add(translationDict);
		}
		return translationDictList;
	}

	/**
	 * 根据主键id获取对象
	 *
	 * @date 2021/1/26 13:28
	 */
	private Translation queryTranslationById(TranslationRequest translationRequest) {
		Optional<Translation> translation = this.getRepository().selectByPrimaryKey(getEntityClass(),
				translationRequest.getTranId());
		if (translation.isEmpty()) {
			throw new TranslationException(TranslationExceptionEnum.NOT_EXISTED, translationRequest.getTranId());
		}
		return translation.get();
	}

	/**
	 * 更新翻译的缓存
	 *
	 * @date 2021/1/26 13:45
	 */
	private void saveContext(TranslationResponse translation) {

		// 没有对应的语种，不添加到context
		if (translation.getTranLanguageCode() == null) {
			return;
		}

		TranslationDict translationDict = TranslationDictFactory
				.createTranslationDict(translation.getTranLanguageCode(), translation);
		TranslationContext.me().addTranslationDict(translationDict);
	}

}

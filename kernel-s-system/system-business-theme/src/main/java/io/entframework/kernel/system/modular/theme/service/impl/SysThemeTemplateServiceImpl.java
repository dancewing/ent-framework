/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeTemplateExceptionEnum;
import io.entframework.kernel.system.api.pojo.theme.SysThemeRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateResponse;
import io.entframework.kernel.system.modular.theme.entity.*;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * 系统主题模板service接口实现类
 *
 * @date 2021/12/17 13:58
 */
public class SysThemeTemplateServiceImpl
		extends BaseServiceImpl<SysThemeTemplateRequest, SysThemeTemplateResponse, SysThemeTemplate>
		implements SysThemeTemplateService {

	public SysThemeTemplateServiceImpl() {
		super(SysThemeTemplateRequest.class, SysThemeTemplateResponse.class, SysThemeTemplate.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysThemeTemplateRequest sysThemeTemplateRequest) {
		SysThemeTemplate sysThemeTemplate = new SysThemeTemplate();

		// 拷贝属性
		BeanUtil.copyProperties(sysThemeTemplateRequest, sysThemeTemplate);

		// 默认启用状态：禁用N
		sysThemeTemplate.setStatusFlag(YesOrNotEnum.N);

		this.getRepository().insert(sysThemeTemplate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysThemeTemplateResponse update(SysThemeTemplateRequest sysThemeTemplateRequest) {
		SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

		if (YesOrNotEnum.Y == sysThemeTemplate.getStatusFlag()) {
			throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_USED);
		}

		// 拷贝属性
		BeanUtil.copyProperties(sysThemeTemplateRequest, sysThemeTemplate);

		this.getRepository().updateByPrimaryKey(sysThemeTemplate);

		return this.converterService.convert(sysThemeTemplate, getResponseClass());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysThemeTemplateRequest sysThemeTemplateRequest) {
		SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

		// Guns开头的模板字段不能删除，系统内置
		if (sysThemeTemplateRequest.getTemplateCode().toUpperCase(Locale.ROOT)
				.startsWith(SystemConstants.THEME_CODE_SYSTEM_PREFIX)) {
			throw new SystemModularException(SysThemeExceptionEnum.THEME_IS_SYSTEM);
		}

		// 启动的主题模板不能删除
		if (YesOrNotEnum.Y == sysThemeTemplate.getStatusFlag()) {
			throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_ENABLE);
		}

		// 删除关联关系
		getRepository().delete(SysThemeTemplateRel.class, c -> c.where(SysThemeTemplateRelDynamicSqlSupport.templateId,
				SqlBuilder.isEqualTo(sysThemeTemplate.getTemplateId())));

		// 删除模板
		this.getRepository().deleteByPrimaryKey(getEntityClass(), sysThemeTemplate.getTemplateId());
	}

	@Override
	public PageResult<SysThemeTemplateResponse> findPage(SysThemeTemplateRequest sysThemeTemplateRequest) {
		return this.page(sysThemeTemplateRequest);
	}

	@Override
	public List<SysThemeTemplateResponse> findList(SysThemeTemplateRequest sysThemeTemplateRequest) {
		return this.select(sysThemeTemplateRequest);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTemplateStatus(SysThemeTemplateRequest sysThemeTemplateRequest) {
		SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

		// 系统主题模板被使用，不允许禁用
		SysThemeRequest request = new SysThemeRequest();
		request.setTemplateId(sysThemeTemplate.getTemplateId());
		long sysThemeNum = getRepository().count(SysTheme.class, c -> c.where(SysThemeDynamicSqlSupport.templateId,
				SqlBuilder.isEqualTo(sysThemeTemplate.getTemplateId())));
		if (sysThemeNum > 0) {
			throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_USED);
		}

		// 修改状态
		if (YesOrNotEnum.Y == sysThemeTemplate.getStatusFlag()) {
			sysThemeTemplate.setStatusFlag(YesOrNotEnum.N);
		}
		else {
			// 如果该模板没有属性不允许启用
			List<SysThemeTemplateRel> sysThemeTemplateRels = getRepository().select(SysThemeTemplateRel.class,
					c -> c.where(SysThemeTemplateRelDynamicSqlSupport.templateId,
							SqlBuilder.isEqualTo(sysThemeTemplate.getTemplateId())));

			if (sysThemeTemplateRels.isEmpty()) {
				throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_NOT_ATTRIBUTE);
			}

			sysThemeTemplate.setStatusFlag(YesOrNotEnum.Y);
		}

		this.getRepository().updateByPrimaryKey(sysThemeTemplate);
	}

	@Override
	public List<SysThemeTemplateFieldResponse> detail(SysThemeTemplateRequest sysThemeTemplateRequest) {
		// return
		// sysThemeTemplateMapper.sysThemeTemplateDetail(sysThemeTemplateRequest.getTemplateId());
		throw new RuntimeException();
	}

	/**
	 * 查询单个系统主题模板
	 *
	 * @date 2021/12/17 14:28
	 */
	private SysThemeTemplate querySysThemeTemplateById(SysThemeTemplateRequest sysThemeTemplateRequest) {
		Optional<SysThemeTemplate> sysThemeTemplate = this.getRepository().selectByPrimaryKey(getEntityClass(),
				sysThemeTemplateRequest.getTemplateId());
		if (!sysThemeTemplate.isPresent()) {
			throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_NOT_EXIT);
		}
		return sysThemeTemplate.get();
	}

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service.impl;

import io.entframework.kernel.db.mds.repository.BaseRepository;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeTemplateExceptionEnum;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelResponse;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import io.entframework.kernel.system.modular.theme.mapper.SysThemeTemplateRelDynamicSqlSupport;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统主题模板属性关系service接口实现类
 *
 * @date 2021/12/17 16:14
 */
public class SysThemeTemplateRelServiceImpl extends BaseServiceImpl<SysThemeTemplateRelRequest, SysThemeTemplateRelResponse, SysThemeTemplateRel>
		implements SysThemeTemplateRelService {

	@Resource
	private SysThemeTemplateService sysThemeTemplateService;

	public SysThemeTemplateRelServiceImpl(BaseRepository<SysThemeTemplateRel> baseRepository) {
		super(baseRepository, SysThemeTemplateRelRequest.class, SysThemeTemplateRelResponse.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysThemeTemplateRelRequest sysThemeTemplateRelRequest) {
		// 校验模板状态
		this.checkTemplateStatus(sysThemeTemplateRelRequest);

		// 获取请求中的所有属性编码
		String[] fieldCodes = sysThemeTemplateRelRequest.getFieldCodes();

		List<SysThemeTemplateRel> sysThemeTemplateRels = new ArrayList<>();

		// 填充对象
		for (String fieldCode : fieldCodes) {
			SysThemeTemplateRel sysThemeTemplateRel = new SysThemeTemplateRel();
			sysThemeTemplateRel.setTemplateId(sysThemeTemplateRelRequest.getTemplateId());
			sysThemeTemplateRel.setFieldCode(fieldCode);

			sysThemeTemplateRels.add(sysThemeTemplateRel);
		}

		// 保存关系
		this.getRepository().insertMultiple(sysThemeTemplateRels);
	}

	/**
	 * 校验模板使用状态
	 *
	 * @date 2021/12/30 17:28
	 */
	private void checkTemplateStatus(SysThemeTemplateRelRequest sysThemeTemplateRelRequest) {
		// 判断当前模板是否被使用
		SysThemeTemplateRequest request = new SysThemeTemplateRequest();
		request.setTemplateId(sysThemeTemplateRelRequest.getTemplateId());
		SysThemeTemplateResponse sysThemeTemplate = sysThemeTemplateService.selectOne(request);
		// 判断状态，如果是启用则禁止操作
		if (YesOrNotEnum.Y == sysThemeTemplate.getStatusFlag()) {
			throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_USED);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysThemeTemplateRelRequest sysThemeTemplateRelRequest) {
		// 校验模板状态
		this.checkTemplateStatus(sysThemeTemplateRelRequest);

		// 获取请求中的所有属性编码
		String[] fieldCodes = sysThemeTemplateRelRequest.getFieldCodes();

		// 构建删除条件
		this.getRepository().delete(c -> c.where(SysThemeTemplateRelDynamicSqlSupport.fieldCode, SqlBuilder.isIn(fieldCodes)));
	}

	@Override
	public List<SysThemeTemplateRel> select(SelectDSLCompleter completer) {
		return this.getRepository().select(completer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(DeleteDSLCompleter completer) {
		return this.getRepository().delete(completer);
	}
}

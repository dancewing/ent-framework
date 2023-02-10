/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import io.entframework.kernel.system.modular.home.enums.SysStatisticsCountExceptionEnum;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsCountRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsCountResponse;
import io.entframework.kernel.system.modular.home.service.SysStatisticsCountService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 常用功能的统计次数业务实现层
 *
 * @date 2022/02/10 21:17
 */
public class SysStatisticsCountServiceImpl extends BaseServiceImpl<SysStatisticsCountRequest, SysStatisticsCountResponse, SysStatisticsCount>
		implements SysStatisticsCountService {

	public SysStatisticsCountServiceImpl() {
		super(SysStatisticsCountRequest.class, SysStatisticsCountResponse.class, SysStatisticsCount.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysStatisticsCountRequest sysStatisticsCountRequest) {
		SysStatisticsCount sysStatisticsCount = new SysStatisticsCount();
		BeanUtil.copyProperties(sysStatisticsCountRequest, sysStatisticsCount);
		this.getRepository().insert(sysStatisticsCount);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysStatisticsCountRequest sysStatisticsCountRequest) {
		SysStatisticsCount sysStatisticsCount = this.querySysStatisticsCount(sysStatisticsCountRequest);
		this.getRepository().deleteByPrimaryKey(getEntityClass(), sysStatisticsCount.getStatCountId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysStatisticsCountResponse update(SysStatisticsCountRequest sysStatisticsCountRequest) {
		SysStatisticsCount sysStatisticsCount = this.querySysStatisticsCount(sysStatisticsCountRequest);
		BeanUtil.copyProperties(sysStatisticsCountRequest, sysStatisticsCount);
		this.getRepository().updateByPrimaryKey(sysStatisticsCount);
		return this.converterService.convert(sysStatisticsCount, getResponseClass());
	}

	@Override
	public SysStatisticsCountResponse detail(SysStatisticsCountRequest sysStatisticsCountRequest) {
		return this.converterService.convert(this.querySysStatisticsCount(sysStatisticsCountRequest), getResponseClass());
	}

	@Override
	public PageResult<SysStatisticsCountResponse> findPage(SysStatisticsCountRequest request) {
		return this.page(request);
	}

	@Override
	public Integer getUserUrlCount(Long userId, Long statUrlId) {
		if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(statUrlId)) {
			throw new SystemModularException(SysStatisticsCountExceptionEnum.PARAM_EMPTY);
		}
		SysStatisticsCountRequest request = new SysStatisticsCountRequest();
		request.setUserId(userId);
		request.setStatUrlId(statUrlId);
		SysStatisticsCountResponse one = this.selectOne(request);
		if (one != null) {
			return one.getStatCount();
		} else {
			return 0;
		}
	}

	@Override
	public List<SysStatisticsCountResponse> findList(SysStatisticsCountRequest request) {
		return this.select(request);
	}

	/**
	 * 获取信息
	 *
	 * @date 2022/02/10 21:17
	 */
	private SysStatisticsCount querySysStatisticsCount(SysStatisticsCountRequest sysStatisticsCountRequest) {
		Optional<SysStatisticsCount> sysStatisticsCount = this.getRepository().selectByPrimaryKey(getEntityClass(), sysStatisticsCountRequest.getStatCountId());
		if (sysStatisticsCount.isEmpty()) {
			throw new ServiceException(SysStatisticsCountExceptionEnum.SYS_STATISTICS_COUNT_NOT_EXISTED);
		}
		return sysStatisticsCount.get();
	}

}

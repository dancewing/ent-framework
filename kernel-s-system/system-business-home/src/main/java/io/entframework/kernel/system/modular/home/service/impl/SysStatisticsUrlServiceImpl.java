/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrlDynamicSqlSupport;
import io.entframework.kernel.system.modular.home.enums.SysStatisticsUrlExceptionEnum;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsUrlRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsUrlResponse;
import io.entframework.kernel.system.modular.home.service.SysStatisticsUrlService;
import org.mybatis.dynamic.sql.SqlBuilder;

import java.util.List;
import java.util.Optional;

/**
 * 常用功能列表业务实现层
 *
 * @date 2022/02/10 21:17
 */
public class SysStatisticsUrlServiceImpl extends BaseServiceImpl<SysStatisticsUrlRequest, SysStatisticsUrlResponse, SysStatisticsUrl> implements SysStatisticsUrlService {

    public SysStatisticsUrlServiceImpl() {
        super(SysStatisticsUrlRequest.class, SysStatisticsUrlResponse.class, SysStatisticsUrl.class);
    }

    @Override
    public void add(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        SysStatisticsUrl sysStatisticsUrl = new SysStatisticsUrl();
        BeanUtil.copyProperties(sysStatisticsUrlRequest, sysStatisticsUrl);
        this.getRepository().insert(sysStatisticsUrl);
    }

    @Override
    public void del(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        SysStatisticsUrl sysStatisticsUrl = this.querySysStatisticsUrl(sysStatisticsUrlRequest);
        this.getRepository().deleteByPrimaryKey(getEntityClass(), sysStatisticsUrl.getStatUrlId());
    }

    @Override
    public void edit(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        SysStatisticsUrl sysStatisticsUrl = this.querySysStatisticsUrl(sysStatisticsUrlRequest);
        BeanUtil.copyProperties(sysStatisticsUrlRequest, sysStatisticsUrl);
        this.getRepository().updateByPrimaryKey(sysStatisticsUrl);
    }

    @Override
    public SysStatisticsUrlResponse detail(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        SysStatisticsUrl row = this.querySysStatisticsUrl(sysStatisticsUrlRequest);
        return this.converterService.convert(row, getResponseClass());
    }

    @Override
    public PageResult<SysStatisticsUrlResponse> findPage(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        return this.page(sysStatisticsUrlRequest);
    }

    @Override
    public List<SysStatisticsUrlResponse> findList(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        return this.select(sysStatisticsUrlRequest);
    }

    /**
     * 获取信息
     *
     * @date 2022/02/10 21:17
     */
    private SysStatisticsUrl querySysStatisticsUrl(SysStatisticsUrlRequest sysStatisticsUrlRequest) {
        Optional<SysStatisticsUrl> sysStatisticsUrl = this.getRepository().selectByPrimaryKey(getEntityClass(), sysStatisticsUrlRequest.getStatUrlId());
        if (sysStatisticsUrl.isEmpty()) {
            throw new ServiceException(SysStatisticsUrlExceptionEnum.SYS_STATISTICS_URL_NOT_EXISTED);
        }
        return sysStatisticsUrl.get();
    }

    @Override
    public List<Long> getMenuIdsByStatUrlIdList(List<Long> statUrlIds) {
        List<SysStatisticsUrl> results = this.getRepository().select(getEntityClass(), c -> c.where(SysStatisticsUrlDynamicSqlSupport.statUrlId, SqlBuilder.isIn(statUrlIds)).orderBy(SysStatisticsUrlDynamicSqlSupport.statUrlId));
        return results.stream().map(SysStatisticsUrl::getStatMenuId).toList();
    }
}

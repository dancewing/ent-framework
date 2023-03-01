/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import io.entframework.kernel.system.api.pojo.request.HrPositionRequest;
import io.entframework.kernel.system.api.pojo.response.HrPositionResponse;
import io.entframework.kernel.system.modular.entity.HrPosition;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.entity.SysUserDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.HrPositionService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统职位表 服务实现类
 *
 * @date 2020/11/04 11:07
 */
public class HrPositionServiceImpl extends BaseServiceImpl<HrPositionRequest, HrPositionResponse, HrPosition>
        implements HrPositionService {

    public HrPositionServiceImpl() {
        super(HrPositionRequest.class, HrPositionResponse.class, HrPosition.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.converterService.convert(hrPositionRequest, getEntityClass());
        // 设置状态为启用
        sysPosition.setStatusFlag(StatusEnum.ENABLE);

        this.getRepository().insert(sysPosition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);

        // 该职位下是否有员工，如果有将不能删除
        long count = getRepository().count(SysUser.class,
                c -> c.where(SysUserDynamicSqlSupport.positionId, SqlBuilder.isEqualTo(sysPosition.getPositionId()))
                        .and(SysUserDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N)));
        if (count > 0) {
            throw new SystemModularException(PositionExceptionEnum.CANT_DELETE_POSITION);
        }

        // 逻辑删除
        sysPosition.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().update(sysPosition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HrPositionResponse update(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        sysPosition = this.converterService.convert(hrPositionRequest, getEntityClass());
        this.getRepository().update(sysPosition);
        return this.converterService.convert(sysPosition, getResponseClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        sysPosition.setStatusFlag(hrPositionRequest.getStatusFlag());
        this.getRepository().update(sysPosition);
    }

    @Override
    public HrPositionResponse detail(HrPositionRequest hrPositionRequest) {
        return this.converterService.convert(this.querySysPositionById(hrPositionRequest), getResponseClass());
    }

    @Override
    public List<HrPositionResponse> findList(HrPositionRequest hrPositionRequest) {
        return this.select(hrPositionRequest);
    }

    @Override
    public PageResult<HrPositionResponse> findPage(HrPositionRequest hrPositionRequest) {
        return this.page(hrPositionRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(HrPositionRequest hrPositionRequest) {
        List<Long> positionIds = hrPositionRequest.getPositionIds();
        for (Long userId : positionIds) {
            HrPositionRequest tempRequest = new HrPositionRequest();
            tempRequest.setPositionId(userId);
            this.del(tempRequest);
        }
    }

    @Override
    public long positionNum() {
        HrPositionRequest request = new HrPositionRequest();
        return this.countBy(request);
    }

    /**
     * 根据主键id获取对象信息
     * @return 实体对象
     * @date 2021/2/2 10:16
     */
    private HrPosition querySysPositionById(HrPositionRequest hrPositionRequest) {
        HrPosition hrPosition = this.getRepository().get(getEntityClass(), hrPositionRequest.getPositionId());
        if (ObjectUtil.isEmpty(hrPosition) || YesOrNotEnum.Y == hrPosition.getDelFlag()) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION,
                    hrPositionRequest.getPositionId());
        }
        return hrPosition;
    }

}

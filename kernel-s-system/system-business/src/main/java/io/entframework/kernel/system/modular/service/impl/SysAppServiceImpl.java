/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.factory.PageResultFactory;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.system.api.MenuServiceApi;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.app.AppExceptionEnum;
import io.entframework.kernel.system.api.pojo.request.SysAppRequest;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.modular.entity.SysApp;
import io.entframework.kernel.system.modular.entity.SysAppDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysAppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Slf4j
public class SysAppServiceImpl extends BaseServiceImpl<SysAppRequest, SysAppResponse, SysApp> implements SysAppService {

    public SysAppServiceImpl() {
        super(SysAppRequest.class, SysAppResponse.class, SysApp.class);
    }

    @Resource
    private MenuServiceApi menuApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysAppRequest sysAppRequest) {

        SysApp sysApp = new SysApp();

        // ?????????????????????
        sysApp.setAppName(sysAppRequest.getAppName());
        sysApp.setAppCode(sysAppRequest.getAppCode());
        sysApp.setAppIcon(sysAppRequest.getAppIcon());

        // ???????????????
        if (sysAppRequest.getAppSort() == null) {
            sysApp.setAppSort(999);
        }

        // ???????????????
        sysApp.setActiveFlag(YesOrNotEnum.N);

        // ????????????
        sysApp.setStatusFlag(StatusEnum.ENABLE);

        this.getRepository().insert(sysApp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysAppRequest sysAppRequest) {
        SysApp sysApp = this.querySysApp(sysAppRequest);
        Long appId = sysApp.getAppId();

        // ???????????????????????????????????????
        boolean hasMenu = menuApi.hasMenu(appId);
        if (hasMenu) {
            throw new ServiceException(AppExceptionEnum.APP_CANNOT_DELETE);
        }

        // ????????????
        sysApp.setDelFlag(YesOrNotEnum.Y);

        this.getRepository().updateByPrimaryKey(sysApp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysAppResponse update(SysAppRequest sysAppRequest) {

        SysApp sysApp = this.querySysApp(sysAppRequest);
        this.converterService.copy(sysAppRequest, sysApp);

        // ??????????????????
        sysApp.setAppCode(null);

        // ???????????????????????????????????????????????????
        sysApp.setStatusFlag(null);

        // ?????????????????????????????????????????????
        sysApp.setActiveFlag(null);

        this.getRepository().updateByPrimaryKey(sysApp);

        return this.converterService.convert(sysApp, getResponseClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SysAppRequest sysAppParam) {
        SysApp currentApp = this.querySysApp(sysAppParam);

        // ??????????????????????????????
        if (YesOrNotEnum.Y == currentApp.getActiveFlag() && StatusEnum.DISABLE == sysAppParam.getStatusFlag()) {
            throw new SystemModularException(AppExceptionEnum.CANT_DISABLE);
        }

        currentApp.setStatusFlag(sysAppParam.getStatusFlag());
        this.getRepository().updateByPrimaryKey(currentApp);
    }

    @Override
    public SysAppResponse detail(SysAppRequest sysAppRequest) {
        return this.converterService.convert(this.querySysApp(sysAppRequest), getResponseClass());
    }

    @Override
    public List<SysAppResponse> findList(SysAppRequest sysAppRequest) {
        return this.select(sysAppRequest);
    }

    @Override
    public PageResult<SysAppResponse> findPage(SysAppRequest sysAppRequest) {
        long count = this.countBy(sysAppRequest);
        List<SysAppResponse> results = findList(sysAppRequest);
        return PageResultFactory.createPageResult(results, count, sysAppRequest.getPageSize(),
                sysAppRequest.getPageNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActiveFlag(SysAppRequest sysAppRequest) {
        SysApp currentApp = this.querySysApp(sysAppRequest);

        // ??????????????????????????????????????????
        boolean hasMenu = menuApi.hasMenu(currentApp.getAppId());
        if (!hasMenu) {
            throw new ServiceException(AppExceptionEnum.ACTIVE_ERROR);
        }

        // ?????????????????????????????????
        UpdateDSLCompleter updateDSLCompleter = c -> c.set(SysAppDynamicSqlSupport.activeFlag).equalTo(YesOrNotEnum.N)
                .where(SysAppDynamicSqlSupport.activeFlag, isEqualTo(YesOrNotEnum.Y));
        this.getRepository().update(getEntityClass(), updateDSLCompleter);

        // ???????????????????????????
        currentApp.setActiveFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(currentApp);
    }

    @Override
    public List<SysApp> getUserTopAppList() {

        // ?????????????????????appId??????
        List<Long> userAppCodeList = menuApi.getUserAppIdList();

        SelectDSLCompleter selectDSLCompleter = c -> c
                .where(SysAppDynamicSqlSupport.appId, isIn(userAppCodeList).filter(Objects::nonNull))
                .orderBy(SysAppDynamicSqlSupport.activeFlag.descending());

        return this.getRepository().select(getEntityClass(), selectDSLCompleter);
    }

    @Override
    public Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes) {
        HashSet<SimpleDict> simpleDicts = new HashSet<>();
        SelectDSLCompleter selectDSLCompleter = c -> c
                .where(SysAppDynamicSqlSupport.appCode, isIn(appCodes).filter(Objects::nonNull))
                .orderBy(SysAppDynamicSqlSupport.activeFlag.descending());
        List<SysApp> list = this.getRepository().select(getEntityClass(), selectDSLCompleter);
        for (SysApp sysApp : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysApp.getAppId());
            simpleDict.setCode(sysApp.getAppCode());
            simpleDict.setName(sysApp.getAppName());
            simpleDicts.add(simpleDict);
        }

        return simpleDicts;
    }

    @Override
    public String getAppNameByAppCode(String appCode) {

        String emptyName = "?????????";

        if (ObjectUtil.isEmpty(appCode)) {
            return emptyName;
        }

        SelectDSLCompleter selectDSLCompleter = c -> c.where(SysAppDynamicSqlSupport.appCode,
                isEqualTo(appCode).filter(Objects::nonNull));

        Optional<SysApp> optionalSysApp = this.getRepository().selectOne(getEntityClass(), selectDSLCompleter);

        if (optionalSysApp.isPresent()) {
            SysApp sysApp = optionalSysApp.get();
            if (ObjectUtil.isEmpty(sysApp.getAppName())) {
                return emptyName;
            }
            else {
                return sysApp.getAppName();
            }
        }
        return emptyName;
    }

    @Override
    public String getActiveAppCode() {
        SysAppResponse appResponse = getActiveApp();
        if (appResponse != null) {
            return appResponse.getAppCode();
        }
        return null;
    }

    @Override
    public SysAppResponse getActiveApp() {

        SelectDSLCompleter selectDSLCompleter = c -> c
                .where(SysAppDynamicSqlSupport.activeFlag, isEqualToWhenPresent(YesOrNotEnum.Y))
                .and(SysAppDynamicSqlSupport.delFlag, isEqualToWhenPresent(YesOrNotEnum.N));

        List<SysApp> list = this.getRepository().select(getEntityClass(), selectDSLCompleter);
        if (list.isEmpty()) {
            return null;
        }
        else {
            return this.converterService.convert(list.get(0), getResponseClass());
        }
    }

    @Override
    public SysAppResponse getApp(Long appId) {
        if (appId == null) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_EXIST);
        }
        return this.get(appId);
    }

    @Override
    public SysAppResponse getAppInfoByAppCode(String appCode) {

        SelectDSLCompleter selectDSLCompleter = c -> c.where(SysAppDynamicSqlSupport.appCode,
                isEqualTo(appCode).filter(Objects::nonNull));

        Optional<SysApp> optionalSysApp = this.getRepository().selectOne(getEntityClass(), selectDSLCompleter);

        if (optionalSysApp.isPresent()) {
            SysApp sysApp = optionalSysApp.get();
            return this.converterService.convert(sysApp, getResponseClass());
        }
        else {
            return new SysAppResponse();
        }
    }

    /**
     * ??????????????????
     *
     * @date 2020/3/26 9:56
     */
    private SysApp querySysApp(SysAppRequest sysAppRequest) {
        Optional<SysApp> sysApp = this.getRepository().selectByPrimaryKey(getEntityClass(), sysAppRequest.getAppId());
        return sysApp.orElseThrow(() -> new ServiceException(AppExceptionEnum.APP_NOT_EXIST));
    }

}
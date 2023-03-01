/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.config.modular.service;

import io.entframework.kernel.config.api.pojo.ConfigInitItem;
import io.entframework.kernel.config.api.pojo.ConfigInitRequest;
import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.config.modular.pojo.request.SysConfigRequest;
import io.entframework.kernel.config.modular.pojo.response.SysConfigResponse;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;

import java.util.List;

/**
 * 系统参数配置service接口
 *
 * @date 2020/4/14 11:14
 */
public interface SysConfigService extends BaseService<SysConfigRequest, SysConfigResponse, SysConfig> {

    /**
     * 添加系统参数配置
     * @param sysConfigRequest 添加参数
     * @date 2020/4/14 11:14
     */
    void add(SysConfigRequest sysConfigRequest);

    /**
     * 删除系统参数配置
     * @param sysConfigRequest 删除参数
     * @date 2020/4/14 11:15
     */
    void del(SysConfigRequest sysConfigRequest);

    /**
     * 更新系统参数配置
     * @param sysConfigRequest 更新参数
     * @date 2020/4/14 11:15
     */
    SysConfigResponse update(SysConfigRequest sysConfigRequest);

    /**
     * 查看系统参数配置
     * @param sysConfigRequest 查看参数
     * @return 系统参数配置
     * @date 2020/4/14 11:15
     */
    SysConfig detail(SysConfigRequest sysConfigRequest);

    /**
     * 查询系统参数配置
     * @param sysConfigRequest 查询参数
     * @return 查询分页结果
     * @date 2020/4/14 11:14
     */
    PageResult<SysConfigResponse> findPage(SysConfigRequest sysConfigRequest);

    /**
     * 查询系统参数配置
     * @param sysConfigRequest 查询参数
     * @return 系统参数配置列表
     * @date 2020/4/14 11:14
     */
    List<SysConfigResponse> findList(SysConfigRequest sysConfigRequest);

    /**
     * 初始化配置参数
     *
     * @date 2021/7/8 16:48
     */
    void initConfig(ConfigInitRequest configInitRequest);

    /**
     * 获取配置是否初始化的标志
     * @return true-系统已经初始化，false-系统没有初始化
     * @date 2021/7/8 17:20
     */
    Boolean getInitConfigFlag();

    /**
     * 获取初始化的配置列表
     *
     * @date 2021/7/8 17:49
     */
    List<ConfigInitItem> getInitConfigs();

    /**
     * 获取后端部署的地址
     *
     * @date 2022/3/3 14:23
     */
    String getServerDeployHost();

}

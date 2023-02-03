/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api;

import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;

import java.util.Set;

/**
 * 应用相关api
 *
 * @date 2020/11/24 21:37
 */
public interface AppServiceApi {

    /**
     * 通过app编码获取app的详情
     *
     * @param appCodes 应用的编码
     * @return 应用的信息
     * @date 2020/11/29 20:06
     */
    Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes);

    /**
     * 通过app编码获取app的中文名
     *
     * @param appCode 应用的编码
     * @return 应用的中文名
     * @date 2020/11/29 20:06
     */
    String getAppNameByAppCode(String appCode);

    /**
     * 获取当前激活的应用编码
     *
     * @return 激活的应用编码
     * @date 2021/1/8 19:01
     */
    String getActiveAppCode();

    /**
     * 获取当前激活的应用
     *
     * @return 激活的应用编码
     * @date 2021/1/8 19:01
     */
    SysAppResponse getActiveApp();

    /**
     * 获取应用信息详情
     *
     * @param appId
     * @return
     */
    SysAppResponse getApp(Long appId);

    /**
     * 获取应用信息详情
     *
     * @date 2021/8/24 20:12
     */
    SysAppResponse getAppInfoByAppCode(String appCode);

}

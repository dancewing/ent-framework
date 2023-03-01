/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api;

import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;

import java.util.List;

/**
 * 组织机构api
 *
 * @date 2021/1/15 10:40
 */
public interface OrganizationServiceApi {

    /**
     * 查询系统组织机构
     * @return 组织机构列表
     * @date 2021/1/15 10:41
     */
    List<HrOrganizationResponse> orgList();

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.PositionServiceApi;
import io.entframework.kernel.system.api.pojo.request.HrPositionRequest;
import io.entframework.kernel.system.api.pojo.response.HrPositionResponse;
import io.entframework.kernel.system.modular.entity.HrPosition;

import java.util.List;

/**
 * 职位信息服务
 *
 * @date 2020/11/04 11:07
 */
public interface HrPositionService extends BaseService<HrPositionRequest, HrPositionResponse, HrPosition>, PositionServiceApi {

    /**
     * 添加职位
     *
     * @param hrPositionRequest 请求参数
     * @date 2020/11/04 11:07
     */
    void add(HrPositionRequest hrPositionRequest);

    /**
     * 删除职位
     *
     * @param hrPositionRequest 请求参数
     * @date 2020/11/04 11:07
     */
    void del(HrPositionRequest hrPositionRequest);

    /**
     * 编辑职位
     *
     * @param hrPositionRequest 请求参数
     * @date 2020/11/04 11:07
     */
    HrPositionResponse update(HrPositionRequest hrPositionRequest);

    /**
     * 更新状态
     *
     * @param hrPositionRequest 请求参数
     * @date 2020/11/18 23:00
     */
    void changeStatus(HrPositionRequest hrPositionRequest);

    /**
     * 查看详情
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情
     * @date 2020/11/04 11:07
     */
    HrPositionResponse detail(HrPositionRequest hrPositionRequest);

    /**
     * 查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情列表
     * @date 2020/11/04 11:07
     */
    List<HrPositionResponse> findList(HrPositionRequest hrPositionRequest);

    /**
     * 分页查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情分页列表
     * @date 2020/11/04 11:07
     */
    PageResult<HrPositionResponse> findPage(HrPositionRequest hrPositionRequest);

    /**
     * 批量删除系统职位
     *
     * @param hrPositionRequest 请求参数
     * @date 2021/4/8 13:51
     */
    void batchDel(HrPositionRequest hrPositionRequest);

}

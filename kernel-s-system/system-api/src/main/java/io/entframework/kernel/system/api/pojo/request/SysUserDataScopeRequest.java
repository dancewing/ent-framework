/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.request;


import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户数据范围
 *
 * @date 2021/2/3 15:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDataScopeRequest extends BaseRequest {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long userDataScopeId;

    /**
     * 用户id
     */
    @ChineseDescription("用户id")
    private Long userId;

    /**
     * 机构id
     */
    @ChineseDescription("机构id")
    private Long orgId;
}

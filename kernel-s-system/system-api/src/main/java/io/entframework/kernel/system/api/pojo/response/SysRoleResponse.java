/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @date 2020/11/5 下午3:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleResponse extends BaseRequest {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long roleId;

    /**
     * 名称
     */
    @ChineseDescription("名称")
    private String roleName;

    /**
     * 编码
     */
    @ChineseDescription("编码")
    private String roleCode;

    /**
     * 排序
     */
    @ChineseDescription("排序")
    private Integer roleSort;

    /**
     * 数据范围类型：10-全部数据，20-本部门及以下数据，30-本部门数据，40-仅本人数据，50-自定义数据
     */
    @ChineseDescription("数据范围类型：10-全部数据，20-本部门及以下数据，30-本部门数据，40-仅本人数据，50-自定义数据")
    private DataScopeTypeEnum dataScopeType;

    /**
     * 数据范围类型枚举
     */
    @ChineseDescription("数据范围类型枚举")
    private DataScopeTypeEnum dataScopeTypeEnum;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    private StatusEnum statusFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    @ChineseDescription("是否是系统角色：Y-是，N-否")
    private String roleSystemFlag;

    /**
     * 角色类型
     */
    @ChineseDescription("角色类型")
    private String roleTypeCode;

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.request;

import java.math.BigDecimal;
import java.util.List;

import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.validator.api.validators.unique.TableUniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统角色参数
 *
 * @date 2020/11/5 下午4:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "roleId不能为空",
            groups = { update.class, delete.class, detail.class, updateStatus.class, grantResource.class,
                    grantDataScope.class, grantMenu.class })
    @ChineseDescription("主键")
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = { add.class, update.class })
    @ChineseDescription("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空", groups = { add.class, update.class })
    @TableUniqueValue(message = "角色编码存在重复", groups = { add.class, update.class }, tableName = "sys_role",
            columnName = "role_code", idFieldName = "role_id", excludeLogicDeleteItems = true)
    @ChineseDescription("角色编码")
    private String roleCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { add.class, update.class })
    @ChineseDescription("排序")
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据
     */
    @Null(message = "数据范围类型应该为空", groups = { add.class, update.class })
    @NotNull(message = "数据范围类型不能为空", groups = { grantDataScope.class })
    @ChineseDescription("数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据")
    private DataScopeTypeEnum dataScopeType;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 状态（字典 1正常 2停用）
     */
    @ChineseDescription("状态（字典 1正常 2停用）")
    private StatusEnum statusFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    @ChineseDescription("是否是系统角色：Y-是，N-否")
    private YesOrNotEnum roleSystemFlag;

    /**
     * 角色类型
     */
    @ChineseDescription("角色类型")
    private String roleTypeCode;

    /**
     * 授权资源
     */
    @NotNull(message = "授权资源不能为空", groups = { grantResource.class })
    @ChineseDescription("授权资源")
    private List<String> grantResourceList;

    /**
     * 授权资源，模块组选中的资源
     */
    @ChineseDescription("授权资源，模块组选中的资源")
    private List<String> selectedResource;

    /**
     * 授权数据
     */
    @ChineseDescription("授权数据")
    private List<Long> grantOrgIdList;

    /**
     * 授权菜单
     */
    @NotNull(message = "授权菜单Id不能为空", groups = { grantMenu.class })
    @ChineseDescription("授权菜单")
    private CheckedKeys<Long> grantMenuIdList;

    /**
     * 参数校验分组：授权菜单
     */
    public @interface grantMenu {

    }

    /**
     * 参数校验分组：授权资源
     */
    public @interface grantResource {

    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantDataScope {

    }

}

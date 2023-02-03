/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 资源请求封装
 *
 * @since 2019-09-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysResourceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @ChineseDescription("资源id")
    private Long resourceId;

    /**
     * 应用编码
     */
    @ChineseDescription("应用编码")
    private String appCode;

    /**
     * 资源编码
     */
    @ChineseDescription("资源编码")
    @NotBlank(message = "资源编码为空", groups = detail.class)
    private String resourceCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String resourceName;

    /**
     * 项目编码
     */
    @ChineseDescription("项目编码")
    private String projectCode;

    /**
     * 类名称
     */
    @ChineseDescription("类名称")
    private String className;

    /**
     * 方法名称
     */
    @ChineseDescription("方法名称")
    private String methodName;

    /**
     * 资源模块编码
     */
    @ChineseDescription("资源模块编码")
    private String modularCode;

    /**
     * 资源模块名称
     */
    @ChineseDescription("资源模块名称")
    private String modularName;

    /**
     * 资源初始化的服务器ip地址
     */
    @ChineseDescription("资源初始化的服务器ip地址")
    private String ipAddress;

    /**
     * 是否是视图类型：Y-是，N-否
     * 如果是视图类型，url需要以 '/view' 开头，
     * 视图类型的接口会渲染出html界面，而不是json数据，
     * 视图层一般会在前后端不分离项目出现
     */
    @ChineseDescription("是否是视图类型")
    private YesOrNotEnum viewFlag;

    /**
     * 资源url
     */
    @ChineseDescription("资源url")
    private String url;

    /**
     * http请求方法
     */
    @ChineseDescription("http请求方法")
    private String httpMethod;

    /**
     * 是否需要登录：Y-是，N-否
     */
    @ChineseDescription("是否需要登录：Y-是，N-否")
    private YesOrNotEnum requiredLoginFlag;

    /**
     * 是否需要鉴权：Y-是，N-否
     */
    @ChineseDescription("是否需要鉴权：Y-是，N-否")
    private YesOrNotEnum requiredPermissionFlag;


    @ChineseDescription("自动上报：Y-是，N-否")
    private YesOrNotEnum autoReport;

    /**
     * 需要进行参数校验的分组
     * <p>
     * json形式存储
     */
    @ChineseDescription("需要进行参数校验的分组")
    private String validateGroups;

    /**
     * 接口参数的字段描述
     * <p>
     * json形式存储
     */
    @ChineseDescription("接口参数的字段描述")
    private String paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     * <p>
     * json形式存储
     */
    @ChineseDescription("接口返回结果的字段描述")
    private String responseFieldDescriptions;

    @ChineseDescription("批量删除ID集合")
    private List<Long> resourceIds;

    /**
     * 创建时间
     */
    @ChineseDescription("创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ChineseDescription("创建人")
    private Long createUser;

    /**
     * 创建人名称
     */
    @ChineseDescription("创建人账号")
    private String createUserName;

    /**
     * 更新时间
     */
    @ChineseDescription("更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @ChineseDescription("更新人")
    private Long updateUser;

    /**
     * 更新人名称
     */
    @ChineseDescription("更新人账号")
    private String updateUserName;
}

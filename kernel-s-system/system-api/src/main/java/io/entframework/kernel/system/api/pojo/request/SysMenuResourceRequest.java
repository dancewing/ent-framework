/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单资源的请求
 *
 * @date 2021/8/8 22:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuResourceRequest extends BaseRequest {

    /**
     * 业务id不能为空
     */
    @NotNull(message = "菜单id不能为空", groups = { list.class, add.class })
    @ChineseDescription("业务id")
    private Long menuId;

    /**
     * 选中的资源
     */
    @ChineseDescription("选中的资源")
    private List<String> selectedResource;

}

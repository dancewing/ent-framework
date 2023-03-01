/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.constants;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * 资源扫描模块的常量
 *
 * @author jeff_qian
 * @date 2020/11/3 13:50
 */
public interface ScannerConstants {

    String SCANNER_PREFIX = "kernel.scanner";

    String DEVOPS_REPORT_PREFIX = "kernel.devops";

    /**
     * 资源模块的名称
     */
    String RESOURCE_MODULE_NAME = "kernel-d-scanner";

    /**
     * 异常枚举的步进值
     */
    String RESOURCE_EXCEPTION_STEP_CODE = "17";

    /**
     * 资源前缀标识
     */
    String RESOURCE_CACHE_KEY = "RESOURCE_CACHES";

    /**
     * 资源汇报的监听器的顺序
     */
    Integer REPORT_RESOURCE_LISTENER_SORT = 200;

    /**
     * 视图类型的控制器url路径开头
     */
    String VIEW_CONTROLLER_PATH_START_WITH = "/view";

    /**
     * FieldMetadata类全路径
     */
    String FIELD_METADATA_CLASS_ALL_PATH = "resource.pojo.api.scanner.kernel.io.entframework.FieldMetadata";

    /**
     * DevOps平台资源汇报接口token超时时间
     */
    Long DEVOPS_REPORT_TIMEOUT_SECONDS = 5L;

    /**
     * DevOps平台资源汇报接口连接超时时间
     */
    Integer DEVOPS_REPORT_CONNECTION_TIMEOUT_SECONDS = 3;

    /**
     * DevOps平台资源汇报路径
     */
    String DEVOPS_REQUEST_PATH = "/scannerResource/addExternalResource";

    /**
     * 不需要解析的字段
     */
    List<String> DONT_PARSE_FIELD = ListUtil.list(false, "serialVersionUID", "delFlag", "createTime", "createUser",
            "updateTime", "updateUser");

    /**
     * 用在为validateGroups字段的标识，@Validate注解，不带class类时候的标识
     */
    String DEFAULT_VALIDATED = "default-all";

    /**
     *
     */
    String LINK_SYMBOL = "$";

    String ANNOTATION_NAME = "name";

    String ANNOTATION_REQUIRED_LOGIN = "requiredLogin";

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.pojo.devops;


import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 资源发送到DevOps一体化平台的参数
 *
 * @date 2022/1/11 14:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DevOpsReportResourceParam extends BaseRequest {

    /**
     * 项目唯一编码，在DevOps平台创建项目后会颁发
     */
    @ChineseDescription("项目唯一编码，在DevOps平台创建项目后会颁发")
    private String projectUniqueCode;

    /**
     * 向DevOps平台发送资源时候的令牌（通过jwt工具生成）
     */
    @ChineseDescription("向DevOps平台发送资源时候的令牌（通过jwt工具生成）")
    private String interactionToken;

    /**
     * 第一个key是模块名称，是下划线分割的控制器名称，不带Controller结尾
     * <p>
     * 第二个key是资源的编码
     */
    @ChineseDescription("第一个key是模块名称，是下划线分割的控制器名称，不带Controller结尾。第二个key是资源的编码")
    private List<SysResourcePersistencePojo> sysResourcePersistencePojoList;

    /**
     * FieldMetadata类的全路径
     */
    @ChineseDescription("FieldMetadata类的全路径")
    private String fieldMetadataClassPath;

    public DevOpsReportResourceParam(String projectUniqueCode, String interactionToken, List<SysResourcePersistencePojo> sysResourcePersistencePojoList, String fieldMetadataClassPath) {
        this.projectUniqueCode = projectUniqueCode;
        this.interactionToken = interactionToken;
        this.sysResourcePersistencePojoList = sysResourcePersistencePojoList;
        this.fieldMetadataClassPath = fieldMetadataClassPath;
    }
}

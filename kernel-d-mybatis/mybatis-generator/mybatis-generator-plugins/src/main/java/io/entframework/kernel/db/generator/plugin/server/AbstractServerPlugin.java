/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.AbstractDynamicSQLPlugin;
import io.entframework.kernel.db.generator.utils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.ArrayList;
import java.util.List;

/***
 * 增删改查，读取context配置参数
 */
public abstract class AbstractServerPlugin extends AbstractDynamicSQLPlugin {
    protected List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();

    protected String pojoRequestTargetPackage;
    protected String pojoRequestSuffix = "";

    protected String pojoRequestRootClass = "";
    protected String pojoResponseTargetPackage;
    protected String pojoResponseSuffix = "";
    protected String pojoResponseRootClass = "";

    protected String mapstructTargetPackage;
    protected String mapstructSuffix = "";

    protected String serviceTargetPackage;
    protected String serviceSuffix = "";
    protected String baseServicePrefix = "";
    protected String servicePrefix = "";
    protected String repositoryTargetPackage;
    protected String repositorySuffix = "";

    protected String controllerTargetPackage;
    protected String controllerSuffix = "Controller";
    protected String controllerPrefix = "";

    protected String codingStyle;

    @Override
    public boolean validate(List<String> warnings) {
        if (!"MyBatis3DynamicSql".equalsIgnoreCase(context.getTargetRuntime())) { //$NON-NLS-1$
            warnings.add("EntCrudPlugin 目前支持 runtime=MyBatis3DynamicSql"); //$NON-NLS-1$
            return false;
        }

        codingStyle = this.context.getProperty("generatedCodeStyle");
        if (StringUtils.isEmpty(codingStyle)) {
            codingStyle = Constants.GENERATED_CODE_STYLE;
        }

        this.pojoRequestTargetPackage = this.context.getProperty("pojoRequestTargetPackage");
        this.pojoRequestSuffix = PropertyUtils.getProperty(context, "pojoRequestSuffix", Constants.DEFAULT_POJO_REQUEST_SUFFIX);

        this.pojoRequestRootClass = this.context.getProperty("pojoRequestRootClass");

        this.pojoResponseTargetPackage = this.context.getProperty("pojoResponseTargetPackage");
        this.pojoResponseSuffix = PropertyUtils.getProperty(context, "pojoResponseSuffix", Constants.DEFAULT_POJO_RESPONSE_SUFFIX);

        this.pojoResponseRootClass = this.context.getProperty("pojoResponseRootClass");

        this.mapstructTargetPackage = this.context.getProperty("mapstructTargetPackage");
        this.mapstructSuffix = PropertyUtils.getProperty(context, "mapstructSuffix", Constants.DEFAULT_MAPSTRUCT_SUFFIX);

        this.serviceTargetPackage = this.context.getProperty("serviceTargetPackage");
        this.serviceSuffix = PropertyUtils.getProperty(context, "serviceSuffix", Constants.DEFAULT_SERVICE_SUFFIX);
        this.baseServicePrefix = PropertyUtils.getProperty(context, "baseServicePrefix", Constants.DEFAULT_BASE_SERVICE_PREFIX);
        this.servicePrefix = PropertyUtils.getProperty(context, "servicePrefix", "");

        this.repositoryTargetPackage = this.context.getProperty("repositoryTargetPackage");
        this.repositorySuffix = PropertyUtils.getProperty(context, "repositorySuffix", Constants.DEFAULT_REPOSITORY_SUFFIX);

        this.controllerTargetPackage = this.context.getProperty("controllerTargetPackage");
        this.controllerPrefix = PropertyUtils.getProperty(context, "controllerPrefix", Constants.DEFAULT_BASE_CONTROLLER_PREFIX);


        String mode = this.properties.getProperty("writeMode");
        if (StringUtils.isNotEmpty(mode)) {
            WriteMode writeMode = convert(mode);
            if (writeMode != null) {
                this.writeMode = writeMode;
            } else {
                warnings.add(this.getClass().getName() + "配置了错误的WriteMode, 可用值: NEVER,OVER_WRITE,SKIP_ON_EXIST");
            }
        }

        return true;
    }

    public FullyQualifiedJavaType getMapperJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.context.getJavaClientGeneratorConfiguration().getTargetPackage() + "." + modelObjectName + "Mapper");
    }

    public FullyQualifiedJavaType getPojoRequestJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.pojoRequestTargetPackage + "." + modelObjectName + this.pojoRequestSuffix);
    }

    public FullyQualifiedJavaType getPojoResponseJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.pojoResponseTargetPackage + "." + modelObjectName + this.pojoResponseSuffix);
    }

    public FullyQualifiedJavaType getMapstructJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.mapstructTargetPackage + "." + modelObjectName + this.mapstructSuffix);
    }


    public FullyQualifiedJavaType getBaseServiceJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.serviceTargetPackage + ".base." + this.baseServicePrefix + modelObjectName + this.serviceSuffix);
    }


    public FullyQualifiedJavaType getBaseServiceImplJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.serviceTargetPackage + ".base.impl." + this.baseServicePrefix + modelObjectName + this.serviceSuffix + "Impl");
    }

    public FullyQualifiedJavaType getServiceJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.serviceTargetPackage + "." + this.servicePrefix + modelObjectName + this.serviceSuffix);
    }


    public FullyQualifiedJavaType getServiceImplJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.serviceTargetPackage + ".impl." + this.servicePrefix + modelObjectName + this.serviceSuffix + "Impl");
    }

    public FullyQualifiedJavaType getRepositoryJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.repositoryTargetPackage + "." + modelObjectName + this.repositorySuffix);
    }

    public FullyQualifiedJavaType getRepositoryImplJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.repositoryTargetPackage + ".impl." + modelObjectName + this.repositorySuffix + "Impl");
    }


    public FullyQualifiedJavaType getBaseControllerJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.controllerTargetPackage + ".base." + this.controllerPrefix + modelObjectName + this.controllerSuffix);
    }

    public FullyQualifiedJavaType getControllerJavaType(String modelObjectName) {
        return new FullyQualifiedJavaType(this.controllerTargetPackage + "." + modelObjectName + this.controllerSuffix);
    }
}

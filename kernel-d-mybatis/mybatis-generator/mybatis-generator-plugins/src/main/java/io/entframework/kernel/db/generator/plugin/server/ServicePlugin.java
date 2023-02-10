/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.List;

/***
 * Service生成
 */
public class ServicePlugin extends AbstractServerPlugin {

    @Override
    public boolean validate(List<String> warnings) {

        boolean validate = super.validate(warnings);

        if (StringUtils.isAnyEmpty(this.serviceTargetPackage, this.serviceSuffix)) {
            warnings.add("请检查ServicePlugin配置");
            return false;
        }

        return validate;
    }

    /**
     * Mapper 文件生成时，同步生成service接口及实现
     *
     * @param interfaze         the generated interface if any, may be null
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true/false
     */
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

        boolean hasGeneratedKeys = introspectedTable.getGeneratedKey().isPresent();
        generatedJavaFiles.add(generateBaseServiceInterface(interfaze, introspectedTable, hasGeneratedKeys));
        generatedJavaFiles.add(generateBaseServiceInterfaceImpl(interfaze, introspectedTable, hasGeneratedKeys));

        return true;
    }

    private GeneratedJavaFile generateBaseServiceInterface(Interface clientInterface, IntrospectedTable introspectedTable, boolean hasGeneratedKeys) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);
        FullyQualifiedJavaType baseServiceJavaType = getServiceJavaType(recordType.getShortName());

        Interface serviceInterface = new Interface(baseServiceJavaType);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        serviceInterface.setWriteMode(this.writeMode == null ? WriteMode.SKIP_ON_EXIST : this.writeMode);

        FullyQualifiedJavaType baseServiceType = new FullyQualifiedJavaType("io.entframework.kernel.db.dao.service.BaseService");
        baseServiceType.addTypeArgument(getPojoRequestJavaType(recordType.getShortName()));
        baseServiceType.addTypeArgument(getPojoResponseJavaType(recordType.getShortName()));
        baseServiceType.addTypeArgument(recordType);
        serviceInterface.addSuperInterface(baseServiceType);

        serviceInterface.addImportedType(baseServiceType);
        serviceInterface.addImportedType(getPojoRequestJavaType(recordType.getShortName()));
        serviceInterface.addImportedType(getPojoResponseJavaType(recordType.getShortName()));

        return new GeneratedJavaFile(serviceInterface,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }


    private GeneratedJavaFile generateBaseServiceInterfaceImpl(Interface clientInterface, IntrospectedTable introspectedTable, boolean hasGeneratedKeys) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);

        FullyQualifiedJavaType serviceImplJavaType = getServiceImplJavaType(recordType.getShortName());
        TopLevelClass serviceInterfaceImplClass = new TopLevelClass(serviceImplJavaType);
        serviceInterfaceImplClass.setVisibility(JavaVisibility.PUBLIC);
        serviceInterfaceImplClass.setAbstract(false);
        FullyQualifiedJavaType interfaceType = getServiceJavaType(recordType.getShortName());
        serviceInterfaceImplClass.setWriteMode(this.writeMode == null ? WriteMode.SKIP_ON_EXIST : this.writeMode);

        serviceInterfaceImplClass.addAnnotation(LombokAnnotation.SLF4J.getName());
        serviceInterfaceImplClass.addImportedType(LombokAnnotation.SLF4J.getJavaType());

        serviceInterfaceImplClass.addSuperInterface(interfaceType);
        serviceInterfaceImplClass.addImportedType(interfaceType);

        FullyQualifiedJavaType baseServiceType = new FullyQualifiedJavaType("io.entframework.kernel.db.dao.service.BaseServiceImpl");
        baseServiceType.addTypeArgument(getPojoRequestJavaType(recordType.getShortName()));
        baseServiceType.addTypeArgument(getPojoResponseJavaType(recordType.getShortName()));
        baseServiceType.addTypeArgument(recordType);
        serviceInterfaceImplClass.setSuperClass(baseServiceType);

        serviceInterfaceImplClass.addImportedType(baseServiceType);
        serviceInterfaceImplClass.addImportedType(getPojoRequestJavaType(recordType.getShortName()));
        serviceInterfaceImplClass.addImportedType(getPojoResponseJavaType(recordType.getShortName()));

        //构造器
        FullyQualifiedJavaType pojoRequestJavaType = getPojoRequestJavaType(recordType.getShortName());
        FullyQualifiedJavaType pojoResponseJavaType = getPojoResponseJavaType(recordType.getShortName());
        Method defaultConstructor = new Method(serviceImplJavaType.getShortName());
        defaultConstructor.setConstructor(true);
        defaultConstructor.setVisibility(JavaVisibility.PUBLIC);
        defaultConstructor.addBodyLine(String.format("super(%s.class, %s.class, %s.class);",
                pojoRequestJavaType.getShortName(), pojoResponseJavaType.getShortName(), recordType.getShortName()));

        Method method = new Method(serviceImplJavaType.getShortName());
        FullyQualifiedJavaType entityClsJavaType = new FullyQualifiedJavaType(String.format("Class<? extends %s>", recordType.getShortName()));
        FullyQualifiedJavaType requestClsJavaType = new FullyQualifiedJavaType(String.format("Class<? extends %s>", pojoRequestJavaType.getShortName()));
        FullyQualifiedJavaType responseClsJavaType = new FullyQualifiedJavaType(String.format("Class<? extends %s>", pojoResponseJavaType.getShortName()));
        Parameter p2 = new Parameter(requestClsJavaType, "requestClass");
        method.addParameter(p2);
        p2 = new Parameter(responseClsJavaType, "responseClass");
        method.addParameter(p2);
        p2 = new Parameter(entityClsJavaType, "entityClass");
        method.addParameter(p2);
        method.setConstructor(true);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addBodyLine("super(requestClass, responseClass, entityClass);");


        serviceInterfaceImplClass.addMethod(defaultConstructor);
        serviceInterfaceImplClass.addMethod(method);

        return new GeneratedJavaFile(serviceInterfaceImplClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }

    /**
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return generatedJavaFiles;
    }
}

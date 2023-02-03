/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.plugin.server.methods.repository.RepConstructorGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.repository.RepDeleteByPrimaryKeyMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.repository.RepEnhancedCreateAndUpdateMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.repository.RepSelectByPrimaryKeyMethodGenerator;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository生成
 */
public class RepositoryPlugin extends AbstractServerPlugin {

    @Override
    public boolean validate(List<String> warnings) {

        boolean validate = super.validate(warnings);

        if (StringUtils.isAnyEmpty(this.repositoryTargetPackage, this.repositorySuffix)) {
            warnings.add("请检查RepositoryPlugin配置");
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
        generatedJavaFiles.add(generateRepositoryInterface(interfaze, introspectedTable, hasGeneratedKeys));
        generatedJavaFiles.add(generateRepositoryImpl(interfaze, introspectedTable, hasGeneratedKeys));

        return true;
    }

    private GeneratedJavaFile generateRepositoryInterface(Interface clientInterface, IntrospectedTable introspectedTable, boolean hasGeneratedKeys) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);
        FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType(recordType.getShortName());
        Interface repositoryInterface = new Interface(repositoryJavaType);
        repositoryInterface.setVisibility(JavaVisibility.PUBLIC);

        repositoryInterface.setWriteMode(WriteMode.OVER_WRITE);


        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("io.entframework.kernel.db.mds.repository.BaseRepository");
        fqjt.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        repositoryInterface.addSuperInterface(fqjt);
        repositoryInterface.addImportedType(fqjt);

        return new GeneratedJavaFile(repositoryInterface,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }


    private GeneratedJavaFile generateRepositoryImpl(Interface interfaze, IntrospectedTable introspectedTable, boolean hasGeneratedKeys) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);

        FullyQualifiedJavaType repositoryImplJavaType = getRepositoryImplJavaType(recordType.getShortName());
        TopLevelClass repositoryInterfaceImplClass = new TopLevelClass(repositoryImplJavaType);

        repositoryInterfaceImplClass.setWriteMode(WriteMode.OVER_WRITE);

        repositoryInterfaceImplClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType interfaceType = getRepositoryJavaType(recordType.getShortName());
        repositoryInterfaceImplClass.addSuperInterface(interfaceType);
        repositoryInterfaceImplClass.addImportedType(interfaceType);

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("io.entframework.kernel.db.mds.repository.BaseRepositoryImpl");
        fqjt.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        repositoryInterfaceImplClass.setSuperClass(fqjt);
        repositoryInterfaceImplClass.addImportedType(fqjt);

        AbstractMethodGenerator.BuildConfig buildConfig = getConfig(interfaze, repositoryInterfaceImplClass, introspectedTable, recordType, false);

        generate(repositoryInterfaceImplClass, new RepConstructorGenerator(buildConfig));
        generate(repositoryInterfaceImplClass, new RepEnhancedCreateAndUpdateMethodGenerator(buildConfig));
        generate(repositoryInterfaceImplClass, new RepDeleteByPrimaryKeyMethodGenerator(buildConfig));
        generate(repositoryInterfaceImplClass, new RepSelectByPrimaryKeyMethodGenerator(buildConfig));

        return new GeneratedJavaFile(repositoryInterfaceImplClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }


    private GeneratedJavaFile generateBaseRepository(Interface interfaze, IntrospectedTable introspectedTable, boolean hasGeneratedKeys) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);

        FullyQualifiedJavaType repositoryImplJavaType = getRepositoryJavaType(recordType.getShortName());
        TopLevelClass repositoryInterfaceImplClass = new TopLevelClass(repositoryImplJavaType);

        repositoryInterfaceImplClass.setWriteMode(WriteMode.OVER_WRITE);

        repositoryInterfaceImplClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType interfaceType = getRepositoryJavaType(recordType.getShortName());
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("io.entframework.kernel.db.mds.repository.BaseRepositoryImpl");
        fqjt.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        repositoryInterfaceImplClass.setSuperClass(fqjt);
        repositoryInterfaceImplClass.addImportedType(fqjt);

        repositoryInterfaceImplClass.addAnnotation("@Repository");
        repositoryInterfaceImplClass.addImportedType("org.springframework.stereotype.Repository");

        return new GeneratedJavaFile(repositoryInterfaceImplClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }

    private List<Method> removeStatementDefinedMethod(List<Method> methods) {

        return methods.stream().filter(method -> {
            if (method.isStatic()) {
                return false;
            }
            if (method.getParameters().size() > 0) {
                Parameter parameter = method.getParameters().get(0);
                String paramType = parameter.getType().getFullyQualifiedNameWithoutTypeParameters();
                if (StringUtils.equals(paramType, "org.mybatis.dynamic.sql.select.render.SelectStatementProvider") &&
                        StringUtils.equalsAny(method.getName(), "leftJoinSelectOne", "leftJoinSelectMany")) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    private AbstractMethodGenerator.BuildConfig getConfig(Interface clientInterface, AbstractJavaType interfaze, IntrospectedTable introspectedTable, FullyQualifiedJavaType recordType, boolean isAbstract) {
        return new AbstractMethodGenerator.BuildConfig()
                .withContext(context)
                .withIntrospectedTable(introspectedTable)
                .withHostJavaClass(interfaze)
                .withClientInterface(clientInterface)
                .withTableFieldName("")
                .withAbstract(isAbstract)
                .withRecordType(recordType);
    }

    protected boolean generate(AbstractJavaType javaType, AbstractMethodGenerator generator) {
        MethodAndImports mi = generator.generateMethodAndImports();
        if (mi != null) {
            if (javaType instanceof Interface) {
                Interface interfaze = (Interface) javaType;
                mi.getMethods().forEach(interfaze::addMethod);
                interfaze.addImportedTypes(mi.getImports());
                interfaze.addStaticImports(mi.getStaticImports());
            }
            if (javaType instanceof TopLevelClass) {
                TopLevelClass topLevelClass = (TopLevelClass) javaType;
                mi.getMethods().forEach(topLevelClass::addMethod);
                topLevelClass.addImportedTypes(mi.getImports());
                topLevelClass.addStaticImports(mi.getStaticImports());
            }

            return true;
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return generatedJavaFiles;
    }
}
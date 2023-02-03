/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

public class SpringAutoConfigurationPlugin extends AbstractServerPlugin {

    private String springConfigJavaFile;

    @Override
    public boolean validate(List<String> warnings) {
        boolean validate = super.validate(warnings);

        this.springConfigJavaFile = this.properties.getProperty("springConfigJavaFile");

        if (StringUtils.isEmpty(springConfigJavaFile)) {
            warnings.add("请检查SpringAutoConfigurationPlugin配置");
            return false;
        }

        return validate;
    }

    private GeneratedJavaFile generateServiceConfig() {

        TopLevelClass configBaseClass = new TopLevelClass(this.springConfigJavaFile);
        configBaseClass.setVisibility(JavaVisibility.PUBLIC);
        configBaseClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);

        List<IntrospectedTable> tables = this.context.getIntrospectedTables();
        for (IntrospectedTable introspectedTable : tables) {
            String baseRecordType = introspectedTable.getBaseRecordType();
            FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);
            FullyQualifiedJavaType baseServiceJavaType = getServiceJavaType(recordType.getShortName());
            FullyQualifiedJavaType baseServiceImplJavaType = getServiceImplJavaType(recordType.getShortName());

            FullyQualifiedJavaType baseRepositoryJavaType = getRepositoryJavaType(recordType.getShortName());
            FullyQualifiedJavaType baseRepositoryImplJavaType = getRepositoryImplJavaType(recordType.getShortName());
            Method repositoryMethod = new Method(StringUtils.uncapitalize(baseRepositoryJavaType.getShortName()));

            repositoryMethod.addAnnotation("@Bean");
            repositoryMethod.setVisibility(JavaVisibility.PUBLIC);
            repositoryMethod.setReturnType(baseRepositoryJavaType);
            repositoryMethod.addAnnotation(String.format("@ConditionalOnMissingBean(%s.class)", baseRepositoryJavaType.getShortName()));

            repositoryMethod.addBodyLine(String.format("return new %s();", baseRepositoryImplJavaType.getShortName()));

            configBaseClass.addMethod(repositoryMethod);
            configBaseClass.addImportedType(baseRepositoryJavaType);
            configBaseClass.addImportedType(baseRepositoryImplJavaType);

            Method serviceMethod = new Method(StringUtils.uncapitalize(baseServiceJavaType.getShortName()));

            Parameter p1 = new Parameter(baseRepositoryJavaType, StringUtils.uncapitalize(baseRepositoryJavaType.getShortName()));
            serviceMethod.addParameter(p1);

            serviceMethod.addBodyLine(String.format("return new %s(%s);", baseServiceImplJavaType.getShortName(), StringUtils.uncapitalize(baseRepositoryJavaType.getShortName())));
            serviceMethod.addAnnotation("@Bean");
            serviceMethod.setVisibility(JavaVisibility.PUBLIC);
            serviceMethod.setReturnType(baseServiceJavaType);
            serviceMethod.addAnnotation(String.format("@ConditionalOnMissingBean(%s.class)", baseServiceJavaType.getShortName()));
            configBaseClass.addMethod(serviceMethod);
            configBaseClass.addImportedType(baseServiceJavaType);
            configBaseClass.addImportedType(baseServiceImplJavaType);
        }
        configBaseClass.addImportedType("org.springframework.context.annotation.Bean");
        configBaseClass.addImportedType("org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean");

        FullyQualifiedJavaType baseServiceType = new FullyQualifiedJavaType("org.springframework.context.annotation.Configuration");
        configBaseClass.addAnnotation("@Configuration");
        configBaseClass.addImportedType(baseServiceType);

        List<String> scanPackages = new ArrayList<>();
        scanPackages.add("\"" + this.controllerTargetPackage + "\"");
        scanPackages.add("\"" + this.mapstructTargetPackage + "\"");
        scanPackages.add("\"" + this.serviceTargetPackage + "\"");
        configBaseClass.addAnnotation(String.format("@ComponentScan(basePackages = {%s})", StringUtils.join(scanPackages, ", ")));
        configBaseClass.addAnnotation(String.format("@MapperScan(basePackages = \"%s\")", context.getJavaClientGeneratorConfiguration().getTargetPackage()));

        configBaseClass.addImportedType("org.springframework.context.annotation.ComponentScan");
        configBaseClass.addImportedType("org.mybatis.spring.annotation.MapperScan");

        return new GeneratedJavaFile(configBaseClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {

        generatedJavaFiles.add(generateServiceConfig());

        return generatedJavaFiles;
    }

    @Override
    public List<GeneratedFile> contextGenerateAdditionalFiles() {
        List<GeneratedFile> generatedFiles = new ArrayList<>();
        generatedFiles.add(generateFactoryFile());
        return generatedFiles;
    }

    private GeneratedFile generateFactoryFile() {
        GeneratedPlainFile factoryFile = new GeneratedPlainFile("src/main/resources", "org.springframework.boot.autoconfigure.AutoConfiguration.imports", "META-INF/spring");
        factoryFile.setMergeable(true);
        factoryFile.addBodyLine(this.springConfigJavaFile);
        return factoryFile;
    }
}

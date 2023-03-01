/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.RestMethod;
import io.entframework.kernel.db.generator.plugin.generator.RestMethodAndImports;
import io.entframework.kernel.db.generator.plugin.generator.RestMethodsGenerator;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/***
 * Controller 生成
 */
public class ControllerPlugin extends AbstractServerPlugin {

    private String responseBodyWrapper = "io.entframework.kernel.rule.pojo.response.ResponseData";

    private String responseBodySuccessStaticMethod = "ok";

    private boolean enableControllerParentMode = false;

    @Override
    public boolean validate(List<String> warnings) {
        boolean validate = super.validate(warnings);

        if (StringUtils.isAnyEmpty(this.controllerTargetPackage)) {
            warnings.add("请检查ControllerPlugin配置");
            return false;
        }

        String enableControllerParentMode = this.properties.getProperty("enableControllerParentMode");
        if (StringUtility.stringHasValue(enableControllerParentMode)
                && StringUtility.isTrue(enableControllerParentMode)) {
            this.enableControllerParentMode = true;
        }

        return validate;
    }

    /**
     * Mapper 文件生成时，同步Controller
     * @param interfaze the generated interface if any, may be null
     * @param introspectedTable The class containing information about the table as
     * introspected from the database
     * @return
     */
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String baseRecordType = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);
        FullyQualifiedJavaType baseControllerJavaType = getControllerJavaType(recordType.getShortName());
        // 启用抽象父类
        if (this.enableControllerParentMode) {
            baseControllerJavaType = getBaseControllerJavaType(recordType.getShortName());
        }
        TopLevelClass baseControllerJavaClass = new TopLevelClass(baseControllerJavaType);
        baseControllerJavaClass.setVisibility(JavaVisibility.PUBLIC);
        baseControllerJavaClass.setAbstract(this.enableControllerParentMode);
        // 启用抽象父类，父类强制覆盖
        if (this.enableControllerParentMode) {
            baseControllerJavaClass.setWriteMode(WriteMode.OVER_WRITE);
        }
        FullyQualifiedJavaType serviceJavaType = getServiceJavaType(recordType.getShortName());
        baseControllerJavaClass.addImportedType(serviceJavaType);
        String serviceFieldName = super.lowerCaseFirstChar(serviceJavaType.getShortName());
        Field serviceField = new Field(serviceFieldName, serviceJavaType);

        serviceField.setVisibility(JavaVisibility.PROTECTED);
        serviceField.addAnnotation("@Resource");
        baseControllerJavaClass.addImportedType("jakarta.annotation.Resource");
        baseControllerJavaClass.addField(serviceField);

        String modelDescription = interfaze.getDescription();
        TopLevelClass modelClass = (TopLevelClass) introspectedTable
                .getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);

        FullyQualifiedJavaType requestJavaType = getPojoRequestJavaType(recordType.getShortName());
        FullyQualifiedJavaType responseJavaType = getPojoResponseJavaType(recordType.getShortName());
        RestMethodsGenerator restMethodsGenerator = new RestMethodsGenerator(recordType, requestJavaType,
                responseJavaType, true);
        if (StringUtils.isNotEmpty(this.pojoRequestRootClass)) {
            restMethodsGenerator.setBaseRequestType(new FullyQualifiedJavaType(this.pojoRequestRootClass));
        }
        restMethodsGenerator.generate();
        RestMethodAndImports methodAndImports = restMethodsGenerator.build();

        methodAndImports.getMethods().forEach(method -> {
            if (StringUtils.equals("POST", method.getHttpMethod())) {
                addPostMapping(baseControllerJavaClass, method, modelDescription);
            }
            if (StringUtils.equals("GET", method.getHttpMethod())) {
                addGetMapping(baseControllerJavaClass, method, modelDescription);
            }
            if (StringUtils.isAnyEmpty(this.responseBodyWrapper, this.responseBodySuccessStaticMethod)) {
                method.addBodyLine(String.format("return %s.%s(request);", serviceFieldName, method.getName()));
            }
            else {
                FullyQualifiedJavaType responseWrapJavaType = new FullyQualifiedJavaType(this.responseBodyWrapper);
                FullyQualifiedJavaType responseBodyWrapperType = new FullyQualifiedJavaType(this.responseBodyWrapper);
                baseControllerJavaClass.addImportedType(responseWrapJavaType);
                if (method.getReturnType().isPresent()) {
                    responseBodyWrapperType.addTypeArgument(method.getReturnType().get());
                    method.setReturnType(responseBodyWrapperType);
                }
                method.addBodyLine(String.format("return %s.%s(%s.%s(request));", responseWrapJavaType.getShortName(),
                        this.responseBodySuccessStaticMethod, serviceFieldName, method.getName()));
            }
        });

        methodAndImports.getMethods().forEach(baseControllerJavaClass::addMethod);
        baseControllerJavaClass.addImportedTypes(methodAndImports.getImports());

        GeneratedJavaFile baseControllerJavaFile = new GeneratedJavaFile(baseControllerJavaClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());

        if (this.enableControllerParentMode) {
            FullyQualifiedJavaType controllerJavaType = getControllerJavaType(recordType.getShortName());
            TopLevelClass controllerJavaClass = new TopLevelClass(controllerJavaType);
            controllerJavaClass.setVisibility(JavaVisibility.PUBLIC);
            controllerJavaClass.addAnnotation("@RestController");
            controllerJavaClass.addImportedType(
                    new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
            controllerJavaClass.setSuperClass(baseControllerJavaType);
            controllerJavaClass.addImportedType(baseControllerJavaType);

            if (this.codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
                controllerJavaClass
                        .addAnnotation(String.format("@ApiResource(name = \"%s\")", modelClass.getDescription()));
                controllerJavaClass.addImportedType("io.entframework.kernel.scanner.api.annotation.ApiResource");
            }

            // 子类默认只新增，不覆盖
            controllerJavaClass.setWriteMode(this.writeMode == null ? WriteMode.SKIP_ON_EXIST : this.writeMode);

            GeneratedJavaFile controllerJavaFile = new GeneratedJavaFile(controllerJavaClass,
                    context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
            this.generatedJavaFiles.add(controllerJavaFile);
        }
        else {
            if (this.codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
                baseControllerJavaClass
                        .addAnnotation(String.format("@ApiResource(name = \"%s\")", modelClass.getDescription()));
                baseControllerJavaClass.addImportedType("io.entframework.kernel.scanner.api.annotation.ApiResource");
            }
        }

        this.generatedJavaFiles.add(baseControllerJavaFile);

        return true;
    }

    private void addPostMapping(TopLevelClass controllerJavaClass, RestMethod method, String modelDescription) {
        if (this.codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
            method.addAnnotation(String.format("@PostResource(name = \"%s-%s\", path = \"%s\")", modelDescription,
                    method.getOperation(), method.getRestPath()));
            controllerJavaClass.addImportedType("io.entframework.kernel.scanner.api.annotation.PostResource");
        }
        else {
            method.addAnnotation(String.format("@PostMapping(\"%s\")", method.getRestPath()));
            controllerJavaClass.addImportedType("org.springframework.web.bind.annotation.PostMapping");
        }
    }

    private void addGetMapping(TopLevelClass controllerJavaClass, RestMethod method, String modelDescription) {
        if (this.codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
            method.addAnnotation(String.format("@GetResource(name = \"%s-%s\", path = \"%s\")", modelDescription,
                    method.getOperation(), method.getRestPath()));
            controllerJavaClass.addImportedType("io.entframework.kernel.scanner.api.annotation.GetResource");
        }
        else {
            method.addAnnotation(String.format("@GetMapping(\"/%s\")", method.getRestPath()));
            controllerJavaClass.addImportedType("org.springframework.web.bind.annotation.GetMapping");
        }
    }

    /**
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return generatedJavaFiles;
    }

}

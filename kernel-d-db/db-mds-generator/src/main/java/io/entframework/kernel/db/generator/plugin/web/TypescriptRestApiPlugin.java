/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.web;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.generator.RestMethodAndImports;
import io.entframework.kernel.db.generator.plugin.generator.RestMethodsGenerator;
import io.entframework.kernel.db.generator.plugin.web.runtime.DefaultTypescriptFormatter;
import io.entframework.kernel.db.generator.plugin.web.runtime.FullyQualifiedTypescriptType;
import io.entframework.kernel.db.generator.plugin.web.runtime.GeneratedTypescriptFile;
import io.entframework.kernel.db.generator.plugin.web.runtime.TypescriptTopLevelClass;
import io.entframework.kernel.db.generator.plugin.web.runtime.render.RenderingUtilities;
import io.entframework.kernel.db.generator.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

/***
 * 数据模型构建后都会执行
 * 生成rest api
 */
public class TypescriptRestApiPlugin extends AbstractWebPlugin {
    private final List<GeneratedFile> generatedFiles = new ArrayList<>();

    private String apiPrefix;

    @Override
    public boolean validate(List<String> warnings) {
        boolean validate = super.validate(warnings);

        this.apiPrefix = this.properties.getProperty("apiPrefix");

        return validate;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        //设置java文件不写磁盘
        topLevelClass.setWriteMode(WriteMode.NEVER);
        generatedFiles.add(generateRestApiClass(topLevelClass, introspectedTable));
        return true;
    }

    private GeneratedFile generateRestApiClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String modelObjectName = topLevelClass.getType().getShortNameWithoutTypeArguments();
        FullyQualifiedTypescriptType tsApiModelJavaType = new FullyQualifiedTypescriptType(this.projectRootAlias, this.apiTargetPackage + "." + WebUtils.getFileName(modelObjectName) + "." + modelObjectName);
        TypescriptTopLevelClass tsBaseModelClass = new TypescriptTopLevelClass(tsApiModelJavaType);
        tsBaseModelClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);

        tsBaseModelClass.setVisibility(JavaVisibility.PUBLIC);

        GeneratorUtils.addComment(tsBaseModelClass, topLevelClass.getDescription() + " 服务请求类");

        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(typescriptModelPackage + "." + modelObjectName);

        FullyQualifiedJavaType requestJavaType = new FullyQualifiedTypescriptType(this.projectRootAlias, typescriptModelPackage + "." + WebUtils.getFileName(modelObjectName) + "." + modelObjectName);
        FullyQualifiedJavaType responseJavaType = new FullyQualifiedTypescriptType(this.projectRootAlias,typescriptModelPackage + "." + WebUtils.getFileName(modelObjectName) + "." + modelObjectName);
        RestMethodsGenerator restMethodsGenerator = new RestMethodsGenerator(recordType, requestJavaType, responseJavaType, false);
        restMethodsGenerator.generate();
        RestMethodAndImports methodAndImports = restMethodsGenerator.build();

        methodAndImports.getMethods().forEach(method -> {
            String methodName = method.getName();
            String returnTypeName = "void";
            if (method.getReturnType().isPresent()) {
                FullyQualifiedJavaType returnType = method.getReturnType().get();
                if (returnType.getFullyQualifiedNameWithoutTypeParameters().equals("io.entframework.kernel.db.api.pojo.page.PageResult")) {
                    FullyQualifiedJavaType arg = returnType.getTypeArguments().get(0);
                    String newTypeName = arg.getShortName() + "PageModel";
                    FullyQualifiedJavaType newType = new FullyQualifiedTypescriptType(this.projectRootAlias, this.typescriptModelPackage + "." + WebUtils.getFileName(arg.getShortName()) + "." + newTypeName);
                    returnTypeName = newTypeName;
                    method.setReturnType(newType);
                    methodAndImports.getImports().removeIf(javaType -> javaType.equals(returnType));
                    methodAndImports.getImports().add(newType);
                } else{
                    returnTypeName = RenderingUtilities.calculateTypescriptTypeName(null, returnType);
                }
            }
            Parameter parameter = method.getParameters().get(0);
            if (StringUtils.equals("POST", method.getHttpMethod())) {
                method.addBodyLine(String.format("defHttp.post<%s>({ url: '%s%s', data: %s });",
                        returnTypeName, apiPrefix,
                        method.getRestPath(), parameter.getName()));
            }
            if (StringUtils.equals("GET", method.getHttpMethod())) {
                method.addBodyLine(String.format("defHttp.get<%s>({ url: '%s%s', data: %s });",
                        returnTypeName, apiPrefix,
                        method.getRestPath(), parameter.getName()));
            }
            method.setName(modelObjectName + StringUtils.capitalize(methodName));
        });

        methodAndImports.getMethods().forEach(tsBaseModelClass::addMethod);
        tsBaseModelClass.addImportedTypes(methodAndImports.getImports());
        tsBaseModelClass.setAttribute(Constants.WEB_PROJECT_ROOT_ALIAS, this.projectRootAlias);
        tsBaseModelClass.addImportedType(new FullyQualifiedTypescriptType("", "fe-ent-core.lib.utils.http.axios.defHttp"));

        return new GeneratedTypescriptFile(tsBaseModelClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                new DefaultTypescriptFormatter(this.context), this.projectRootAlias);
    }

    public List<GeneratedFile> contextGenerateAdditionalFiles() {
        return generatedFiles;
    }
}

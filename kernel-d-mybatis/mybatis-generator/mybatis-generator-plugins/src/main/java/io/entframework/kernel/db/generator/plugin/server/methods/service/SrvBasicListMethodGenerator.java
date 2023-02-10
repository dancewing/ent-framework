/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.generator.plugin.server.methods.service;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvBasicListMethodGenerator extends AbstractMethodGenerator {

    public SrvBasicListMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(getPojoRequestJavaType());
        imports.add(getPojoResponseJavaType());

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        returnType.addTypeArgument(getPojoResponseJavaType());
        Method method = new Method("list"); //$NON-NLS-1$
        method.setAbstract(this.isAbstract);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("list", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method).withImports(imports);
        // isAbstract 为 false，接口实现类
        if (!this.isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            builder.withImport(mapstructJavaType);
            String mapperValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());
            String dslMethod = "defaultSelectDSL";
            String selectMethod = "select";
//            if (GeneratorUtils.hasMethod(clientInterface, "leftJoinSelect")) {
//                selectMethod = "leftJoinSelect";
//                dslMethod = "defaultQuerySelectDSL";
//            }
            method.addBodyLine(String.format("List<%s> results = %s.%s(%s(request));", recordType.getShortName(),
                    mapperValName, selectMethod, dslMethod));
            method.addBodyLine(String.format("return %s.INSTANCE.toResponse(results);", mapstructJavaType.getShortName()));

        }
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);


        return builder.build();
    }
}

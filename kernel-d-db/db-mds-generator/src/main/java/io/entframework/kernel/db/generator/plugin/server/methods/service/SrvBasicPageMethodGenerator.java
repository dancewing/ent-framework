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

public class SrvBasicPageMethodGenerator extends AbstractMethodGenerator {

    public SrvBasicPageMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        FullyQualifiedJavaType responseJavaType = getPojoResponseJavaType();
        imports.add(getPojoRequestJavaType());
        imports.add(responseJavaType);

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("io.entframework.kernel.db.api.pojo.page.PageResult");
        returnType.addTypeArgument(responseJavaType);
        imports.add(returnType);
        imports.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method("page"); //$NON-NLS-1$
        method.setAbstract(this.isAbstract);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("page", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);
        // isAbstract 为 false，接口实现类
        if (!this.isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);

            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();

            builder.withImport(new FullyQualifiedJavaType("io.entframework.kernel.db.api.factory.PageResultFactory"));

            builder.withImport(mapstructJavaType);
            String mapperValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());
            imports.add(recordType);
            method.addBodyLine("long count = this.count(request);");
            method.addBodyLine("Integer pageNo = request.getPageNo();");
            method.addBodyLine("Integer pageSize = request.getPageSize();");
            String dslMethod = "defaultSelectDSL";
            String selectMethod = "select";
//            if (GeneratorUtils.hasMethod(clientInterface, "leftJoinSelect")) {
//                selectMethod = "leftJoinSelect";
//                dslMethod = "defaultQuerySelectDSL";
//            }
            //method.addBodyLine(String.format("List<%s> rows = %s.%s(defaultSelectDSL(request, pageNo, pageSize));", recordType.getShortName(), mapperValName, selectMethod));
            method.addBodyLine(String.format("List<%s> rows = %s.%s(%s(request));", recordType.getShortName(), mapperValName, selectMethod, dslMethod));
            method.addBodyLine(String.format("List<%s> results = %s.INSTANCE.toResponse(rows);", responseJavaType.getShortName(),
                    mapstructJavaType.getShortName()));
            method.addBodyLine("return PageResultFactory.createPageResult(results, count, pageSize, pageNo);");

        }
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);


        return builder.build();
    }
}

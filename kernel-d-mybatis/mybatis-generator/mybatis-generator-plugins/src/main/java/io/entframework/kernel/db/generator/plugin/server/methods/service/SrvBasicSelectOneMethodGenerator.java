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

public class SrvBasicSelectOneMethodGenerator extends AbstractMethodGenerator {

    public SrvBasicSelectOneMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        FullyQualifiedJavaType returnType = getPojoResponseJavaType(); //$NON-NLS-1$
        imports.add(returnType);
        Method method = new Method("selectOne"); //$NON-NLS-1$
        method.setAbstract(isAbstract);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("selectOne", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        if (!isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);

            FullyQualifiedJavaType mapperJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());
            imports.add(new FullyQualifiedJavaType("java.util.Optional"));
            imports.add(recordType);
            String dslMethod = "defaultSelectDSL";
            String selectMethod = "selectOne";
//            if (GeneratorUtils.hasMethod(clientInterface, "leftJoinSelect")) {
//                selectMethod = "leftJoinSelectOne";
//                dslMethod = "defaultQuerySelectDSL";
//            }
            method.addBodyLine(String.format("Optional<%s> result = %s.%s(%s(request));", recordType.getShortName(), mapperValName, selectMethod, dslMethod));
            method.addBodyLine(String.format("return %s.INSTANCE.toResponse(result.orElse(null));", mapstructJavaType.getShortName()));
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        return builder.build();
    }
}

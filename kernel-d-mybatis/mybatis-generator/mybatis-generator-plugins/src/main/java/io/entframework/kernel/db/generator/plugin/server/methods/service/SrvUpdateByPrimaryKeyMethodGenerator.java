/*
 *    Copyright 2006-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.entframework.kernel.db.generator.plugin.server.methods.service;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.plugin.server.methods.Utils;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvUpdateByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    public SrvUpdateByPrimaryKeyMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!Utils.generateUpdateByPrimaryKey(introspectedTable)) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Method method = new Method("update"); //$NON-NLS-1$
        method.setAbstract(isAbstract);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(getPojoResponseJavaType());
        imports.add(getPojoResponseJavaType());
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("update", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        if (!isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addAnnotation("@Transactional(rollbackFor = Exception.class)");
            imports.add(new FullyQualifiedJavaType("org.springframework.transaction.annotation.Transactional"));
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            String repositoryValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());
            imports.add(mapstructJavaType);

            imports.add(recordType);
            method.addBodyLine(String.format("%s record = %s.INSTANCE.toEntity(request);",
                    recordType.getShortName(),
                    mapstructJavaType.getShortName()));

            method.addBodyLine(String.format("return %s.INSTANCE.toResponse(%s.update(record));", mapstructJavaType.getShortName(), repositoryValName));

        }
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }
}
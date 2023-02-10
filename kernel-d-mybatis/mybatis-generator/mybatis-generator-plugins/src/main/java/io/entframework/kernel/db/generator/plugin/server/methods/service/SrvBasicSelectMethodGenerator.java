/*
 *    Copyright 2006-2020 the original author or authors.
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
import io.entframework.kernel.db.generator.utils.CommentHelper;
import io.entframework.kernel.rule.util.Maps;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SrvBasicSelectMethodGenerator extends AbstractMethodGenerator {

    public SrvBasicSelectMethodGenerator(BuildConfig builder) {
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
        Method method = new Method("select"); //$NON-NLS-1$
        method.setAbstract(this.isAbstract);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            Map<String, Object> variables = Maps.of("RepositoryName", getRepositoryJavaType().getShortName())
                    .and("EntityName", recordType.getShortName())
                    .and("RequestType", getPojoRequestJavaType().getShortName()).build();
            imports.add(getRepositoryJavaType());
            imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSLCompleter"));
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("select", "Service", variables));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }
        // isAbstract 为 false，接口实现类
        if (!this.isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            imports.add(mapstructJavaType);
            String mapperValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());
            imports.add(recordType);
            method.addBodyLine(String.format("List<%s> results = %s.select(defaultSelectDSL(request));", recordType.getShortName(), mapperValName));
            method.addBodyLine(String.format("return %s.INSTANCE.toResponse(results);", mapstructJavaType.getShortName()));

        }

        return MethodAndImports.withMethod(method)
                .withImports(imports).build();
    }
}

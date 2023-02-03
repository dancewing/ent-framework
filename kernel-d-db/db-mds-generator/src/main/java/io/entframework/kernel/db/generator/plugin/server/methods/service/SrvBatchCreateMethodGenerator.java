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
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvBatchCreateMethodGenerator extends AbstractMethodGenerator {

    public SrvBatchCreateMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();

        imports.add(getPojoRequestJavaType());
        imports.add(getPojoResponseJavaType());

        Method batchCreateMethod = new Method("batchCreate"); //$NON-NLS-1$
        batchCreateMethod.setAbstract(isAbstract);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        returnType.addTypeArgument(getPojoResponseJavaType());
        batchCreateMethod.setReturnType(returnType);

        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewListInstance(); //$NON-NLS-1$
        parameterType.addTypeArgument(getPojoRequestJavaType());
        imports.add(parameterType);
        batchCreateMethod.addParameter(new Parameter(parameterType, "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(batchCreateMethod, CommentHelper.INSTANCE.getComments("batchCreate", "Service"));
        } else {
            GeneratorUtils.addComment(batchCreateMethod, "{@inheritDoc}");
        }

        if (!isAbstract) {
            batchCreateMethod.addAnnotation("@Override");
            batchCreateMethod.setVisibility(JavaVisibility.PUBLIC);
            batchCreateMethod.addAnnotation("@Transactional(rollbackFor = Exception.class)");

            batchCreateMethod.addBodyLine("if (request == null || request.isEmpty()) {");
            batchCreateMethod.addBodyLine("return null;");
            batchCreateMethod.addBodyLine("}");

            FullyQualifiedJavaType mapperJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());
            imports.add(mapperJavaType);
            imports.add(mapstructJavaType);

            String mapperConvertEntity = String.format("%s.INSTANCE.toEntity", mapstructJavaType.getShortName());
            String mapperConvertResponse = String.format("%s.INSTANCE.toResponse", mapstructJavaType.getShortName());
            imports.add(recordType);
            batchCreateMethod.addBodyLine(String.format("List<%s> records = %s(request);", recordType.getShortName(), mapperConvertEntity));
            batchCreateMethod.addBodyLine(String.format("return %s(this.%s.batchCreate(records));", mapperConvertResponse, mapperValName));
        }

        return MethodAndImports.withMethod(batchCreateMethod)
                .withImports(imports).withStaticImports(staticImports)
                .build();
    }
}

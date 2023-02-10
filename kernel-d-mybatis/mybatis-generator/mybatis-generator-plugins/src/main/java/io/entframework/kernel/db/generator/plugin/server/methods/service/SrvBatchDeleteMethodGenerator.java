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
import io.entframework.kernel.rule.util.Inflection;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvBatchDeleteMethodGenerator extends AbstractMethodGenerator {

    public SrvBatchDeleteMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();

        imports.add(getPojoRequestJavaType());
        imports.add(getPojoResponseJavaType());

        Method batchDelete = new Method("batchDelete"); //$NON-NLS-1$
        batchDelete.setAbstract(isAbstract);
        batchDelete.setReturnType(FullyQualifiedJavaType.getIntInstance());
        batchDelete.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(batchDelete, CommentHelper.INSTANCE.getComments("batchDelete", "Service"));
        } else {
            GeneratorUtils.addComment(batchDelete, "{@inheritDoc}");
        }

        if (!isAbstract) {
            batchDelete.addAnnotation("@Override");
            batchDelete.setVisibility(JavaVisibility.PUBLIC);
            batchDelete.addAnnotation("@Transactional(rollbackFor = Exception.class)");

            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            String mapperValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());
            imports.add(repositoryJavaType);

            IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
            String pluralizeIds = Inflection.pluralize(pk.getJavaProperty());
            batchDelete.addBodyLine(String.format("if (request.get%s() == null || request.get%s().isEmpty()) {", StringUtils.capitalize(pluralizeIds), StringUtils.capitalize(pluralizeIds)));
            batchDelete.addBodyLine("return 0;");
            batchDelete.addBodyLine("}");
            batchDelete.addBodyLine(String.format("request.get%s().forEach(id -> {", StringUtils.capitalize(pluralizeIds)));
            batchDelete.addBodyLine(String.format("%s row = this.%s.get(id);", recordType.getShortName(), mapperValName));
            batchDelete.addBodyLine(String.format("this.%s.delete(row);", mapperValName));
            batchDelete.addBodyLine("});");
            batchDelete.addBodyLine(String.format("return request.get%s().size();", StringUtils.capitalize(pluralizeIds)));

        }

        return MethodAndImports.withMethod(batchDelete)
                .withImports(imports).withStaticImports(staticImports)
                .build();
    }
}

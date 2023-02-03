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
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvGeneralDeleteMethodGenerator extends AbstractMethodGenerator {

    public SrvGeneralDeleteMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        Method delete = new Method("delete"); //$NON-NLS-1$
        delete.addParameter(new Parameter(getPojoRequestJavaType(), "request"));  //$NON-NLS-1$
        delete.setReturnType(FullyQualifiedJavaType.getIntInstance()); //$NON-NLS-1$
        delete.setAbstract(isAbstract);

        Method deleteById = new Method("deleteById"); //$NON-NLS-1$
        deleteById.addParameter(new Parameter(getPojoRequestJavaType(), "request"));  //$NON-NLS-1$
        deleteById.setReturnType(FullyQualifiedJavaType.getIntInstance()); //$NON-NLS-1$
        deleteById.setAbstract(isAbstract);

        if (this.isAbstract) {
            GeneratorUtils.addComment(delete, CommentHelper.INSTANCE.getComments("delete", "Service"));
            GeneratorUtils.addComment(deleteById, CommentHelper.INSTANCE.getComments("deleteById", "Service"));
        } else {
            GeneratorUtils.addComment(delete, "{@inheritDoc}");
            GeneratorUtils.addComment(deleteById, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(delete).withMethod(deleteById)
                .withImports(imports);

        if (!isAbstract) {
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            builder.withImport(mapstructJavaType);
            builder.withImport(repositoryJavaType);
            builder.withImport(new FullyQualifiedJavaType("org.springframework.transaction.annotation.Transactional"));
            String mapperValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());

            delete.addAnnotation("@Override");
            delete.setVisibility(JavaVisibility.PUBLIC);
            delete.addAnnotation("@Transactional(rollbackFor = Exception.class)");

            delete.addBodyLine(String.format("return %s.delete(defaultDeleteDSL(request));", mapperValName));

            IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
            deleteById.addAnnotation("@Override");
            deleteById.setVisibility(JavaVisibility.PUBLIC);
            deleteById.addAnnotation("@Transactional(rollbackFor = Exception.class)");
            deleteById.addBodyLine(String.format("%s row = %s.INSTANCE.toEntity(request);", recordType.getShortName(), mapstructJavaType.getShortName()));
            deleteById.addBodyLine(String.format("return %s.deleteById(row);", mapperValName));
        }

        return builder.build();
    }
}

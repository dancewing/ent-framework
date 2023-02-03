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

import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.plugin.server.methods.Utils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SrvDeleteByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    public SrvDeleteByPrimaryKeyMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!Utils.generateSelectByPrimaryKey(introspectedTable)) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        Method deleteByPrimaryKey = new Method("delete");
        deleteByPrimaryKey.setAbstract(isAbstract);
        deleteByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());

        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            Parameter parameter = new Parameter(column.getFullyQualifiedJavaType(), column.getJavaProperty());
            deleteByPrimaryKey.addParameter(parameter);
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(deleteByPrimaryKey)
                .withImports(imports);

        if (!isAbstract) {

            deleteByPrimaryKey.addAnnotation("@Override");
            deleteByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
            deleteByPrimaryKey.addAnnotation("@Transactional(rollbackFor = Exception.class)");
            builder.withImport(new FullyQualifiedJavaType("org.springframework.transaction.annotation.Transactional"));

            FullyQualifiedJavaType repositoryJavaType = getRepositoryJavaType();
            FullyQualifiedJavaType mapstructJavaType = getMapstructJavaType();
            builder.withImport(mapstructJavaType);
            String repositoryValName = StringUtils.uncapitalize(repositoryJavaType.getShortName());

            List<String> params = new ArrayList<>();
            for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
                params.add(column.getJavaProperty());
            }
            deleteByPrimaryKey.addBodyLine(String.format("return %s.delete(%s);", repositoryValName, StringUtils.join(params, ", ")));
        }

        return builder.build();
    }
}

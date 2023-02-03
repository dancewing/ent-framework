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
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvIgnoreInsertMethodGenerator extends AbstractMethodGenerator {

    public SrvIgnoreInsertMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        Method method = new Method("createIfNotExist"); //$NON-NLS-1$
        method.setAbstract(isAbstract);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$

        if (!isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addAnnotation("@Transactional(rollbackFor = Exception.class)");
            imports.add(new FullyQualifiedJavaType("org.springframework.transaction.annotation.Transactional"));

            FullyQualifiedJavaType mapperJavaType = getRepositoryJavaType();
            imports.add(mapperJavaType);
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());

            method.addBodyLine(String.format("return %s.ignoreInsert(row);", mapperValName));

        }

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }
}

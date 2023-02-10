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
package io.entframework.kernel.db.generator.plugin.server.methods.repository;

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

public class RepGeneralDeleteMethodGenerator extends AbstractMethodGenerator {

    public RepGeneralDeleteMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        Method method = new Method("delete"); //$NON-NLS-1$
        method.addParameter(new Parameter(recordType, "row"));  //$NON-NLS-1$
        method.setReturnType(FullyQualifiedJavaType.getIntInstance()); //$NON-NLS-1$
        method.setAbstract(isAbstract);

        if (this.isAbstract) {
            Map<String, Object> variables = Maps.of("RepositoryName", getRepositoryJavaType().getShortName())
                    .and("EntityName", recordType.getShortName()).build();
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("delete", "Repository", "Row", variables));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        if (!isAbstract) {
            FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType();
            FullyQualifiedJavaType mapperJavaType = getMapperJavaType();
            builder.withImport(mapperSupportJavaType);
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addBodyLine(String.format("return %s.delete(defaultDeleteDSL(row));", mapperValName));
        }

        return builder.build();
    }
}

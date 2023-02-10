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
package io.entframework.kernel.db.generator.plugin.server.methods;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodParts {

    private final List<String> annotations;
    private final List<String> bodyLines;
    private final Set<FullyQualifiedJavaType> imports;
    private final List<Parameter> parameters;

    private MethodParts(Builder builder) {
        imports = builder.imports;
        bodyLines = builder.bodyLines;
        parameters = builder.parameters;
        annotations = builder.annotations;
    }

    public Set<FullyQualifiedJavaType> getImports() {
        return imports;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<String> getBodyLines() {
        return bodyLines;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public static class Builder {
        private final List<String> bodyLines = new ArrayList<>();
        private final Set<FullyQualifiedJavaType> imports = new HashSet<>();
        private final List<Parameter> parameters = new ArrayList<>();
        private final List<String> annotations = new ArrayList<>();

        public MethodParts.Builder withAnnotation(String annotation) {
            annotations.add(annotation);
            return this;
        }

        public MethodParts.Builder withBodyLine(String bodyLine) {
            this.bodyLines.add(bodyLine);
            return this;
        }

        public MethodParts.Builder withImport(FullyQualifiedJavaType importedType) {
            this.imports.add(importedType);
            return this;
        }

        public MethodParts.Builder withImports(Set<FullyQualifiedJavaType> imports) {
            this.imports.addAll(imports);
            return this;
        }

        public MethodParts.Builder withParameter(Parameter parameter) {
            parameters.add(parameter);
            return this;
        }

        public MethodParts build() {
            return new MethodParts(this);
        }
    }
}

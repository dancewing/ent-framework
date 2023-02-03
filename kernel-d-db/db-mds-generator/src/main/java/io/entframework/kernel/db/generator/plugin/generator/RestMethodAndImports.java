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
package io.entframework.kernel.db.generator.plugin.generator;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.*;

public class RestMethodAndImports {

    private final List<RestMethod> methods;
    private final Set<FullyQualifiedJavaType> imports;
    private final Set<String> staticImports;

    private RestMethodAndImports(Builder builder) {
        methods = builder.methods;
        imports = builder.imports;
        staticImports = builder.staticImports;
    }

    public List<RestMethod> getMethods() {
        return methods;
    }

    public Set<FullyQualifiedJavaType> getImports() {
        return imports;
    }

    public Set<String> getStaticImports() {
        return staticImports;
    }

    public static RestMethodAndImports.Builder withMethod(RestMethod method) {
        return new RestMethodAndImports.Builder().withMethod(method);
    }

    public static class Builder {
        private final List<RestMethod> methods = new ArrayList<>();
        private final Set<FullyQualifiedJavaType> imports = new TreeSet<>();
        private final Set<String> staticImports = new TreeSet<>();

        public Builder withMethod(RestMethod method) {
            this.methods.add(method);
            return this;
        }

        public Builder withImport(FullyQualifiedJavaType importedType) {
            this.imports.add(importedType);
            return this;
        }

        public Builder withImport(String importedType) {
            this.imports.add(new FullyQualifiedJavaType(importedType));
            return this;
        }

        public Builder withImports(Set<FullyQualifiedJavaType> imports) {
            this.imports.addAll(imports);
            return this;
        }

        public Builder withStaticImport(String staticImport) {
            this.staticImports.add(staticImport);
            return this;
        }

        public Builder withStaticImports(Set<String> staticImports) {
            this.staticImports.addAll(staticImports);
            return this;
        }

        public RestMethodAndImports build() {
            return new RestMethodAndImports(this);
        }
    }
}

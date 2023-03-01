/*
 *    Copyright 2016-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.select.render;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultSelectStatementProvider implements SelectStatementProvider {

    private final String selectStatement;

    private final Map<String, Object> parameters;

    private final SelectRenderer source;

    private DefaultSelectStatementProvider(Builder builder) {
        selectStatement = Objects.requireNonNull(builder.selectStatement);
        parameters = Collections.unmodifiableMap(Objects.requireNonNull(builder.parameters));
        source = builder.source;
    }

    @Override
    public SelectRenderer getSource() {
        return source;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String getSelectStatement() {
        return selectStatement;
    }

    public static Builder withSelectStatement(String selectStatement) {
        return new Builder().withSelectStatement(selectStatement);
    }

    public static class Builder {

        private String selectStatement;

        private final Map<String, Object> parameters = new HashMap<>();

        private SelectRenderer source;

        public Builder withSelectStatement(String selectStatement) {
            this.selectStatement = selectStatement;
            return this;
        }

        public Builder withParameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }

        public Builder withSource(SelectRenderer source) {
            this.source = source;
            return this;
        }

        public DefaultSelectStatementProvider build() {
            return new DefaultSelectStatementProvider(this);
        }

    }

}

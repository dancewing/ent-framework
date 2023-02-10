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
package org.mybatis.dynamic.sql.update.render;

import java.util.Objects;
import java.util.Optional;

public class SetAndWhere {
    private final String fieldName;
    private final String valuePhrase;
    private final Object value;

    private SetAndWhere(Builder builder) {
        fieldName = Objects.requireNonNull(builder.fieldName);
        valuePhrase = Objects.requireNonNull(builder.valuePhrase);
        value = builder.value;
    }

    public String fieldName() {
        return fieldName;
    }

    public String valuePhrase() {
        return valuePhrase;
    }

    public Optional<Object> value() {
        return Optional.ofNullable(value);
    }

    public static Builder withFieldName(String fieldName) {
        return new Builder().withFieldName(fieldName);
    }

    public static class Builder {
        private String fieldName;
        private String valuePhrase;
        private Object value;

        public Builder withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder withValuePhrase(String valuePhrase) {
            this.valuePhrase = valuePhrase;
            return this;
        }

        public Builder withValue(Object value) {
            this.value = value;
            return this;
        }

        public SetAndWhere build() {
            return new SetAndWhere(this);
        }

        public Optional<SetAndWhere> buildOptional() {
            return Optional.of(build());
        }
    }
}

package org.mybatis.dynamic.sql.update.render;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultUpdateRowStatementProvider<T> implements UpdateRowStatementProvider<T> {

    private final T row;

    private final String updateStatement;

    private final Map<String, Object> parameters = new HashMap<>();

    private DefaultUpdateRowStatementProvider(Builder<T> builder) {
        this.updateStatement = Objects.requireNonNull(builder.updateStatement);
        row = Objects.requireNonNull(builder.row);
        parameters.putAll(builder.parameters);
    }

    @Override
    public @NotNull T getRow() {
        return this.row;
    }

    @Override
    public String getUpdateStatement() {
        return this.updateStatement;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    public static <T> Builder<T> withRow(T row) {
        return new Builder<T>().withRow(row);
    }

    public static class Builder<T> {

        private String updateStatement;

        private T row;

        private final Map<String, Object> parameters = new HashMap<>();

        public Builder<T> withUpdateStatement(String updateStatement) {
            this.updateStatement = updateStatement;
            return this;
        }

        public Builder<T> withRow(T row) {
            this.row = row;
            return this;
        }

        public Builder<T> withParameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }

        public DefaultUpdateRowStatementProvider<T> build() {
            return new DefaultUpdateRowStatementProvider<>(this);
        }

    }

}

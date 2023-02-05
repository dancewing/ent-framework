package io.entframework.kernel.db.mds.extend.update.render;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DefaultEntityUpdateStatementProvider<T> implements EntityUpdateStatementProvider<T> {
    private final T row;

    private final String updateStatement;

    private DefaultEntityUpdateStatementProvider(DefaultEntityUpdateStatementProvider.Builder<T> builder) {
        this.updateStatement = Objects.requireNonNull(builder.updateStatement);
        row = Objects.requireNonNull(builder.row);
    }

    @Override
    public @NotNull T getRow() {
        return this.row;
    }

    @Override
    public String getUpdateStatement() {
        return this.updateStatement;
    }

    public static <T> DefaultEntityUpdateStatementProvider.Builder<T> withRow(T row) {
        return new DefaultEntityUpdateStatementProvider.Builder<T>().withRow(row);
    }

    public static class Builder<T> {
        private String updateStatement;
        private T row;

        public Builder<T> withUpdateStatement(String updateStatement) {
            this.updateStatement = updateStatement;
            return this;
        }

        public Builder<T> withRow(T row) {
            this.row = row;
            return this;
        }

        public DefaultEntityUpdateStatementProvider<T> build() {
            return new DefaultEntityUpdateStatementProvider<>(this);
        }
    }
}

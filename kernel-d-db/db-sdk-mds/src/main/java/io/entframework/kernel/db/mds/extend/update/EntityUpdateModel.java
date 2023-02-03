package io.entframework.kernel.db.mds.extend.update;

import io.entframework.kernel.db.mds.extend.update.render.EntityUpdateRenderer;
import io.entframework.kernel.db.mds.extend.update.render.EntityUpdateStatementProvider;
import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.exception.InvalidSqlException;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.AbstractColumnMapping;
import org.mybatis.dynamic.sql.util.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class EntityUpdateModel<T> {
    private final SqlTable table;
    private final T row;
    private final List<AbstractColumnMapping> columnMappings;

    private EntityUpdateModel(Builder<T> builder) {
        table = Objects.requireNonNull(builder.table);
        row = Objects.requireNonNull(builder.row);
        columnMappings = Objects.requireNonNull(builder.columnMappings);
        if (columnMappings.isEmpty()) {
            throw new InvalidSqlException(Messages.getString("ERROR.7")); //$NON-NLS-1$
        }
    }

    public <R> Stream<R> mapColumnMappings(Function<AbstractColumnMapping, R> mapper) {
        return columnMappings.stream().map(mapper);
    }

    public T row() {
        return row;
    }

    public SqlTable table() {
        return table;
    }

    @NotNull
    public EntityUpdateStatementProvider<T> render(RenderingStrategy renderingStrategy) {
        return EntityUpdateRenderer.withUpdateModel(this)
                .withRenderingStrategy(renderingStrategy)
                .build()
                .render();
    }

    public static <T> EntityUpdateModel.Builder<T> withRow(T row) {
        return new EntityUpdateModel.Builder<T>().withRow(row);
    }

    public static class Builder<T> {
        private SqlTable table;
        private T row;
        private final List<AbstractColumnMapping> columnMappings = new ArrayList<>();

        public Builder<T> withTable(SqlTable table) {
            this.table = table;
            return this;
        }

        public Builder<T> withRow(T row) {
            this.row = row;
            return this;
        }

        public Builder<T> withColumnMappings(List<AbstractColumnMapping> columnMappings) {
            this.columnMappings.addAll(columnMappings);
            return this;
        }

        public EntityUpdateModel<T> build() {
            return new EntityUpdateModel<>(this);
        }
    }
}

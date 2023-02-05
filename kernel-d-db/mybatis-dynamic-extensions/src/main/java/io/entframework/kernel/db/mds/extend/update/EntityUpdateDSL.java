package io.entframework.kernel.db.mds.extend.update;

import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class EntityUpdateDSL<T> implements Buildable<EntityUpdateModel<T>> {
    private final T row;
    private final SqlTable table;
    private final List<AbstractColumnMapping> columnMappings;

    private EntityUpdateDSL(Builder<T> builder) {
        this.row = Objects.requireNonNull(builder.row);
        this.table = Objects.requireNonNull(builder.table);
        columnMappings = builder.columnMappings;
    }

    public <F> ColumnMappingFinisher<F> map(SqlColumn<F> column) {
        return new ColumnMappingFinisher<>(column);
    }

    @NotNull
    @Override
    public EntityUpdateModel<T> build() {
        return EntityUpdateModel.withRow(row)
                .withTable(table)
                .withColumnMappings(columnMappings)
                .build();
    }

    public static <T> EntityUpdateDSL.IntoGatherer<T> update(T row) {
        return new EntityUpdateDSL.IntoGatherer<>(row);
    }

    public static class IntoGatherer<T> {
        private final T row;

        private IntoGatherer(T row) {
            this.row = row;
        }

        public EntityUpdateDSL<T> into(SqlTable table) {
            return new EntityUpdateDSL.Builder<T>().withRow(row).withTable(table).build();
        }
    }

    public class ColumnMappingFinisher<F> {
        private final SqlColumn<F> column;

        public ColumnMappingFinisher(SqlColumn<F> column) {
            this.column = column;
        }

        public EntityUpdateDSL<T> toProperty(String property) {
            columnMappings.add(PropertyMapping.of(column, property));
            return EntityUpdateDSL.this;
        }

        public EntityUpdateDSL<T> toPropertyWhenPresent(String property, Supplier<?> valueSupplier) {
            columnMappings.add(PropertyWhenPresentMapping.of(column, property, valueSupplier));
            return EntityUpdateDSL.this;
        }

        public EntityUpdateDSL<T> toNull() {
            columnMappings.add(NullMapping.of(column));
            return EntityUpdateDSL.this;
        }

        public EntityUpdateDSL<T> toConstant(String constant) {
            columnMappings.add(ConstantMapping.of(column, constant));
            return EntityUpdateDSL.this;
        }

        public EntityUpdateDSL<T> toStringConstant(String constant) {
            columnMappings.add(StringConstantMapping.of(column, constant));
            return EntityUpdateDSL.this;
        }
    }

    public static class Builder<T> {
        private T row;
        private SqlTable table;
        private final List<AbstractColumnMapping> columnMappings = new ArrayList<>();

        public Builder<T> withRow(T row) {
            this.row = row;
            return this;
        }

        public Builder<T> withTable(SqlTable table) {
            this.table = table;
            return this;
        }

        public Builder<T> withColumnMappings(Collection<AbstractColumnMapping> columnMappings) {
            this.columnMappings.addAll(columnMappings);
            return this;
        }

        public EntityUpdateDSL<T> build() {
            return new EntityUpdateDSL<>(this);
        }
    }
}

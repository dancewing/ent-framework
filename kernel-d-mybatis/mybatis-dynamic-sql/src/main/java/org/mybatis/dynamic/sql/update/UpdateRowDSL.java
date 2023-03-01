package org.mybatis.dynamic.sql.update;

import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class UpdateRowDSL<T> implements Buildable<UpdateRowModel<T>> {

	private final T row;

	private final SqlTable table;

	private final List<AbstractColumnMapping> columnMappings;

	private final List<AbstractColumnMapping> whereMappings;

	private final List<AbstractColumnMapping> parameterMappings;

	private UpdateRowDSL(Builder<T> builder) {
		this.row = Objects.requireNonNull(builder.row);
		this.table = Objects.requireNonNull(builder.table);
		columnMappings = builder.columnMappings;
		whereMappings = builder.whereMappings;
		parameterMappings = builder.parameterMappings;
	}

	public <F> SetClauseFinisher<F> set(SqlColumn<F> column) {
		return new SetClauseFinisher<>(column);
	}

	public <F> WhereMappingFinisher<F> where(SqlColumn<F> column) {
		return new WhereMappingFinisher<>(column);
	}

	@NotNull
	@Override
	public UpdateRowModel<T> build() {
		return UpdateRowModel.withRow(row).withTable(table).withColumnMappings(columnMappings)
				.withWhereMappings(whereMappings).withParameterMappings(parameterMappings).build();
	}

	public static <T> IntoGatherer<T> update(T row) {
		return new IntoGatherer<>(row);
	}

	public static class IntoGatherer<T> {

		private final T row;

		private IntoGatherer(T row) {
			this.row = row;
		}

		public UpdateRowDSL<T> into(SqlTable table) {
			return new Builder<T>().withRow(row).withTable(table).build();
		}

	}

	public class SetClauseFinisher<F> {

		private final SqlColumn<F> column;

		public SetClauseFinisher(SqlColumn<F> column) {
			this.column = column;
		}

		public UpdateRowDSL<T> toProperty(String property) {
			columnMappings.add(PropertyMapping.of(column, property));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toPropertyWhenPresent(String property, Supplier<?> valueSupplier) {
			columnMappings.add(PropertyWhenPresentMapping.of(column, property, valueSupplier));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toValue(F value) {
			return toValue(() -> value);
		}

		public UpdateRowDSL<T> toValue(Supplier<F> valueSupplier) {
			columnMappings.add(ValueMapping.of(column, valueSupplier));
			parameterMappings.add(ValueMapping.of(column, valueSupplier));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toNull() {
			columnMappings.add(NullMapping.of(column));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toConstant(String constant) {
			columnMappings.add(ConstantMapping.of(column, constant));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toStringConstant(String constant) {
			columnMappings.add(StringConstantMapping.of(column, constant));
			return UpdateRowDSL.this;
		}

	}

	public class WhereMappingFinisher<F> {

		private final SqlColumn<F> column;

		public WhereMappingFinisher(SqlColumn<F> column) {
			this.column = column;
		}

		public UpdateRowDSL<T> toProperty(String property) {
			whereMappings.add(PropertyMapping.of(column, property));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toPropertyWhenPresent(String property, Supplier<?> valueSupplier) {
			whereMappings.add(PropertyWhenPresentMapping.of(column, property, valueSupplier));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toValue(F value) {
			return toValue(() -> value);
		}

		public UpdateRowDSL<T> toValue(Supplier<F> valueSupplier) {
			parameterMappings.add(ValueMapping.of(column, valueSupplier));
			whereMappings.add(ValueMapping.of(column, valueSupplier));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toNull() {
			whereMappings.add(NullMapping.of(column));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toConstant(String constant) {
			whereMappings.add(ConstantMapping.of(column, constant));
			return UpdateRowDSL.this;
		}

		public UpdateRowDSL<T> toStringConstant(String constant) {
			whereMappings.add(StringConstantMapping.of(column, constant));
			return UpdateRowDSL.this;
		}

	}

	public static class Builder<T> {

		private T row;

		private SqlTable table;

		private final List<AbstractColumnMapping> columnMappings = new ArrayList<>();

		private final List<AbstractColumnMapping> whereMappings = new ArrayList<>();

		private final List<AbstractColumnMapping> parameterMappings = new ArrayList<>();

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

		public Builder<T> withWhereMappings(Collection<AbstractColumnMapping> whereMappings) {
			this.whereMappings.addAll(whereMappings);
			return this;
		}

		public UpdateRowDSL<T> build() {
			return new UpdateRowDSL<>(this);
		}

	}

}

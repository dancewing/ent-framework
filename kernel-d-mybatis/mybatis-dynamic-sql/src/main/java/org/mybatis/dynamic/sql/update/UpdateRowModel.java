package org.mybatis.dynamic.sql.update;

import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.exception.InvalidSqlException;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.render.UpdateRowRenderer;
import org.mybatis.dynamic.sql.update.render.UpdateRowStatementProvider;
import org.mybatis.dynamic.sql.util.AbstractColumnMapping;
import org.mybatis.dynamic.sql.util.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class UpdateRowModel<T> {

	private final SqlTable table;

	private final T row;

	private final List<AbstractColumnMapping> columnMappings;

	private final List<AbstractColumnMapping> whereMappings;

	private final List<AbstractColumnMapping> parameterMappings;

	private UpdateRowModel(Builder<T> builder) {
		table = Objects.requireNonNull(builder.table);
		row = Objects.requireNonNull(builder.row);
		columnMappings = Objects.requireNonNull(builder.columnMappings);
		whereMappings = Objects.requireNonNull(builder.whereMappings);
		parameterMappings = Objects.requireNonNull(builder.parameterMappings);

		if (columnMappings.isEmpty()) {
			throw new InvalidSqlException(Messages.getString("ERROR.7")); //$NON-NLS-1$
		}
		if (whereMappings.isEmpty()) {
			throw new InvalidSqlException(Messages.getString("ERROR.31")); //$NON-NLS-1$
		}
	}

	public <R> Stream<R> mapColumnMappings(Function<AbstractColumnMapping, R> mapper) {
		return columnMappings.stream().map(mapper);
	}

	public <R> Stream<R> whereMappings(Function<AbstractColumnMapping, R> mapper) {
		return whereMappings.stream().map(mapper);
	}

	public <R> Stream<R> parameterMappings(Function<AbstractColumnMapping, R> mapper) {
		return parameterMappings.stream().map(mapper);
	}

	public T row() {
		return row;
	}

	public SqlTable table() {
		return table;
	}

	@NotNull
	public UpdateRowStatementProvider<T> render(RenderingStrategy renderingStrategy) {
		return UpdateRowRenderer.withUpdateModel(this).withRenderingStrategy(renderingStrategy).build().render();
	}

	public static <T> Builder<T> withRow(T row) {
		return new Builder<T>().withRow(row);
	}

	public static class Builder<T> {

		private SqlTable table;

		private T row;

		private final List<AbstractColumnMapping> columnMappings = new ArrayList<>();

		private final List<AbstractColumnMapping> whereMappings = new ArrayList<>();

		private final List<AbstractColumnMapping> parameterMappings = new ArrayList<>();

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

		public Builder<T> withWhereMappings(List<AbstractColumnMapping> whereMappings) {
			this.whereMappings.addAll(whereMappings);
			return this;
		}

		public Builder<T> withParameterMappings(List<AbstractColumnMapping> parameterMappings) {
			this.parameterMappings.addAll(parameterMappings);
			return this;
		}

		public UpdateRowModel<T> build() {
			return new UpdateRowModel<>(this);
		}

	}

}

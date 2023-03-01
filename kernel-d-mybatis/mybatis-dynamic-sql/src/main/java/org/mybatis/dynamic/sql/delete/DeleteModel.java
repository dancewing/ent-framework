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
package org.mybatis.dynamic.sql.delete;

import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.EntityModel;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.delete.render.DeleteRenderer;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.where.WhereModel;

import java.util.Objects;
import java.util.Optional;

public class DeleteModel extends EntityModel {

	private final SqlTable table;

	private final String tableAlias;

	private final WhereModel whereModel;

	private DeleteModel(Builder builder) {
		super(builder.entityClass);
		table = Objects.requireNonNull(builder.table);
		whereModel = builder.whereModel;
		tableAlias = builder.tableAlias;
	}

	public SqlTable table() {
		return table;
	}

	public Optional<String> tableAlias() {
		return Optional.ofNullable(tableAlias);
	}

	public Optional<WhereModel> whereModel() {
		return Optional.ofNullable(whereModel);
	}

	@NotNull
	public DeleteStatementProvider render(RenderingStrategy renderingStrategy) {
		return DeleteRenderer.withDeleteModel(this).withRenderingStrategy(renderingStrategy).build().render();
	}

	public static Builder withTable(SqlTable table) {
		return new Builder().withTable(table);
	}

	public static class Builder {

		private SqlTable table;

		private String tableAlias;

		private WhereModel whereModel;

		private Class<?> entityClass;

		public Builder withTable(SqlTable table) {
			this.table = table;
			return this;
		}

		public Builder withTableAlias(String tableAlias) {
			this.tableAlias = tableAlias;
			return this;
		}

		public Builder withWhereModel(WhereModel whereModel) {
			this.whereModel = whereModel;
			return this;
		}

		public Builder withEntityClass(Class<?> entityClass) {
			this.entityClass = entityClass;
			return this;
		}

		public DeleteModel build() {
			return new DeleteModel(this);
		}

	}

}

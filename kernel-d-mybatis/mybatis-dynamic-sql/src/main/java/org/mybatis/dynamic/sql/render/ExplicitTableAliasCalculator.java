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
package org.mybatis.dynamic.sql.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.SqlTable;

public class ExplicitTableAliasCalculator implements TableAliasCalculator {

	private final Map<SqlTable, String> aliases;

	protected ExplicitTableAliasCalculator(Map<SqlTable, String> aliases) {
		this.aliases = Objects.requireNonNull(aliases);
	}

	@Override
	public Optional<String> aliasForColumn(SqlTable table) {
		return explicitAliasOrTableAlias(table);
	}

	@Override
	public Optional<String> aliasForTable(SqlTable table) {
		return explicitAliasOrTableAlias(table);
	}

	private Optional<String> explicitAliasOrTableAlias(SqlTable table) {
		String alias = aliases.get(table);
		if (alias == null) {
			return table.tableAlias();
		}
		else {
			return Optional.of(alias);
		}
	}

	public static TableAliasCalculator of(SqlTable table, String alias) {
		Map<SqlTable, String> tableAliases = new HashMap<>();
		tableAliases.put(table, alias);
		return of(tableAliases);
	}

	public static TableAliasCalculator of(Map<SqlTable, String> aliases) {
		return new ExplicitTableAliasCalculator(aliases);
	}

}

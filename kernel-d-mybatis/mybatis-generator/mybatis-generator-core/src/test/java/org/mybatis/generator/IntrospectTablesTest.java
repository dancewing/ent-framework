/*
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.TableConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntrospectTablesTest {

	@Test
	void testIntrospectTables() throws Exception {

		createDatabase();
		Context context = new Context(ModelType.FLAT);
		JDBCConnectionConfiguration connectionConfiguration = new JDBCConnectionConfiguration();
		connectionConfiguration.setConnectionURL("jdbc:hsqldb:mem:aname");
		connectionConfiguration.setPassword("");
		connectionConfiguration.setUserId("sa");
		connectionConfiguration.setDriverClass("org.hsqldb.jdbcDriver");
		context.setJdbcConnectionConfiguration(connectionConfiguration);
		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName("%");
		context.addTableConfiguration(tableConfiguration);
		VerboseProgressCallback callback = new VerboseProgressCallback();
		Set<String> fullyQualifiedTableNames = new HashSet<>();
		List<String> warning = new ArrayList<>();
		try {
			context.introspectTables(callback, warning, fullyQualifiedTableNames);
			List<IntrospectedTable> introspectedTables = context.getIntrospectedTables();
			assertTrue(introspectedTables.size() > 0);
		}
		catch (Exception ex) {

		}
	}

	static void createDatabase() throws Exception {
		SqlScriptRunner scriptRunner = new SqlScriptRunner(
				IntrospectTablesTest.class.getResourceAsStream("/scripts/CreateDB.sql"), "org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:mem:aname", "sa", "");
		scriptRunner.executeScript();
	}

}

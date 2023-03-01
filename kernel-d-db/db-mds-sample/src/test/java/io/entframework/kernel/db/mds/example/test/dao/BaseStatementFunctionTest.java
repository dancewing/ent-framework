/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.StudentDynamicSqlSupport;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseStatementFunctionTest extends JUnitDaoWithFraud {

	@Test
	void testSelectManyStatement() {
		Student tsc = fraudStudent();
		Student scd = generalRepository.insert(tsc);
		SelectStatementProvider statement = SqlBuilder.select(StudentDynamicSqlSupport.selectList).from(Student.class)
				.where(StudentDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N)).build()
				.render(RenderingStrategies.MYBATIS3);
		List<Student> students = generalMapperSupport.selectMany(statement);
		assertEquals(1, students.size());
		assertEquals(scd.getId(), students.get(0).getId());
	}

}

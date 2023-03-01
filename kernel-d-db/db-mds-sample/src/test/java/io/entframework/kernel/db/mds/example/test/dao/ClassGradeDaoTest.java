/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.ClassGradeDynamicSqlSupport;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClassGradeDaoTest extends JUnitDaoWithFraud {

    @Test
    void testCreate() {
        ClassGrade tg = fraudClassGrade();
        generalRepository.insert(tg.getRegulator());
        tg.setRegulatorId(tg.getRegulator().getId());
        ClassGrade tgd = generalRepository.insert(tg);
        assertNotNull(tgd);
        assertNotNull(tgd.getId());
    }

    @Test
    void testInsertSelective() {
        ClassGrade tg = fraudClassGrade();
        generalRepository.insert(tg.getRegulator());
        tg.setRegulatorId(tg.getRegulator().getId());
        assertNotNull(tg.getDescription());
        tg.setDescription(null);
        generalRepository.insertSelective(tg);
        Optional<ClassGrade> tgd = generalRepository.selectByPrimaryKey(ClassGrade.class, tg.getId());
        assertTrue(tgd.isPresent());
        assertNotNull(tgd);
        assertNull(tgd.get().getDescription());
    }

    // @Test
    void testBatchCreate() {
        List<ClassGrade> classGrades = fraudList(this::fraudClassGrade);
        List<ClassGrade> records = generalRepository.insertMultiple(classGrades);
        assertEquals(records.size(), classGrades.size());
    }

    @Test
    void testLeftJoinSelectOne() {
        ClassGrade tg = fraudClassGrade();
        generalRepository.insert(tg.getRegulator());
        tg.setRegulatorId(tg.getRegulator().getId());
        ClassGrade grade = generalRepository.insert(tg);
        tg.getStudents().forEach(student -> {
            student.setGradeId(tg.getId());
            generalRepository.insert(student);
        });

        ClassGrade query = new ClassGrade();
        query.setId(grade.getId());
        Optional<ClassGrade> result = this.generalRepository.selectOne(query);
        assertTrue(result.isPresent());
        ClassGrade classGrade = result.get();
        assertNotNull(classGrade.getStudents());
        assertTrue(classGrade.getStudents().size() > 0);
        assertNotNull(classGrade.getRegulator());
    }

    @Test
    void testLeftJoinSelect() {
        repeat(() -> {
            ClassGrade tg = fraudClassGrade();
            generalRepository.insert(tg.getRegulator());
            tg.setRegulatorId(tg.getRegulator().getId());
            return generalRepository.insert(tg);
        }, 5);
        long count = this.generalRepository.count(ClassGrade.class, CountDSL::where);
        assertEquals(5, count);
        ClassGrade query = new ClassGrade();
        List<ClassGrade> results = this.generalRepository.selectBy(query, 1, 10, true);
        assertEquals(5, results.size());
    }

    @Test
    void delete() {
        ClassGrade tg = fraudClassGrade();
        generalRepository.insert(tg.getRegulator());
        tg.setRegulatorId(tg.getRegulator().getId());
        ClassGrade grade = generalRepository.insert(tg);
        assertNotNull(grade.getId());
        generalRepository.delete(grade);
        assertThrows(DaoException.class, () -> generalRepository.get(ClassGrade.class, grade.getId()));
    }

    @Test
    void updateByStatementProvider() {
        ClassGrade tg = fraudClassGrade();
        tg.setRegulatorId(1L);
        ClassGrade grade = generalRepository.insert(tg);
        UpdateStatementProvider statement = SqlBuilder.update(ClassGrade.class).set(ClassGradeDynamicSqlSupport.name)
                .equalTo("test").where(ClassGradeDynamicSqlSupport.id, SqlBuilder.isEqualTo(grade.getId())).build()
                .render(RenderingStrategies.MYBATIS3);
        int k = this.generalMapperSupport.update(statement);
        assertTrue(k > 0);
        ClassGrade gradeGet = generalRepository.get(ClassGrade.class, grade.getId());
        assertEquals("test", gradeGet.getName());
    }

    @Test
    void get() {
        ClassGrade tg = fraudClassGrade();
        generalRepository.insert(tg.getRegulator());
        tg.setRegulatorId(tg.getRegulator().getId());
        ClassGrade grade = generalRepository.insert(tg);
        tg.getStudents().forEach(student -> {
            student.setGradeId(tg.getId());
            generalRepository.insert(student);
        });
        ClassGrade gradeGet = generalRepository.get(ClassGrade.class, grade.getId());
        assertNotNull(gradeGet);
        assertNotNull(gradeGet.getRegulatorId());
        assertNotNull(gradeGet.getRegulator());
        assertEquals(gradeGet.getRegulator().getId(), gradeGet.getRegulatorId());
        assertEquals(gradeGet.getRegulator().getId(), grade.getRegulatorId());
        assertEquals(gradeGet.getRegulator().getName(), grade.getRegulator().getName());
        assertNotNull(gradeGet.getStudents());
        assertEquals(gradeGet.getStudents().size(), grade.getStudents().size());
    }

}
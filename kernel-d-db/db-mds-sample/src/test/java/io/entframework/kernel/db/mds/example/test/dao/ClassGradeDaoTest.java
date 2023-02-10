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
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
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
        ClassGrade tgd = generalRepository.insert(tg);
        assertNotNull(tgd);
        assertNotNull(tgd.getId());
        assertNotNull(tgd.getStudents());
        tgd.getStudents().forEach(student -> assertNotNull(student.getId()));
        assertNotNull(tgd.getRegulatorId());
        assertNotNull(tgd.getRegulator());
        assertNotNull(tgd.getRegulator().getId());
    }

    @Test
    void testInsertSelective() {
        ClassGrade tg = fraudClassGrade();
        ClassGrade tgd = generalRepository.insertSelective(tg);
        assertNotNull(tgd);
        assertNotNull(tgd.getId());
        assertNotNull(tgd.getStudents());
        tgd.getStudents().forEach(student -> assertNotNull(student.getId()));
        assertNotNull(tgd.getRegulatorId());
        assertNotNull(tgd.getRegulator());
        assertNotNull(tgd.getRegulator().getId());
    }

    //@Test
    void testBatchCreate() {
        List<ClassGrade> classGrades = fraudList(this::fraudClassGrade);
        List<ClassGrade> records = generalRepository.insertMultiple(classGrades);
        assertEquals(records.size(), classGrades.size());
    }


    @Test
    void testLeftJoinSelectOne() {
        ClassGrade grade = generalRepository.insert(fraudClassGrade());
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
        repeat(() -> generalRepository.insert(fraudClassGrade()), 5);
        long count = this.generalRepository.count(ClassGrade.class, CountDSL::where);
        assertEquals(5, count);
        ClassGrade query = new ClassGrade();
        List<ClassGrade> results = this.generalRepository.selectBy(query, 1, 10, true);
        assertEquals(5, results.size());
    }

    @Test
    void updateAll() {
        String newDesc = "this is new desc of class";
        ClassGrade target = fraudClassGrade();
        List<Student> students = fraudList(this::fraudStudent, 4);
        target.setStudents(students);
        Teacher teacher = fraudTeacher();
        target.setRegulator(teacher);
        generalRepository.insert(target);
        ClassGrade cgGet = generalRepository.get(ClassGrade.class, target.getId());
        assertNotNull(cgGet);
        assertNotNull(cgGet.getRegulator());
        assertEquals(cgGet.getRegulatorId(), cgGet.getRegulator().getId());

        List<Student> changedStudents = fraudList(this::fraudStudent, 5);
        target.setStudents(changedStudents);
        target.setDescription(newDesc);
        generalRepository.update(target);
        ClassGrade gradeGet = generalRepository.get(ClassGrade.class, target.getId());
        assertEquals(newDesc, gradeGet.getDescription());
        assertNotNull(gradeGet.getRegulatorId());
        assertNotNull(gradeGet.getRegulator());
        assertEquals(gradeGet.getRegulator().getId(), gradeGet.getRegulatorId());
        assertEquals(gradeGet.getRegulator().getName(), teacher.getName());
        assertNotNull(gradeGet.getStudents());
        //assertEquals(gradeGet.getStudents().size(), 5);
    }

    @Test
    void delete() {
        ClassGrade grade = generalRepository.insert(fraudClassGrade());
        assertNotNull(grade.getId());
        generalRepository.delete(grade);
        assertThrows(DaoException.class, () -> generalRepository.get(ClassGrade.class, grade.getId()));
    }

    @Test
    void updateByStatementProvider() {
        ClassGrade grade = generalRepository.insert(fraudClassGrade());
        UpdateStatementProvider statement = SqlBuilder.update(ClassGradeDynamicSqlSupport.classGrade)
                .set(ClassGradeDynamicSqlSupport.name).equalTo("test")
                .where(ClassGradeDynamicSqlSupport.id, SqlBuilder.isEqualTo(grade.getId()))
                .build().render(RenderingStrategies.MYBATIS3);
        int k = this.generalMapperSupport.update(statement);
        assertTrue(k > 0);
        ClassGrade gradeGet = generalRepository.get(ClassGrade.class, grade.getId());
        assertEquals("test", gradeGet.getName());
    }

    @Test
    void get() {
        ClassGrade grade = generalRepository.insert(fraudClassGrade());
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
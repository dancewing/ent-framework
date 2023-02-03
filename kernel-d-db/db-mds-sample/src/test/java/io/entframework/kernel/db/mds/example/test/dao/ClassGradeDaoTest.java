/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.mapper.ClassGradeDynamicSqlSupport;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassGradeDaoTest extends JUnitDaoWithFraud {

    @Test
    public void testCreate() {
        ClassGrade tg = fraudClassGrade();
        ClassGrade tgd = classGradeRepository.insert(tg);
        assertNotNull(tgd);
        assertNotNull(tgd.getId());
        assertNotNull(tgd.getStudents());
        tgd.getStudents().forEach(student -> assertNotNull(student.getId()));
        assertNotNull(tgd.getRegulatorId());
        assertNotNull(tgd.getRegulator());
        assertNotNull(tgd.getRegulator().getId());
    }

    @Test
    public void testBatchCreate() {
        List<ClassGrade> classGrades = fraudList(this::fraudClassGrade);
        List<ClassGrade> records = classGradeRepository.insertMultiple(classGrades);
        TestCase.assertEquals(records.size(), classGrades.size());
    }


    @Test
    public void testLeftJoinSelectOne() {
        ClassGrade grade = classGradeRepository.insert(fraudClassGrade());
        ClassGrade query = new ClassGrade();
        query.setId(grade.getId());
        Optional<ClassGrade> result = this.classGradeRepository.selectOne(query);
        TestCase.assertTrue(result.isPresent());
        ClassGrade classGrade = result.get();
        assertNotNull(classGrade.getStudents());
        TestCase.assertTrue(classGrade.getStudents().size() > 0);
        assertNotNull(classGrade.getRegulator());
    }

    @Test
    public void testLeftJoinSelect() {
        repeat(() -> classGradeRepository.insert(fraudClassGrade()), 5);
        long count = this.classGradeRepository.count(CountDSL::where);
        TestCase.assertEquals(5, count);
        ClassGrade query = new ClassGrade();
        List<ClassGrade> results = this.classGradeRepository.selectBy(query);
        TestCase.assertEquals(5, results.size());
    }

    @Test
    public void updateAll() {
        String newDesc = "this is new desc of class";
        ClassGrade target = fraudClassGrade();
        List<Student> students = fraudList(this::fraudStudent, 4);
        target.setStudents(students);
        Teacher teacher = fraudTeacher();
        target.setRegulator(teacher);
        classGradeRepository.insert(target);
        ClassGrade cgGet = classGradeRepository.get(target.getId());
        assertNotNull(cgGet);
        assertNotNull(cgGet.getRegulator());
        assertEquals(cgGet.getRegulatorId(), cgGet.getRegulator().getId());

        List<Student> changedStudents = fraudList(this::fraudStudent, 5);
        target.setStudents(changedStudents);
        target.setDescription(newDesc);
        classGradeRepository.update(target);
        ClassGrade gradeGet = classGradeRepository.get(target.getId());
        assertEquals(newDesc, gradeGet.getDescription());
        assertNotNull(gradeGet.getRegulatorId());
        assertNotNull(gradeGet.getRegulator());
        assertEquals(gradeGet.getRegulator().getId(), gradeGet.getRegulatorId());
        assertEquals(gradeGet.getRegulator().getName(), teacher.getName());
        assertNotNull(gradeGet.getStudents());
        assertEquals(gradeGet.getStudents().size(), 5);
    }

    @Test
    public void delete() {
        ClassGrade grade = classGradeRepository.insert(fraudClassGrade());
        assertNotNull(grade.getId());
        classGradeRepository.delete(grade);
        assertThrows(DaoException.class, () -> classGradeRepository.get(grade.getId()));
    }

    @Test
    public void updateByStatementProvider() {
        ClassGrade grade = classGradeRepository.insert(fraudClassGrade());
        UpdateStatementProvider statement = SqlBuilder.update(ClassGradeDynamicSqlSupport.classGrade)
                .set(ClassGradeDynamicSqlSupport.name).equalTo("test")
                .where(ClassGradeDynamicSqlSupport.id, SqlBuilder.isEqualTo(grade.getId()))
                .build().render(RenderingStrategies.MYBATIS3);
        int k = this.classGradeRepository.update(statement);
        TestCase.assertTrue(k > 0);
        ClassGrade gradeGet = classGradeRepository.get(grade.getId());
        assertEquals("test", gradeGet.getName());
    }

    @Test
    public void get() {
        ClassGrade grade = classGradeRepository.insert(fraudClassGrade());
        ClassGrade gradeGet = classGradeRepository.get(grade.getId());
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
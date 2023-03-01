/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.StudentDynamicSqlSupport;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StudentDaoTest extends JUnitDaoWithFraud {

    @Test
    void createAndSelect() {
        Student tsc = fraudStudent();
        Student scd = generalRepository.insert(tsc);
        assertNotNull(scd.getId());
        assertEquals(YesOrNotEnum.N, scd.getDelFlag());
        assertEquals(1L, scd.getVersion().longValue());

        List<Student> students = generalRepository.select(Student.class, c -> {
            c.where(StudentDynamicSqlSupport.id, SqlBuilder.isEqualTo(scd.getId()));
            return c;
        });
        assertEquals(1, students.size());
        assertEquals(scd.getId(), students.get(0).getId());
    }

    @Test
    void batchCreate() {
        List<Student> students = fraudList(this::fraudStudent);
        List<Student> scList = generalRepository.insertMultiple(students);
        assertNotNull(scList);
        scList.forEach(student -> {
            assertNotNull(student);
            assertNotNull(student.getId());
            assertEquals(YesOrNotEnum.N, student.getDelFlag());
            assertEquals(1L, student.getVersion().longValue());
        });
    }

    @Test
    void update() {
        Student student = generalRepository.insert(fraudStudent());
        String newHomeTown = fraudUnique("new_town");
        LocalDate newBirthday = LocalDate.now().plusYears(-17);
        student.setBirthday(newBirthday);
        student.setHometown(newHomeTown);
        Student su = generalRepository.update(student);
        Student sg = generalRepository.get(Student.class, student.getId());
        assertNotNull(su);
        assertNotNull(sg);
        assertEquals(sg.getBirthday(), newBirthday);
        assertEquals(sg.getHometown(), newHomeTown);
        assertEquals(1, student.getVersion().longValue());
        assertEquals(2, sg.getVersion().longValue());
    }

    /**
     * logic delete
     */
    @Test
    void delete() {
        Student student = generalRepository.insert(fraudStudent());
        generalRepository.delete(student);
        assertThrows(DaoException.class, () -> generalRepository.get(Student.class, student.getId()));
        Optional<Student> sg = generalRepository.selectByPrimaryKey(Student.class, student.getId());
        assertTrue(sg.isPresent());
        assertEquals(YesOrNotEnum.Y, sg.get().getDelFlag());
        assertEquals(2, sg.get().getVersion().longValue());
    }

    @Test
    void get() {
        Student student = generalRepository.insert(fraudStudent());
        Student sg = generalRepository.get(Student.class, student.getId());
        assertNotNull(sg);
        assertEquals(sg.getId(), student.getId());
    }

    /*
     * @Test void updatePart() { Student student =
     * generalRepository.create(fraudStudent()); String newHomeTown =
     * "this is new hometown"; student.setHometown(newHomeTown); int count =
     * generalRepository.updateByPrimaryKeySelective(student); assertTrue(count > 0);
     * Student scg = generalRepository.get(student.getId());
     * assertNotNull(scg.getHometown()); assertEquals(scg.getHometown(), newHomeTown); }
     */

}
/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class StudentDaoTest extends JUnitDaoWithFraud {

    @Test
    public void create() {
        Student tsc = fraudStudent();
        Student scd = studentRepository.insert(tsc);
        assertNotNull(scd.getId());
        assertEquals(YesOrNotEnum.N, scd.getDelFlag());
        assertEquals(1L, scd.getVersion().longValue());
    }

    @Test
    public void batchCreate() {
        List<Student> students = fraudList(this::fraudStudent);
        List<Student> scList = studentRepository.insertMultiple(students);
        TestCase.assertNotNull(scList);
        scList.forEach(student -> {
            assertNotNull(student);
            assertNotNull(student.getId());
            assertEquals(YesOrNotEnum.N, student.getDelFlag());
            assertEquals(1L, student.getVersion().longValue());
        });
    }

    @Test
    public void update() {
        Student student = studentRepository.insert(fraudStudent());
        String newHomeTown = fraudUnique("new_town");
        LocalDate newBirthday = LocalDate.now().plusYears(-17);
        student.setBirthday(newBirthday);
        student.setHometown(newHomeTown);
        Student su = studentRepository.update(student);
        Student sg = studentRepository.get(student.getId());
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
    public void delete() {
        Student student = studentRepository.insert(fraudStudent());
        studentRepository.delete(student);
        assertThrows(DaoException.class, () -> studentRepository.get(student.getId()));
        Optional<Student> sg = studentRepository.selectByPrimaryKey(student.getId());
        TestCase.assertTrue(sg.isPresent());
        assertSame(sg.get().getDelFlag(), YesOrNotEnum.Y);
        assertEquals(sg.get().getVersion().longValue(), 2);
    }

    @Test
    public void get() {
        Student student = studentRepository.insert(fraudStudent());
        Student sg = studentRepository.get(student.getId());
        assertNotNull(sg);
        assertEquals(sg.getId(), student.getId());
    }

/*    @Test
    public void updatePart() {
        Student student = studentRepository.create(fraudStudent());
        String newHomeTown = "this is new hometown";
        student.setHometown(newHomeTown);
        int count = studentRepository.updateByPrimaryKeySelective(student);
        assertTrue(count > 0);
        Student scg = studentRepository.get(student.getId());
        assertNotNull(scg.getHometown());
        assertEquals(scg.getHometown(), newHomeTown);
    }*/
}
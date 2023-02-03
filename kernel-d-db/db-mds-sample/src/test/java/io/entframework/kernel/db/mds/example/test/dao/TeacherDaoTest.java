/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeacherDaoTest extends JUnitDaoWithFraud {

    @Test
    public void create() {
        Teacher ttc = fraudTeacher();
        Teacher ttcd = teacherRepository.insert(ttc);
        assertNotNull(ttcd.getId());
    }

    @Test
    public void batchCreate() {
        List<Teacher> list = fraudList(this::fraudTeacher);
        List<Teacher> listd = teacherRepository.insertMultiple(list);
        TestCase.assertNotNull(listd);
        listd.forEach(teacher -> {
            assertNotNull(teacher);
            assertNotNull(teacher.getId());
        });
    }

    @Test
    public void update() {
        Teacher teacher = teacherRepository.insert(fraudTeacher());
        LocalDate newBirth = LocalDate.now().plusYears(-30);
        teacher.setBirthday(newBirth);
        Teacher tcu = teacherRepository.update(teacher);
        assertEquals(tcu.getBirthday(), newBirth);
    }

    @Test
    public void delete() {
        Teacher teacher = teacherRepository.insert(fraudTeacher());
        teacherRepository.delete(teacher);
        assertThrows(DaoException.class, () -> teacherRepository.get(teacher.getId()));
    }

    @Test
    public void get() {
        Teacher teacher = teacherRepository.insert(fraudTeacher());
        Teacher tcg = teacherRepository.get(teacher.getId());
        assertNotNull(tcg);
        assertEquals(tcg.getId(), teacher.getId());
    }

    @Test
    public void getAll() {
        Teacher teacher = teacherRepository.insert(fraudTeacher());
        List<Teacher> teachers = teacherRepository.selectBy(new Teacher());
        TestCase.assertTrue(teachers.size() >= 1);
    }
}
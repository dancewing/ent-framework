/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;


import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.repository.ClassGradeRepository;
import io.entframework.kernel.db.mds.example.repository.StudentRepository;
import io.entframework.kernel.db.mds.example.repository.TeacherRepository;
import io.entframework.kernel.db.mds.example.test.JUnitWithFraud;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JUnitDaoWithFraud extends JUnitWithFraud {

    @Autowired
    protected ClassGradeRepository classGradeRepository;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected TeacherRepository teacherRepository;


    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        clean();
    }

    @AfterEach
    public void tearDown() throws Exception {
        clean();
    }

    private void clean() {
        this.classGradeRepository.deleteBy(new ClassGrade());
        this.studentRepository.deleteBy(new Student());
        this.teacherRepository.deleteBy(new Teacher());
    }
}

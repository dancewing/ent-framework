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
import io.entframework.kernel.db.mds.example.test.JUnitWithFraud;
import io.entframework.kernel.db.mds.mapper.GeneralMapperSupport;
import io.entframework.kernel.db.mds.repository.GeneralRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JUnitDaoWithFraud extends JUnitWithFraud {
    @Autowired
    protected GeneralRepository generalRepository;
    @Autowired
    protected GeneralMapperSupport generalMapperSupport;

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
        this.generalRepository.deleteBy(new ClassGrade());
        this.generalRepository.deleteBy(new Student());
        this.generalRepository.deleteBy(new Teacher());
    }
}

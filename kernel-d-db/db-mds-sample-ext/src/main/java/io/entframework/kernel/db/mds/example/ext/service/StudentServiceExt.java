/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.service;

import io.entframework.kernel.db.mds.example.ext.repository.StudentRepositoryExt;
import io.entframework.kernel.db.mds.example.service.impl.StudentServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceExt extends StudentServiceImpl {
    public StudentServiceExt(StudentRepositoryExt studentRepository) {
        super(studentRepository);
    }
}

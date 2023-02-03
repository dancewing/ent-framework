/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.repository;

import io.entframework.kernel.db.mds.example.ext.entity.StudentExt;
import io.entframework.kernel.db.mds.example.repository.impl.StudentRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentRepositoryExt extends StudentRepositoryImpl {
    public StudentRepositoryExt() {
        super(StudentExt.class);
    }
}

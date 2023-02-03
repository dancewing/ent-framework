/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.repository;

import io.entframework.kernel.db.mds.example.ext.entity.ClassGradeExt;
import io.entframework.kernel.db.mds.example.repository.impl.ClassGradeRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class ClassGradeRepositoryExt extends ClassGradeRepositoryImpl {

    public ClassGradeRepositoryExt() {
        super(ClassGradeExt.class);
    }
}

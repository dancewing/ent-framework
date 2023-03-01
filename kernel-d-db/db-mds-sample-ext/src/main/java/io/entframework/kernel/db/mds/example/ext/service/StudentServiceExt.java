/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.service;

import io.entframework.kernel.db.mds.example.ext.entity.StudentExt;
import io.entframework.kernel.db.mds.example.ext.pojo.StudentRequestExt;
import io.entframework.kernel.db.mds.example.ext.pojo.StudentResponseExt;
import io.entframework.kernel.db.mds.example.service.impl.StudentServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceExt extends StudentServiceImpl {

	public StudentServiceExt() {
		super(StudentRequestExt.class, StudentResponseExt.class, StudentExt.class);
	}

}

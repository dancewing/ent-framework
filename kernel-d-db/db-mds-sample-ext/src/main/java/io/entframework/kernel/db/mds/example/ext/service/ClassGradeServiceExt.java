/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.service;

import io.entframework.kernel.db.mds.example.ext.entity.ClassGradeExt;
import io.entframework.kernel.db.mds.example.ext.pojo.ClassGradeRequestExt;
import io.entframework.kernel.db.mds.example.ext.pojo.ClassGradeResponseExt;
import io.entframework.kernel.db.mds.example.service.impl.ClassGradeServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ClassGradeServiceExt extends ClassGradeServiceImpl {

	public ClassGradeServiceExt() {
		super(ClassGradeRequestExt.class, ClassGradeResponseExt.class, ClassGradeExt.class);
	}

}

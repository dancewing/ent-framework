/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.mapper;


import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.example.ext.entity.ClassGradeExt;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(ClassGradeExt.class)
public interface ClassGradeExtMapper extends BaseMapper<ClassGradeExt> {
}

/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.entity;

import io.entframework.kernel.db.mds.example.entity.Student;
import org.mybatis.dynamic.sql.annotation.Column;

import java.sql.JDBCType;

public class StudentExt extends Student {

    @Column(name = "child_test", jdbcType = JDBCType.VARCHAR)
    private String childTest;

    public String getChildTest() {
        return childTest;
    }

    public void setChildTest(String childTest) {
        this.childTest = childTest;
    }
}

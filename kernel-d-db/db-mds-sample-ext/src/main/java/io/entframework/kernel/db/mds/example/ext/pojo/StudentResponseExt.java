/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.pojo;

import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;

public class StudentResponseExt extends StudentResponse {
    private String childTest;

    public String getChildTest() {
        return childTest;
    }

    public void setChildTest(String childTest) {
        this.childTest = childTest;
    }
}

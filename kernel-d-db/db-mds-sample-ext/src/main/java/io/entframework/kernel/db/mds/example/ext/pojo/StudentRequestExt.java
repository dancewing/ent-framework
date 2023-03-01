/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.ext.pojo;

import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;

public class StudentRequestExt extends StudentRequest {

    private String childTest;

    public String getChildTest() {
        return childTest;
    }

    public void setChildTest(String childTest) {
        this.childTest = childTest;
    }

}

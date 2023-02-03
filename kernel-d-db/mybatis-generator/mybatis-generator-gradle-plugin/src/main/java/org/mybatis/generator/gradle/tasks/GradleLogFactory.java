/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.tasks;

import org.gradle.api.Project;
import org.mybatis.generator.logging.AbstractLogFactory;
import org.mybatis.generator.logging.Log;

public class GradleLogFactory implements AbstractLogFactory {

    private GradleLogImpl log;

    public GradleLogFactory(Project project) {
        log = new GradleLogImpl(project.getLogger());
    }

    @Override
    public Log getLog(Class<?> targetClass) {
        return log;
    }
}

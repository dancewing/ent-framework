/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package org.mybatis.generator.gradle.tasks;

import org.mybatis.generator.api.ProgressCallback;

/**
 * This callback logs progress messages with the Maven logger.
 *
 * @author Jeff Butler
 */
public class GradleProgressCallback implements ProgressCallback {

    private final org.gradle.api.logging.Logger log;
    private final boolean verbose;

    public GradleProgressCallback(org.gradle.api.logging.Logger log, boolean verbose) {
        super();
        this.log = log;
        this.verbose = verbose;
    }

    @Override
    public void startTask(String subTaskName) {
        if (verbose) {
            log.info(subTaskName);
        }
    }
}

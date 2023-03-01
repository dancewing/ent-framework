/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.tasks;

import org.gradle.api.logging.Logger;

public class GradleLogImpl implements org.mybatis.generator.logging.Log {

	private final org.gradle.api.logging.Logger logger;

	public GradleLogImpl(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	@Override
	public void error(String s, Throwable e) {
		this.logger.error(s, e);
	}

	@Override
	public void error(String s) {
		this.logger.error(s);
	}

	@Override
	public void debug(String s) {
		this.logger.debug(s);
	}

	@Override
	public void warn(String s) {
		this.logger.warn(s);
	}

}

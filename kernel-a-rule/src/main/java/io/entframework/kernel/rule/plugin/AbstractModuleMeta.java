/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.rule.plugin;

/**
 * @author jeff_qian
 */
public abstract class AbstractModuleMeta implements ModuleMeta {

	private final String name;

	private final String flywayTableSuffix;

	private final String flywayLocations;

	private String version;

	protected AbstractModuleMeta(String name, String flywayTableSuffix, String flywayLocations) {
		this.name = name;
		this.flywayTableSuffix = flywayTableSuffix;
		this.flywayLocations = flywayLocations;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFlywayTableSuffix() {
		return flywayTableSuffix;
	}

	@Override
	public String getFlywayLocations() {
		return flywayLocations;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getVersion() {
		return version;
	}

}

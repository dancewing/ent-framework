/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.dsl;

import org.gradle.api.Project;

import java.util.Properties;

public class MybatisGeneratorExtension {

	private final Project project;

	private Boolean enabled = true;

	private Boolean verbose = false;

	private Boolean overwrite = false;

	private String configFile;

	private String tableNames;

	private String contexts;

	private String outputDirectory;

	private Properties properties;

	public MybatisGeneratorExtension(Project project) {
		this.project = project;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(Boolean verbose) {
		this.verbose = verbose;
	}

	public Boolean getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(Boolean overwrite) {
		this.overwrite = overwrite;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getTableNames() {
		return tableNames;
	}

	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}

	public String getContexts() {
		return contexts;
	}

	public void setContexts(String contexts) {
		this.contexts = contexts;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "MybatisGeneratorExtension{" + "enabled=" + enabled + ", verbose=" + verbose + ", overwrite=" + overwrite
				+ ", configFile='" + configFile + '\'' + ", tableNames='" + tableNames + '\'' + ", contexts='"
				+ contexts + '\'' + ", outputDirectory='" + outputDirectory + '\'' + '}';
	}

}

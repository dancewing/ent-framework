/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.mybatis.generator.gradle.tasks.GeneratorJavaExec;

public class MybatisGeneratorAction implements PluginApplicationAction {

	@Override
	public Class<? extends Plugin<? extends Project>> getPluginClass()
			throws ClassNotFoundException, NoClassDefFoundError {
		return JavaPlugin.class;
	}

	@Override
	public void execute(Project project) {
		configureGeneratorTask(project);
	}

	private void configureGeneratorTask(Project project) {

		project.getTasks().register("mybatisGenerator", GeneratorJavaExec.class, (run) -> {
			run.setDescription("Runs mybatis generator in this project.");
			run.setGroup("mybatis");
		});
	}

}

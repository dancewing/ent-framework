/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package org.mybatis.generator.gradle.tasks;

import org.gradle.api.internal.ConventionTask;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.gradle.dsl.MybatisGeneratorExtension;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.messages.Messages;

import java.io.File;
import java.util.StringTokenizer;

/**
 * Shell callback that calculates the Maven output directory.
 *
 * @author Jeff Butler
 */
public class GradleShellCallback extends DefaultShellCallback {

	private ConventionTask conventionTask;

	public GradleShellCallback(ConventionTask conventionTask, boolean overwrite) {
		super(overwrite);
		this.conventionTask = conventionTask;
	}

	@Override
	public File getDirectory(String targetProject, String targetPackage) throws ShellException {
		MybatisGeneratorExtension extension = conventionTask.getProject().getExtensions()
				.getByType(MybatisGeneratorExtension.class);
		File project = new File(extension.getOutputDirectory());
		if (!project.exists()) {
			project.mkdirs();
		}

		if (!project.isDirectory()) {
			throw new ShellException(Messages.getString("Warning.9", //$NON-NLS-1$
					project.getAbsolutePath()));
		}

		if (!"MAVEN".equals(targetProject)) {
			return super.getDirectory(new File(project, targetProject).getAbsolutePath(), targetPackage);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("src/main/java/");
		StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());
			sb.append(File.separatorChar);
		}

		File directory = new File(project, sb.toString());
		if (!directory.isDirectory()) {
			boolean rc = directory.mkdirs();
			if (!rc) {
				throw new ShellException(Messages.getString("Warning.10", //$NON-NLS-1$
						directory.getAbsolutePath()));
			}
		}

		return directory;
	}

}

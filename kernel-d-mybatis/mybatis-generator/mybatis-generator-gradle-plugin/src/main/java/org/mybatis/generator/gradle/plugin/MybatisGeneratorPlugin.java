/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.plugin;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.util.GradleVersion;
import org.mybatis.generator.gradle.dsl.MybatisGeneratorExtension;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MybatisGeneratorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getLogger().info("Configuring Mybatis Generator for project: {}", project.getName());
        verifyGradleVersion();
        createExtension(project);
        createConfiguration(project);
        registerPluginActions(project);
    }

    private void verifyGradleVersion() {
        GradleVersion currentVersion = GradleVersion.current();
        if (currentVersion.compareTo(GradleVersion.version("7.4")) < 0) {
            throw new GradleException("Spring Boot plugin requires Gradle 7.x (7.4 or later). "
                    + "The current version is " + currentVersion);
        }
    }

    private void createExtension(Project project) {
        project.getExtensions().create("mybatisGenerator", MybatisGeneratorExtension.class, project);
    }

    private void createConfiguration(Project project) {
        project.getConfigurations().create("generator", c -> {
            c.setVisible(false);
            c.setCanBeConsumed(false);
            c.setCanBeResolved(true);
            c.setDescription("The data artifacts to be processed for this plugin.");
        });
    }
    
    private void registerPluginActions(Project project) {
        List<PluginApplicationAction> actions = Arrays.asList(new MybatisGeneratorAction());
        for (PluginApplicationAction action : actions) {
            withPluginClassOfAction(action,
                    (pluginClass) -> project.getPlugins().withType(pluginClass, (plugin) -> action.execute(project)));
        }
    }

    private void withPluginClassOfAction(PluginApplicationAction action,
                                         Consumer<Class<? extends Plugin<? extends Project>>> consumer) {
        Class<? extends Plugin<? extends Project>> pluginClass;
        try {
            pluginClass = action.getPluginClass();
        } catch (Throwable ex) {
            // Plugin class unavailable.
            return;
        }
        consumer.accept(pluginClass);
    }
}

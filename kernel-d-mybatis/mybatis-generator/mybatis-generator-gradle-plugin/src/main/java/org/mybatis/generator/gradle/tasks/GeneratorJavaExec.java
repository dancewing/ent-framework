/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.gradle.tasks;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.gradle.dsl.MybatisGeneratorExtension;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.ClassloaderUtility;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@DisableCachingByDefault(because = "Application should always run")
public class GeneratorJavaExec extends ConventionTask {

    private final ThreadLocal<ClassLoader> savedClassloader = new ThreadLocal<>();

    @TaskAction
    public void exec() {
        Project project = getProject();
        MybatisGeneratorExtension extension = project.getExtensions().getByType(MybatisGeneratorExtension.class);
        if (!StringUtility.stringHasValue(extension.getConfigFile())) {
            throw new GradleException("mybatisGenerator config doesn't exist.");
        }
        String configFileLocation = extension.getConfigFile();
        File configFile = new File(configFileLocation);
        if (!configFile.exists() || !configFile.isFile()) {
            throw new GradleException("Generator XML config file doesn't exist.");
        }

        if (project.getLogger().isDebugEnabled()) {
            project.getLogger().debug(extension.toString());
        }

        System.setProperty("javax.xml.accessExternalDTD", "all");

        saveClassLoader();

        LogFactory.setLogFactory(new GradleLogFactory(project));

        calculateClassPath(project);

        // add resource directories to the classpath.  This is required to support
        // use of a properties file in the build.  Typically, the properties file
        // is in the project's source tree, but the plugin classpath does not
        // include the project classpath.
//        List<Resource> resources = project.getResources();
        List<String> resourceDirectories = new ArrayList<>();
//        for (Resource resource : resources) {
//            resourceDirectories.add(resource.getDirectory());
//        }
        ClassLoader cl = ClassloaderUtility.getCustomClassloader(resourceDirectories);
        ObjectFactory.addExternalClassLoader(cl);

        List<String> warnings = new ArrayList<>();


        Set<String> fullyqualifiedTables = new HashSet<>();
        if (StringUtility.stringHasValue(extension.getTableNames())) {
            StringTokenizer st = new StringTokenizer(extension.getTableNames(), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }

        Set<String> contextsToRun = new HashSet<>();
        if (StringUtility.stringHasValue(extension.getContexts())) {
            StringTokenizer st = new StringTokenizer(extension.getContexts(), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contextsToRun.add(s);
                }
            }
        }

        try {
            ConfigurationParser cp = new ConfigurationParser(convertMapToProperty(project.getProperties(), extension), warnings);
            Configuration config = cp.parseConfiguration(configFile);

            ShellCallback callback = new GradleShellCallback(this, extension.getOverwrite());

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    callback, warnings);

            myBatisGenerator.generate(new GradleProgressCallback(project.getLogger(), extension.getVerbose()), contextsToRun, fullyqualifiedTables);

        } catch (XMLParserException | InvalidConfigurationException e) {
            for (String error : e.getErrors()) {
                project.getLogger().error(error);
            }
            throw new GradleException(e.getMessage());
        } catch (SQLException | IOException e) {
            throw new GradleException(e.getMessage());
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
        }

        for (String error : warnings) {
            project.getLogger().warn(error);
        }

        restoreClassLoader();
    }

    private void calculateClassPath(Project project) {
        org.gradle.api.artifacts.Configuration configuration = project.getConfigurations().getByName("generator");
        try {
            DependencySet dependencies = configuration.getDependencies();
            // add the project compile classpath to the plugin classpath,
            // so that the project dependency classes can be found
            // directly, without adding the classpath to configuration's classPathEntries
            // repeatedly.Examples are JDBC drivers, root classes, root interfaces, etc.
            Set<String> entries = new HashSet<>();

            dependencies.forEach(dependency -> {
                Set<File> jarLocations = configuration.files(dependency);
                jarLocations.forEach(file -> {
                    entries.add(file.getAbsolutePath());
                });
            });

            // remove the output directories (target/classes and target/test-classes)
            // because this mojo runs in the generate-sources phase and
            // those directories have not been created yet (typically)
//            entries.remove(project.getBuild().getOutputDirectory());
//            entries.remove(project.getBuild().getTestOutputDirectory());

            ClassLoader contextClassLoader = ClassloaderUtility.getCustomClassloader(entries);
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (Exception e) {
            throw new GradleException("Dependency Resolution Required", e);
        }
    }

    private Properties convertMapToProperty(Map<String, ?> properties, MybatisGeneratorExtension extension) {
        if (properties == null) {
            return new Properties();
        }
        Properties props = new Properties();
        properties.forEach((key, value) -> {
            if (key != null && value != null) {
                props.put(key, value);
            }
        });
        if (extension.getProperties() != null) {
            props.putAll(extension.getProperties());
        }
        return props;
    }

    private void saveClassLoader() {
        savedClassloader.set(Thread.currentThread().getContextClassLoader());
    }

    private void restoreClassLoader() {
        Thread.currentThread().setContextClassLoader(savedClassloader.get());
        savedClassloader.remove();
    }
}

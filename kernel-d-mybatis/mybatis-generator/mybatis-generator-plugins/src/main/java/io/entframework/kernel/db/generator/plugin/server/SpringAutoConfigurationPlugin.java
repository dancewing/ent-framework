/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

public class SpringAutoConfigurationPlugin extends AbstractServerPlugin {

	private String springConfigJavaFile;

	@Override
	public boolean validate(List<String> warnings) {
		boolean validate = super.validate(warnings);

		this.springConfigJavaFile = this.properties.getProperty("springConfigJavaFile");

		if (StringUtils.isEmpty(springConfigJavaFile)) {
			warnings.add("请检查SpringAutoConfigurationPlugin配置");
			return false;
		}

		return validate;
	}

	private GeneratedJavaFile generateServiceConfig() {

		TopLevelClass configBaseClass = new TopLevelClass(this.springConfigJavaFile);
		configBaseClass.setVisibility(JavaVisibility.PUBLIC);
		configBaseClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);

		List<IntrospectedTable> tables = this.context.getIntrospectedTables();
		for (IntrospectedTable introspectedTable : tables) {
			String baseRecordType = introspectedTable.getBaseRecordType();
			FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(baseRecordType);
			FullyQualifiedJavaType baseServiceJavaType = getServiceJavaType(recordType.getShortName());
			FullyQualifiedJavaType baseServiceImplJavaType = getServiceImplJavaType(recordType.getShortName());

			Method serviceMethod = new Method(StringUtils.uncapitalize(baseServiceJavaType.getShortName()));

			serviceMethod.addBodyLine(String.format("return new %s();", baseServiceImplJavaType.getShortName()));
			serviceMethod.addAnnotation("@Bean");
			serviceMethod.setVisibility(JavaVisibility.PUBLIC);
			serviceMethod.setReturnType(baseServiceJavaType);
			serviceMethod.addAnnotation(
					String.format("@ConditionalOnMissingBean(%s.class)", baseServiceJavaType.getShortName()));
			configBaseClass.addMethod(serviceMethod);
			configBaseClass.addImportedType(baseServiceJavaType);
			configBaseClass.addImportedType(baseServiceImplJavaType);
		}
		configBaseClass.addImportedType("org.springframework.context.annotation.Bean");
		configBaseClass.addImportedType("org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean");

		FullyQualifiedJavaType baseServiceType = new FullyQualifiedJavaType(
				"org.springframework.context.annotation.Configuration");
		configBaseClass.addAnnotation("@Configuration");
		configBaseClass.addImportedType(baseServiceType);

		List<String> scanPackages = new ArrayList<>();
		scanPackages.add("\"" + this.controllerTargetPackage + "\"");
		scanPackages.add("\"" + this.mapstructTargetPackage + "\"");
		scanPackages.add("\"" + this.serviceTargetPackage + "\"");
		configBaseClass.addAnnotation(
				String.format("@ComponentScan(basePackages = {%s})", StringUtils.join(scanPackages, ", ")));

		configBaseClass.addAnnotation(
				String.format("@EntityScan(\"%s\")", context.getJavaModelGeneratorConfiguration().getTargetPackage()));
		configBaseClass.addImportedType("org.springframework.boot.autoconfigure.domain.EntityScan");
		configBaseClass.addImportedType("org.springframework.context.annotation.ComponentScan");

		return new GeneratedJavaFile(configBaseClass, context.getJavaModelGeneratorConfiguration().getTargetProject(),
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {

		generatedJavaFiles.add(generateServiceConfig());

		return generatedJavaFiles;
	}

	@Override
	public List<GeneratedFile> contextGenerateAdditionalFiles() {
		List<GeneratedFile> generatedFiles = new ArrayList<>();
		generatedFiles.add(generateFactoryFile());
		return generatedFiles;
	}

	private GeneratedFile generateFactoryFile() {
		GeneratedPlainFile factoryFile = new GeneratedPlainFile("src/main/resources",
				"org.springframework.boot.autoconfigure.AutoConfiguration.imports", "META-INF/spring");
		factoryFile.setMergeable(true);
		factoryFile.addBodyLine(this.springConfigJavaFile);
		return factoryFile;
	}

}

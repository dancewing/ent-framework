/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.FieldAndImports;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.generator.PojoFieldsGenerator;
import io.entframework.kernel.db.generator.utils.ClassInfo;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.List;

/***
 * Pojo生成
 */
public class PojoPlugin extends AbstractServerPlugin {

	@Override
	public boolean validate(List<String> warnings) {

		boolean validate = super.validate(warnings);

		if (StringUtils.isAnyEmpty(this.pojoRequestTargetPackage, this.pojoRequestSuffix,
				this.pojoResponseTargetPackage, this.pojoResponseSuffix, this.mapstructTargetPackage,
				this.mapstructSuffix)) {
			warnings.add("请检查PojoPlugin配置");
			return false;
		}

		return validate;
	}

	/***
	 * 在model产生后新增pojo request 和 pojo response 插件注册时要注意顺序，因为需要从TopLevelClass读取所有Field
	 * @param topLevelClass the generated base record class
	 * @param introspectedTable The class containing information about the table as
	 * introspected from the database
	 * @return
	 */
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType qualifiedJavaType = topLevelClass.getType();
		PojoFieldsGenerator pojoFieldsGenerator = new PojoFieldsGenerator(this.context, this.codingStyle,
				this.pojoRequestTargetPackage, this.pojoRequestSuffix, this.pojoResponseTargetPackage,
				this.pojoResponseSuffix, qualifiedJavaType);

		// 判断是否包含Entity父类
		String rootClass = this.context.getJavaModelGeneratorConfiguration().getProperty("rootClass");
		if (StringUtils.isNotEmpty(rootClass)) {
			ClassInfo classInfo = ClassInfo.getInstance(rootClass);
			TopLevelClass parentEntityClass = classInfo.toTopLevelClass();
			if (parentEntityClass != null) {
				topLevelClass.setAttribute(Constants.PARENT_ENTITY_CLASS, parentEntityClass);
			}
		}
		generatedJavaFiles.add(generatePojoRequest(topLevelClass, introspectedTable, pojoFieldsGenerator));
		generatedJavaFiles.add(generatePojoResponse(topLevelClass, introspectedTable, pojoFieldsGenerator));

		return true;
	}

	private GeneratedJavaFile generatePojoRequest(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
			PojoFieldsGenerator pojoFieldsGenerator) {
		String modelObjectName = topLevelClass.getType().getShortNameWithoutTypeArguments();

		FullyQualifiedJavaType pojoRequestJavaType = getPojoRequestJavaType(modelObjectName);
		TopLevelClass pojoRequestClass = new TopLevelClass(pojoRequestJavaType);
		pojoRequestClass.setVisibility(JavaVisibility.PUBLIC);
		if (StringUtils.isNotEmpty(this.pojoRequestRootClass)) {
			pojoRequestClass.setSuperClass(this.pojoRequestRootClass);
			pojoRequestClass.addImportedType(this.pojoRequestRootClass);

			ClassInfo classInfo = ClassInfo.getInstance(this.pojoRequestRootClass);
			TopLevelClass parentRequestClass = classInfo.toTopLevelClass();
			if (parentRequestClass != null) {
				pojoRequestClass.setAttribute(Constants.PARENT_REQUEST_CLASS, parentRequestClass);
			}
		}

		pojoRequestClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);

		GeneratorUtils.addComment(pojoRequestClass, topLevelClass.getDescription() + " 服务请求类");

		addLombokAnnotation(pojoRequestClass);

		FieldAndImports fieldAndImports = pojoFieldsGenerator.generatePojoRequest(topLevelClass, introspectedTable);

		fieldAndImports.getFields().forEach(pojoRequestClass::addField);
		fieldAndImports.getImports().forEach(pojoRequestClass::addImportedType);

		return new GeneratedJavaFile(pojoRequestClass, context.getJavaModelGeneratorConfiguration().getTargetProject(),
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
	}

	private GeneratedJavaFile generatePojoResponse(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
			PojoFieldsGenerator pojoFieldsGenerator) {
		String modelObjectName = topLevelClass.getType().getShortNameWithoutTypeArguments();

		FullyQualifiedJavaType pojoResponseJavaType = getPojoResponseJavaType(modelObjectName);
		TopLevelClass pojoResponseClass = new TopLevelClass(pojoResponseJavaType);
		pojoResponseClass.setVisibility(JavaVisibility.PUBLIC);
		if (StringUtils.isNotEmpty(this.pojoResponseRootClass)) {
			pojoResponseClass.setSuperClass(this.pojoResponseRootClass);
			pojoResponseClass.addImportedType(this.pojoResponseRootClass);
		}

		pojoResponseClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);
		GeneratorUtils.addComment(pojoResponseClass, topLevelClass.getDescription() + " 服务响应类");

		addLombokAnnotation(pojoResponseClass);

		FieldAndImports fieldAndImports = pojoFieldsGenerator.generatePojoResponse(topLevelClass, introspectedTable);

		fieldAndImports.getFields().forEach(pojoResponseClass::addField);
		fieldAndImports.getImports().forEach(pojoResponseClass::addImportedType);

		return new GeneratedJavaFile(pojoResponseClass, context.getJavaModelGeneratorConfiguration().getTargetProject(),
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());

	}

	private void addLombokAnnotation(TopLevelClass topLevelClass) {
		topLevelClass.addImportedType(LombokAnnotation.DATA.getJavaType());
		topLevelClass.addAnnotation(LombokAnnotation.DATA.getName());
		topLevelClass.addImportedType(LombokAnnotation.EqualsAndHashCode.getJavaType());
		topLevelClass.addAnnotation(LombokAnnotation.EqualsAndHashCode.getName());
		topLevelClass.addImportedType(LombokAnnotation.ALL_ARGS_CONSTRUCTOR.getJavaType());
		topLevelClass.addAnnotation(LombokAnnotation.ALL_ARGS_CONSTRUCTOR.getName());
		topLevelClass.addImportedType(LombokAnnotation.NO_ARGS_CONSTRUCTOR.getJavaType());
		topLevelClass.addAnnotation(LombokAnnotation.NO_ARGS_CONSTRUCTOR.getName());
	}

	/**
	 * @return
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		return generatedJavaFiles;
	}

}

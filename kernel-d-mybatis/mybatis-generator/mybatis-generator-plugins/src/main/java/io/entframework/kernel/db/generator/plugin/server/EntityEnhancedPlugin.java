/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * 生成的java model 添加lombok 注解
 */
public class EntityEnhancedPlugin extends PluginAdapter {

    private final Collection<LombokAnnotation> annotations;

    public EntityEnhancedPlugin() {
        annotations = new TreeSet<>();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * Intercepts base record class generation
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass, true);

        addFieldEnhancedMethod(topLevelClass);
        return true;
    }

    private void addFieldEnhancedMethod(TopLevelClass topLevelClass) {
        List<Field> fieldList = topLevelClass.getFields();
        fieldList.forEach(field -> {
            Method method = new Method(field.getName());
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(topLevelClass.getType());
            Parameter parameter = new Parameter(field.getType(), field.getName());
            method.addParameter(parameter);
            method.addBodyLine(String.format("this.%s = %s;", field.getName(), field.getName()));
            method.addBodyLine("return this;");
            topLevelClass.addMethod(method);
        });
    }

    /**
     * Intercepts primary key class generation
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass, false);
        return true;
    }

    /**
     * Intercepts "record with blob" class generation
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass, true);
        return true;
    }

    /**
     * Prevents all getters from being generated.
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * Prevents all setters from being generated
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {
        // add types
        return true;
    }

    /**
     * Adds the lombok annotations' imports and annotations to the class
     */
    private void addDataAnnotation(TopLevelClass topLevelClass, boolean builder) {
        for (LombokAnnotation annotation : annotations) {
            if (!builder && "builder".equals(annotation.getParamName())) {
                continue;
            }
            topLevelClass.addImportedType(annotation.getJavaType());
            topLevelClass.addAnnotation(annotation.getName());
        }
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        // 要添加到类上的注解
        annotations.add(LombokAnnotation.DATA);
        if (StringUtils.isNotEmpty(context.getJavaModelGeneratorConfiguration().getProperty("rootClass"))) {
            annotations.add(LombokAnnotation.EqualsAndHashCode);
        }
        annotations.add(LombokAnnotation.NO_ARGS_CONSTRUCTOR);
        annotations.add(LombokAnnotation.ALL_ARGS_CONSTRUCTOR);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            boolean isEnable = Boolean.parseBoolean(entry.getValue().toString());
            if (isEnable) {
                String paramName = entry.getKey().toString().trim();
                LombokAnnotation annotation = LombokAnnotation.getValueOf(paramName);
                if (annotation != null) {
                    annotations.add(annotation);
                    annotations.addAll(LombokAnnotation.getDependencies(annotation));
                }
            }
        }
    }
}

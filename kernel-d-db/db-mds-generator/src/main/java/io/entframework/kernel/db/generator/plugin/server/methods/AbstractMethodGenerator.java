/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server.methods;

import io.entframework.kernel.db.generator.utils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.util.Optional;

import static io.entframework.kernel.db.generator.Constants.*;


public abstract class AbstractMethodGenerator {
    protected final Context context;
    protected final IntrospectedTable introspectedTable;
    protected final AbstractJavaType hostJavaClass;

    protected final Interface clientInterface;
    protected final String tableFieldName;
    protected final FullyQualifiedJavaType recordType;
    protected final boolean isAbstract;
    protected final String pojoRequestTargetPackage;
    protected final String pojoRequestSuffix;
    protected final String pojoResponseTargetPackage;
    protected final String pojoResponseSuffix;
    protected final String repositoryTargetPackage;
    protected final String repositorySuffix;
    protected final String mapstructTargetPackage;
    protected final String mapstructSuffix;

    protected AbstractMethodGenerator(BuildConfig config) {
        context = config.context;
        introspectedTable = config.introspectedTable;
        hostJavaClass = config.hostJavaClass;
        clientInterface = config.clientInterface;
        tableFieldName = config.tableFieldName;
        recordType = config.recordType;
        isAbstract = config.isAbstract;
        this.pojoRequestTargetPackage = this.context.getProperty("pojoRequestTargetPackage");
        this.pojoRequestSuffix = PropertyUtils.getProperty(context, "pojoRequestSuffix", DEFAULT_POJO_REQUEST_SUFFIX);

        this.pojoResponseTargetPackage = this.context.getProperty("pojoResponseTargetPackage");
        this.pojoResponseSuffix = PropertyUtils.getProperty(context, "pojoResponseSuffix", DEFAULT_POJO_RESPONSE_SUFFIX);

        this.repositoryTargetPackage = this.context.getProperty("repositoryTargetPackage");
        this.repositorySuffix = PropertyUtils.getProperty(context, "repositorySuffix", DEFAULT_REPOSITORY_SUFFIX);

        this.mapstructTargetPackage = this.context.getProperty("mapstructTargetPackage");
        this.mapstructSuffix = PropertyUtils.getProperty(context, "mapstructSuffix", DEFAULT_MAPSTRUCT_SUFFIX);

    }

    protected String calculateFieldName(IntrospectedColumn column) {
        return calculateFieldName(tableFieldName, column);
    }

    public static String calculateFieldName(String tableFieldName, IntrospectedColumn column) {
        String fieldName = column.getJavaProperty();
        if (fieldName.equals(tableFieldName)) {
            // name collision, no shortcut generated
            fieldName = tableFieldName + "." + fieldName; //$NON-NLS-1$
        }
        return fieldName;
    }

    protected void acceptParts(MethodAndImports.Builder builder, Method method, MethodParts methodParts) {
        for (Parameter parameter : methodParts.getParameters()) {
            method.addParameter(parameter);
        }

        for (String annotation : methodParts.getAnnotations()) {
            method.addAnnotation(annotation);
        }

        method.addBodyLines(methodParts.getBodyLines());
        builder.withImports(methodParts.getImports());
    }

    public FullyQualifiedJavaType getPojoRequestJavaType() {
        return new FullyQualifiedJavaType(this.pojoRequestTargetPackage + "." + this.recordType.getShortName() + this.pojoRequestSuffix);
    }

    public FullyQualifiedJavaType getMapperSupportJavaType() {
        return new FullyQualifiedJavaType(this.context.getJavaClientGeneratorConfiguration().getTargetPackage() + "." + this.recordType.getShortName() + "DynamicSqlSupport");
    }

    public FullyQualifiedJavaType getMapperSupportJavaType(String shortName) {
        return new FullyQualifiedJavaType(this.context.getJavaClientGeneratorConfiguration().getTargetPackage() + "." + shortName + "DynamicSqlSupport");
    }

    public FullyQualifiedJavaType getPojoResponseJavaType() {
        return new FullyQualifiedJavaType(this.pojoResponseTargetPackage + "." + this.recordType.getShortName() + this.pojoResponseSuffix);
    }

    public FullyQualifiedJavaType getMapperJavaType() {
        return new FullyQualifiedJavaType(this.context.getJavaClientGeneratorConfiguration().getTargetPackage() + "." + this.recordType.getShortName() + "Mapper");
    }

    public FullyQualifiedJavaType getMapperJavaType(String shortName) {
        return new FullyQualifiedJavaType(this.context.getJavaClientGeneratorConfiguration().getTargetPackage() + "." + shortName + "Mapper");
    }

    public FullyQualifiedJavaType getRepositoryJavaType() {
        return new FullyQualifiedJavaType(this.repositoryTargetPackage + "." + this.recordType.getShortName() + this.repositorySuffix);
    }

    public FullyQualifiedJavaType getRepositoryJavaType(String shortName) {
        return new FullyQualifiedJavaType(this.repositoryTargetPackage + "." + shortName + this.repositorySuffix);
    }

    public FullyQualifiedJavaType getMapstructJavaType() {
        return new FullyQualifiedJavaType(this.mapstructTargetPackage + "." + this.recordType.getShortName() + this.mapstructSuffix);
    }

    public Field findMapperField(TopLevelClass topLevelClass, String fieldName, FullyQualifiedJavaType javaType) {
        Optional<Field> foundField = topLevelClass.getFields().stream().filter(field -> StringUtils.equals(fieldName, field.getName())).findFirst();
        if (foundField.isPresent()) {
            return foundField.get();
        } else {
            Field created = new Field(fieldName, javaType);
            created.setVisibility(JavaVisibility.PROTECTED);
            created.addAnnotation("@Resource");
            topLevelClass.addImportedType("jakarta.annotation.Resource");
            topLevelClass.addField(created);
            topLevelClass.addImportedType(javaType);
            return created;
        }
    }

    public abstract MethodAndImports generateMethodAndImports();

    public static class BuildConfig {
        private Context context;
        private IntrospectedTable introspectedTable;
        private AbstractJavaType hostJavaClass;
        private Interface clientInterface;
        private String tableFieldName;
        private FullyQualifiedJavaType recordType;
        private boolean isAbstract;

        public BuildConfig withContext(Context context) {
            this.context = context;
            return getThis();
        }

        public BuildConfig withIntrospectedTable(IntrospectedTable introspectedTable) {
            this.introspectedTable = introspectedTable;
            return getThis();
        }

        public BuildConfig withHostJavaClass(AbstractJavaType javaType) {
            this.hostJavaClass = javaType;
            return getThis();
        }

        public BuildConfig withClientInterface(Interface clientInterface) {
            this.clientInterface = clientInterface;
            return getThis();
        }

        public BuildConfig withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return getThis();
        }

        public BuildConfig withTableFieldName(String tableFieldName) {
            this.tableFieldName = tableFieldName;
            return getThis();
        }

        public BuildConfig withAbstract(boolean isAbstract) {
            this.isAbstract = isAbstract;
            return getThis();
        }

        public BuildConfig getThis() {
            return this;
        }
    }
}

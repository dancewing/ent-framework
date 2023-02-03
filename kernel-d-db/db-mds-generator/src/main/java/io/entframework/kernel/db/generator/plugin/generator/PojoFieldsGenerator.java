/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.generator;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.rule.util.Inflection;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.util.List;

public class PojoFieldsGenerator {

    private final Context context;
    private final String codingStyle;
    private final String pojoRequestTargetPackage;
    private String pojoRequestSuffix = "";
    private final String pojoResponseTargetPackage;
    private String pojoResponseSuffix = "";

    private final FullyQualifiedJavaType factory;

    public PojoFieldsGenerator(Context context, String codingStyle,
                               String pojoRequestTargetPackage, String pojoRequestSuffix,
                               String pojoResponseTargetPackage, String pojoResponseSuffix,
                               FullyQualifiedJavaType factory) {
        this.context = context;
        this.codingStyle = codingStyle;
        this.pojoRequestTargetPackage = pojoRequestTargetPackage;
        this.pojoRequestSuffix = pojoRequestSuffix;
        this.pojoResponseTargetPackage = pojoResponseTargetPackage;
        this.pojoResponseSuffix = pojoResponseSuffix;
        this.factory = factory;
    }

    public FieldAndImports generatePojoRequest(TopLevelClass modelClass, IntrospectedTable introspectedTable){
        String modelObjectName = modelClass.getType().getShortNameWithoutTypeArguments();
        FieldAndImports.Builder builder = new FieldAndImports.Builder();

        List<Field> fields = modelClass.getFields();
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName()) || GeneratorUtils.isLogicDeleteField(field)) {
                continue;
            }
            Field pojoRequestField = new Field(field);
            //清除源字段的Annotation
            pojoRequestField.getAnnotations().clear();
            FullyQualifiedJavaType fieldType = field.getType();
            if (GeneratorUtils.isRelationField(introspectedTable, field)) {
                fieldType = getActualJavaType(field, true);
            }
            InnerEnum innerEnum = (InnerEnum) field.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR);
            if (innerEnum != null) {
                FullyQualifiedJavaType modelJavaType = GeneratorUtils.getModelJavaType(context, modelObjectName, factory);
                fieldType = factory.create(modelJavaType.getFullyQualifiedName() + "." + field.getType());
                builder.withImport(fieldType);
            }
            pojoRequestField.setType(fieldType);
            pojoRequestField.setVisibility(JavaVisibility.PRIVATE);
            if (field.getAttribute(Constants.FIELD_LOGIC_DELETE_ATTR) != null) {
                pojoRequestField.setAttribute(Constants.FIELD_LOGIC_DELETE_ATTR, field.getAttribute(Constants.FIELD_LOGIC_DELETE_ATTR));
            }
            String fieldDescription = field.getDescription();
            //如果是关联关系
            if (GeneratorUtils.isRelationField(introspectedTable, field)) {
                builder.withImport(fieldType);
                pojoRequestField.setAttribute(Constants.FIELD_RELATION, field.getAttribute(Constants.FIELD_RELATION));
            } else {
                builder.withImport(fieldType);
                IntrospectedColumn column = GeneratorUtils.getIntrospectedColumnByJavaProperty(introspectedTable, field.getName());

                //增加通用的注解
                if (GeneratorUtils.isPrimaryKey(introspectedTable, column)) {
                    if (column.isNumberColumn()) {
                        pojoRequestField.addAnnotation(String.format("@NotNull(message = \"%s不能为空\", groups = {update.class, delete.class, detail.class, updateStatus.class})", fieldDescription));
                        builder.withImport("jakarta.validation.constraints.NotNull");
                    } else if (column.isStringColumn()) {
                        pojoRequestField.addAnnotation(String.format("@NotBlank(message = \"%s不能为空\", groups = {update.class, delete.class, detail.class, updateStatus.class})", fieldDescription));
                        builder.withImport("jakarta.validation.constraints.NotBlank");
                    }
                } else if (!column.isNullable()) {
                    if (column.isStringColumn()) {
                        pojoRequestField.addAnnotation(String.format("@NotBlank(message = \"%s不能为空\", groups = {add.class, update.class})", fieldDescription));
                        builder.withImport("jakarta.validation.constraints.NotBlank");
                    }
                }
            }
            if (codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
                pojoRequestField.addAnnotation(String.format("@ChineseDescription(\"%s\")", fieldDescription));
                builder.withImport("io.entframework.kernel.rule.annotation.ChineseDescription");
            }

            builder.withField(pojoRequestField);
        }
        //追加支持batch delete的复数字段
        IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
        String pluralizeIds = Inflection.pluralize(pk.getJavaProperty());
        FullyQualifiedJavaType fqjt = FullyQualifiedJavaType.getNewListInstance();
        fqjt.addTypeArgument(pk.getFullyQualifiedJavaType());
        Field pluralizeField = new Field(pluralizeIds, fqjt);
        pluralizeField.setVisibility(JavaVisibility.PRIVATE);
        pluralizeField.addAnnotation("@NotNull(message = \"ID集合不能为空\", groups = {batchDelete.class})");
        if (codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
            pluralizeField.addAnnotation(String.format("@ChineseDescription(\"%s\")", "ID集合"));
            builder.withImport("io.entframework.kernel.rule.annotation.ChineseDescription");
        }
        pluralizeField.setDescription("ID集合");
        pluralizeField.setAttribute(Constants.FIELD_EXT_ATTR, true);

        builder.withField(pluralizeField);

        return builder.build();
    }

    public FieldAndImports generatePojoResponse(TopLevelClass modelClass, IntrospectedTable introspectedTable){
        String modelObjectName = modelClass.getType().getShortNameWithoutTypeArguments();
        FieldAndImports.Builder builder = new FieldAndImports.Builder();

        List<Field> fields = modelClass.getFields();

        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName()) || GeneratorUtils.isLogicDeleteField(field)) {
                continue;
            }
            Field pojoResponseField = new Field(field);
            //清除源字段的Annotation
            pojoResponseField.getAnnotations().clear();
            FullyQualifiedJavaType fieldType = field.getType();
            if (GeneratorUtils.isRelationField(introspectedTable, field)) {
                fieldType = getActualJavaType(field, false);
            }
            InnerEnum innerEnum = (InnerEnum) field.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR);
            if (innerEnum != null) {
                FullyQualifiedJavaType modelJavaType = GeneratorUtils.getModelJavaType(context, modelObjectName, factory);
                fieldType = factory.create(modelJavaType.getFullyQualifiedName() + "." + field.getType());
                builder.withImport(fieldType);
            }
            pojoResponseField.setType(fieldType);
            pojoResponseField.setVisibility(JavaVisibility.PRIVATE);
            //如果是关联关系
            if (GeneratorUtils.isRelationField(introspectedTable, field)) {
                builder.withImport(fieldType);
            } else {
                builder.withImport(field.getType());
            }
            String fieldDescription = field.getDescription();
            if (codingStyle.equals(Constants.GENERATED_CODE_STYLE)) {
                pojoResponseField.addAnnotation(String.format("@ChineseDescription(\"%s\")", fieldDescription));
                builder.withImport("io.entframework.kernel.rule.annotation.ChineseDescription");
            }
            builder.withField(pojoResponseField);
        }

        return builder.build();
    }

    //获取字段的实际类型
    private FullyQualifiedJavaType getActualJavaType(Field field, boolean isRequest) {
        String fieldTypeName = field.getType().getFullyQualifiedNameWithoutTypeParameters();
        if ("java.util.List".equals(fieldTypeName)) {
            FullyQualifiedJavaType fullyQualifiedJavaType = FullyQualifiedJavaType.getNewListInstance();
            List<FullyQualifiedJavaType> typeArgs = field.getType().getTypeArguments();
            if (typeArgs != null) {
                for (FullyQualifiedJavaType type : typeArgs) {
                    fullyQualifiedJavaType.addTypeArgument(isRequest ? getPojoRequestJavaType(type.getShortName()) :
                            getPojoResponseJavaType(type.getShortName()));
                }
            }
            return fullyQualifiedJavaType;
        } else {
            return isRequest ? getPojoRequestJavaType(field.getType().getShortName()) : getPojoResponseJavaType(field.getType().getShortName());
        }
    }

    public FullyQualifiedJavaType getPojoRequestJavaType(String modelObjectName) {
        return factory.create(this.pojoRequestTargetPackage + "." + modelObjectName + this.pojoRequestSuffix);
    }

    public FullyQualifiedJavaType getPojoResponseJavaType(String modelObjectName) {
        return factory.create(this.pojoResponseTargetPackage + "." + modelObjectName + this.pojoResponseSuffix);
    }
}

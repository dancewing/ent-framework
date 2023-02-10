/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.config.Relation;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JoinEntry;
import org.mybatis.generator.config.JoinTarget;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;

import static org.mybatis.generator.internal.util.JavaBeansUtil.*;

public class MyBatisExtPlugin extends AbstractDynamicSQLPlugin {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        GeneratorUtils.addFieldComment(field, introspectedColumn);
        String fieldDescription = GeneratorUtils.getFieldDescription(introspectedColumn);
        field.setDescription(fieldDescription);

        if (GeneratorUtils.isPrimaryKey(introspectedTable, introspectedColumn)) {
            field.addAnnotation("@Id");
            topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.Id");
        }

        introspectedTable.getGeneratedKey().ifPresent(generatedKey -> {
            if (generatedKey.getColumn().equals(introspectedColumn.getActualColumnName())) {
                field.addAnnotation("@GeneratedValue");
                topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.GeneratedValue");
            }
        });

        if (!GeneratorUtils.isRelationField(field)) {
            StringBuilder sb = new StringBuilder(String.format("@Column(name = \"%s\"", introspectedColumn.getActualColumnName()));
            if (!StringUtils.equals("OTHER", introspectedColumn.getJdbcTypeName())) {
                sb.append(", jdbcType = JDBCType.").append(introspectedColumn.getJdbcTypeName());
                topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.JDBCType"));
            }
            if (StringUtils.isNotEmpty(introspectedColumn.getTypeHandler())) {
                FullyQualifiedJavaType typeHandler = new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
                topLevelClass.addImportedType(typeHandler);
                sb.append(", typeHandler = ").append(typeHandler.getShortName()).append(".class");
            }
            sb.append(")");
            field.addAnnotation(sb.toString());
            topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.Column");
        }

        if (StringUtils.equalsIgnoreCase(introspectedColumn.getActualColumnName(), introspectedTable.getTableConfiguration().getLogicDeleteColumn())) {
            field.addAnnotation("@LogicDelete");
            topLevelClass.addImportedType("io.entframework.kernel.db.api.annotation.LogicDelete");
            field.setAttribute(Constants.FIELD_LOGIC_DELETE_ATTR, true);
        }

        if (StringUtils.equalsIgnoreCase(introspectedColumn.getActualColumnName(), introspectedTable.getTableConfiguration().getVersionColumn())) {
            field.addAnnotation("@Version");
            topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.Version");
            field.setAttribute(Constants.FIELD_VERSION_ATTR, true);
        }

        return true;
    }

    /**
     * Intercepts base record class generation
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        JoinEntry joinEntry = context.getJoinConfig().getJoinEntry(tableName);
        if (joinEntry != null) {
            addJoinField(topLevelClass, joinEntry);
        }

        String fileDescription = GeneratorUtils.getFileDescription(introspectedTable);
        topLevelClass.setDescription(fileDescription);

        //IntrospectedTable 和 TopLevelClass 建立关联, TODO 是否有API，待验证
        introspectedTable.setAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS, topLevelClass);

        FullyQualifiedJavaType dynamicSqlSupportType = new FullyQualifiedJavaType(introspectedTable.getMyBatisDynamicSqlSupportType());
        topLevelClass.addAnnotation(String.format("@Table(value = \"%s\", sqlSupport = %s.class, tableProperty = \"%s\")",
                introspectedTable.getFullyQualifiedTableNameAtRuntime(),
                dynamicSqlSupportType.getShortName(),
                JavaBeansUtil.getValidPropertyName(introspectedTable.getMyBatisDynamicSQLTableObjectName())));

        topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.Table");
        topLevelClass.addImportedType(introspectedTable.getMyBatisDynamicSqlSupportType());
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        JoinEntry joinEntry = context.getJoinConfig().getJoinEntry(tableName);
        if (joinEntry != null) {
            // 验证join配置
            joinEntry.validate();
        }

        String fileDescription = GeneratorUtils.getFileDescription(introspectedTable);
        interfaze.setDescription(fileDescription);

        interfaze.setWriteMode(WriteMode.NEVER);
        return true;
    }

    private void addJoinField(TopLevelClass topLevelClass, JoinEntry joinEntry) {
        for (Pair<String, JoinTarget> detail : joinEntry.getDetails()) {
            JoinTarget target = detail.getRight();
            IntrospectedTable rightTable = GeneratorUtils.getIntrospectedTable(context, target.getRightTable());
            IntrospectedTable leftTable = GeneratorUtils.getIntrospectedTable(context, joinEntry.getLeftTable());

            FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(rightTable.getBaseRecordType());
            FullyQualifiedJavaType listReturnType = FullyQualifiedJavaType.getNewListInstance();
            listReturnType.addTypeArgument(recordType);
            FullyQualifiedJavaType filedType = target.getType() == JoinTarget.JoinType.MORE ? listReturnType : recordType;

            IntrospectedColumn introspectedColumn = new IntrospectedColumn();
            introspectedColumn.setJavaProperty(target.getFieldName());
            introspectedColumn.setContext(context);
            introspectedColumn.setIntrospectedTable(rightTable);
            introspectedColumn.setFullyQualifiedJavaType(filedType);
            introspectedColumn.setActualColumnName(target.getJoinColumn());
            IntrospectedColumn rightTableColumn = GeneratorUtils.getIntrospectedColumnByColumn(rightTable, target.getJoinColumn());

            Field field = getJavaBeansFieldWithGeneratedAnnotation(introspectedColumn, context, rightTable,
                    topLevelClass);
            field.setAttribute(Constants.FIELD_RELATION, true);
            if (context.getPlugins().modelFieldGenerated(field, topLevelClass, introspectedColumn, rightTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                Relation.Builder builder = Relation.builder();
                topLevelClass.addImportedType(recordType);
                if (target.getType() == JoinTarget.JoinType.MORE) {
                    topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());

                    IntrospectedColumn leftTableColumn = GeneratorUtils.getIntrospectedColumnByColumn(leftTable, detail.getLeft());
                    field.setDescription(GeneratorUtils.getFileDescription(rightTable));
                    builder.joinType(JoinTarget.JoinType.MORE)
                            .bindField(field)
                            .sourceField(GeneratorUtils.getFieldByName(topLevelClass, detail.getKey()))
                            .targetTable(rightTable)
                            .targetColumn(rightTableColumn);
                    field.addAnnotation("@OneToMany");
                    field.addAnnotation(String.format("@JoinColumn(target = %s.class, left = \"%s\", right = \"%s\")", recordType.getShortName(), leftTableColumn.getJavaProperty(), rightTableColumn.getJavaProperty()));
                    topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.OneToMany");
                    topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.JoinColumn");
                }

                if (target.getType() == JoinTarget.JoinType.ONE) {
                    String columnName = detail.getKey();
                    IntrospectedColumn leftColumn = GeneratorUtils.getIntrospectedColumnByColumn(leftTable, columnName);
                    Field relatedField = GeneratorUtils.getFieldByName(topLevelClass, leftColumn.getJavaProperty());
                    field.setDescription(relatedField.getDescription());
                    builder.sourceField(relatedField)
                            .joinType(JoinTarget.JoinType.ONE)
                            .bindField(field)
                            .targetTable(rightTable)
                            .displayField(rightTable.getTableConfiguration().getDisplayField())
                            .targetColumn(GeneratorUtils.getIntrospectedColumnByColumn(rightTable, target.getJoinColumn()));
                    field.addAnnotation("@ManyToOne");
                    field.addAnnotation(String.format("@JoinColumn(target = %s.class, left = \"%s\", right = \"%s\")", recordType.getShortName(), leftColumn.getJavaProperty(), rightTableColumn.getJavaProperty()));
                    topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.ManyToOne");
                    topLevelClass.addImportedType("org.mybatis.dynamic.sql.annotation.JoinColumn");
                }
                field.setAttribute(Constants.FIELD_RELATION, builder.build());
                //重置Field的注释行
                field.getJavaDocLines().clear();
                GeneratorUtils.addComment(field, field.getDescription());
                //关联关系的Field 添加到TopLevelClass中, 但是对应的Column并不添加到IntrospectedTable中
                topLevelClass.addField(field);
            }

            Method method = getJavaBeansGetterWithGeneratedAnnotation(introspectedColumn, context, rightTable,
                    topLevelClass);
            if (context.getPlugins().modelGetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, rightTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addMethod(method);
            }

            if (!rightTable.isImmutable()) {
                method = getJavaBeansSetterWithGeneratedAnnotation(introspectedColumn, context, rightTable,
                        topLevelClass);
                if (context.getPlugins().modelSetterMethodGenerated(method, topLevelClass,
                        introspectedColumn, rightTable,
                        Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }
            }
        }
    }
}

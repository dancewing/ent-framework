/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.generator;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.config.Relation;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JoinTarget;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GeneratorUtils {

    public static IntrospectedTable getIntrospectedTable(Context context, String tableName) {
        IntrospectedTable table = context.getIntrospectedTables().stream()
                .filter(introspectedTable -> introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()
                        .equals(tableName))
                .findFirst().orElseThrow(() -> new RuntimeException("can not find target table: " + tableName));
        table.initialize();
        return table;
    }

    public static String generateAliasedColumn(String tableName, String columnName) {
        return tableName + "_" + columnName;
    }

    public static IntrospectedColumn getIntrospectedColumnByColumn(IntrospectedTable introspectedTable,
            String columnName) {
        return introspectedTable.getAllColumns().stream()
                .filter(column -> column.getActualColumnName().equals(columnName)).findFirst()
                .orElseThrow(() -> new RuntimeException("can not find target column by column : " + columnName));
    }

    public static IntrospectedColumn safeGetIntrospectedColumnByColumn(IntrospectedTable introspectedTable,
            String columnName) {
        return introspectedTable.getAllColumns().stream()
                .filter(column -> column.getActualColumnName().equals(columnName)).findFirst().orElse(null);
    }

    public static IntrospectedColumn getIntrospectedColumnByJavaProperty(IntrospectedTable introspectedTable,
            String property) {
        return introspectedTable.getAllColumns().stream().filter(column -> column.getJavaProperty().equals(property))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("can not find target column by property : " + property));
    }

    public static IntrospectedColumn safeGetIntrospectedColumnByJavaProperty(IntrospectedTable introspectedTable,
            String property) {
        return introspectedTable.getAllColumns().stream().filter(column -> column.getJavaProperty().equals(property))
                .findFirst().orElse(null);
    }

    public static Field getFieldByName(TopLevelClass topLevelClass, String name) {
        return topLevelClass.getFields().stream().filter(column -> column.getName().equals(name)).findFirst()
                .orElseThrow(() -> new RuntimeException("can not find field by name : " + name));
    }

    public static boolean isLogicDeleteField(Field field) {
        return field.getAttribute(Constants.FIELD_LOGIC_DELETE_ATTR) != null
                && (Boolean) field.getAttribute(Constants.FIELD_LOGIC_DELETE_ATTR);
    }

    public static boolean isVersionField(Field field) {
        return field.getAttribute(Constants.FIELD_VERSION_ATTR) != null
                && (Boolean) field.getAttribute(Constants.FIELD_VERSION_ATTR);
    }

    public static boolean isVersionColumn(IntrospectedColumn column) {
        String versionColumn = column.getIntrospectedTable().getTableConfiguration().getVersionColumn();
        return (StringUtils.isNotEmpty(versionColumn)
                && StringUtils.equals(column.getActualColumnName(), versionColumn));
    }

    public static boolean isLogicDeleteColumn(IntrospectedColumn column) {
        String logicColumn = column.getIntrospectedTable().getTableConfiguration().getLogicDeleteColumn();
        return (StringUtils.isNotEmpty(logicColumn) && StringUtils.equals(column.getActualColumnName(), logicColumn));
    }

    public static Optional<Field> getLogicDeleteField(TopLevelClass topLevelClass) {
        return topLevelClass.getFields().stream().filter(GeneratorUtils::isLogicDeleteField).findFirst();
    }

    public static Optional<IntrospectedColumn> getLogicDeleteColumn(IntrospectedTable introspectedTable) {
        return introspectedTable.getAllColumns().stream().filter(GeneratorUtils::isLogicDeleteColumn).findFirst();
    }

    public static Optional<IntrospectedColumn> getVersionColumn(IntrospectedTable introspectedTable) {
        return introspectedTable.getAllColumns().stream().filter(GeneratorUtils::isVersionColumn).findFirst();
    }

    public static Optional<Field> getDisplayField(IntrospectedTable introspectedTable) {
        String displayField = introspectedTable.getTableConfiguration().getDisplayField();
        TopLevelClass modelClass = (TopLevelClass) introspectedTable
                .getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
        if (StringUtils.isEmpty(displayField)) {
            IntrospectedColumn pk = getPrimaryKey(introspectedTable);
            return modelClass.getFields().stream()
                    .filter(field -> StringUtils.equals(field.getName(), pk.getJavaProperty())).findFirst();
        }
        return modelClass.getFields().stream().filter(field -> StringUtils.equals(field.getName(), displayField))
                .findFirst();
    }

    public static IntrospectedColumn getPrimaryKey(IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            throw new RuntimeException("table " + introspectedTable.getBaseRecordType() + " has one more primary key");
        }
        if (introspectedTable.getPrimaryKeyColumns().size() == 0) {
            throw new RuntimeException("table " + introspectedTable.getBaseRecordType() + " has none primary key");
        }
        return introspectedTable.getPrimaryKeyColumns().get(0);
    }

    public static boolean isPrimaryKey(IntrospectedTable introspectedTable, IntrospectedColumn column) {
        return introspectedTable.getPrimaryKeyColumns().stream()
                .anyMatch(c -> StringUtils.equals(column.getJavaProperty(), c.getJavaProperty()));
    }

    public static boolean hasMethod(Interface interfaze, String method) {
        return interfaze.getMethods().stream().anyMatch(method1 -> method1.getName().equals(method));
    }

    public static boolean hasField(TopLevelClass topLevelClass, String field) {
        return topLevelClass.getFields().stream().anyMatch(f -> f.getName().equals(field));
    }

    /**
     * @param introspectedTable
     * @return e.g. TestTableDynamicSqlSupport.testTable
     */
    public static String getDynamicSqlSupportSubTableType(IntrospectedTable introspectedTable) {
        String tableFieldName = JavaBeansUtil
                .getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        return introspectedTable.getMyBatisDynamicSqlSupportType() + "." + tableFieldName;
    }

    public static String getFieldDescription(IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            return StringUtils.join(remarkLines, " ");
        }
        return StringUtils.capitalize(introspectedColumn.getJavaProperty());
    }

    public static String getFileDescription(IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            return StringUtils.join(remarkLines, " ");
        }
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        return StringUtils.capitalize(fqjt.getShortName());
    }

    public static boolean hasRelation(TopLevelClass modelClass, JoinTarget.JoinType joinType) {
        return modelClass.getFields().stream().anyMatch(field -> {
            Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
            if (relation != null && relation.getJoinType() != null) {
                return relation.getJoinType() == joinType;
            }
            return false;
        });
    }

    public static List<Field> getRelatedFields(TopLevelClass modelClass, JoinTarget.JoinType joinType) {
        return getRelatedFields(modelClass.getFields(), joinType);
    }

    public static List<Field> getRelatedFields(List<Field> fields, JoinTarget.JoinType joinType) {
        return fields.stream().filter(field -> {
            Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
            if (relation != null && relation.getJoinType() != null) {
                return relation.getJoinType() == joinType;
            }
            return false;
        }).toList();
    }

    public static boolean isRelationField(IntrospectedTable introspectedTable, Field field) {
        return field.getAttribute(Constants.FIELD_RELATION) != null;
    }

    public static boolean isRelationField(Field field) {
        return isRelationField(null, field);
    }

    public static boolean isInnerEnum(Field field) {
        return field.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR) != null;
    }

    public static FullyQualifiedJavaType getModelJavaType(Context context, String modelObjectName) {
        return new FullyQualifiedJavaType(
                context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + modelObjectName);
    }

    public static FullyQualifiedJavaType getModelJavaType(Context context, String modelObjectName,
            FullyQualifiedJavaType factory) {
        return factory.create(context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + modelObjectName);
    }

    public static void addComment(JavaElement element, String comment) {
        addComment(element, Collections.singletonList(comment));
    }

    public static void addComment(JavaElement element, List<String> comments) {
        if (comments == null || comments.size() == 0) {
            return;
        }
        element.addJavaDocLine("/**"); //$NON-NLS-1$
        for (String remarkLine : comments) {
            element.addJavaDocLine(" * " + remarkLine); //$NON-NLS-1$
        }
        element.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addFieldComment(Field field, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("/**"); //$NON-NLS-1$
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine); //$NON-NLS-1$
            }
        }
        else {
            field.addJavaDocLine(" * " + StringUtils.capitalize(field.getName()));
        }
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static List<Field> getFields(TopLevelClass modelClass) {
        List<Field> fields = new ArrayList<>(modelClass.getFields());
        TopLevelClass parentClass = (TopLevelClass) modelClass.getAttribute(Constants.PARENT_ENTITY_CLASS);
        if (parentClass != null) {
            fields.addAll(parentClass.getFields());
        }
        return fields;
    }

    public static boolean hasRelation(Context context, IntrospectedTable table) {
        return context.getJoinConfig().getJoinEntry(table.getFullyQualifiedTableNameAtRuntime()) != null;
    }

}

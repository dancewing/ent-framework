/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.generator.plugin.server.methods.repository;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.config.Relation;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import io.entframework.kernel.rule.util.Maps;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JoinTarget;

import java.util.*;

public class RepEnhancedCreateAndUpdateMethodGenerator extends AbstractMethodGenerator {

    public RepEnhancedCreateAndUpdateMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();

        imports.add(recordType);

        Method createMethod = new Method("insert"); //$NON-NLS-1$
        createMethod.setAbstract(isAbstract);
        createMethod.setReturnType(recordType);
        createMethod.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$

        Method updateMethod = new Method("update"); //$NON-NLS-1$
        updateMethod.setAbstract(isAbstract);
        updateMethod.setReturnType(recordType);
        updateMethod.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$

        Method batchCreateMethod = new Method("insertMultiple"); //$NON-NLS-1$
        batchCreateMethod.setAbstract(isAbstract);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        returnType.addTypeArgument(recordType);
        batchCreateMethod.setReturnType(returnType);

        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewListInstance(); //$NON-NLS-1$
        parameterType.addTypeArgument(recordType);
        imports.add(parameterType);
        batchCreateMethod.addParameter(new Parameter(parameterType, "records")); //$NON-NLS-1$

        if (this.isAbstract) {
            Map<String, Object> variables = Maps.of("RepositoryName", getRepositoryJavaType().getShortName())
                    .and("EntityName", recordType.getShortName()).build();
            GeneratorUtils.addComment(createMethod, CommentHelper.INSTANCE.getComments("create", "Repository", variables));
            GeneratorUtils.addComment(updateMethod, CommentHelper.INSTANCE.getComments("update", "Repository", variables));
            GeneratorUtils.addComment(batchCreateMethod, CommentHelper.INSTANCE.getComments("batchCreate", "Repository", variables));
        } else {
            GeneratorUtils.addComment(createMethod, "{@inheritDoc}");
            GeneratorUtils.addComment(updateMethod, "{@inheritDoc}");
            GeneratorUtils.addComment(batchCreateMethod, "{@inheritDoc}");
        }
        if (!isAbstract) {
            createMethod.addAnnotation("@Override");
            createMethod.setVisibility(JavaVisibility.PUBLIC);

            updateMethod.addAnnotation("@Override");
            updateMethod.setVisibility(JavaVisibility.PUBLIC);

            batchCreateMethod.addAnnotation("@Override");
            batchCreateMethod.setVisibility(JavaVisibility.PUBLIC);

            batchCreateMethod.addBodyLine("if (records == null || records.isEmpty()) {");
            batchCreateMethod.addBodyLine("return Collections.emptyList();");
            batchCreateMethod.addBodyLine("}");
            imports.add(new FullyQualifiedJavaType("java.util.Collections"));

            FullyQualifiedJavaType mapperJavaType = getMapperJavaType();
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());
            TopLevelClass hostTopLevelClass = (TopLevelClass) this.hostJavaClass;

            IntrospectedColumn pkColumn = GeneratorUtils.getPrimaryKey(introspectedTable);
            TopLevelClass modelClass = (TopLevelClass) this.introspectedTable.getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
            if (GeneratorUtils.hasRelation(modelClass, JoinTarget.JoinType.ONE)) {
                List<Field> fields = GeneratorUtils.getRelatedFields(modelClass, JoinTarget.JoinType.ONE);
                if (fields.size() > 0) {
                    for (Field field : fields) {
                        Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
                        Field relationField = relation.getSourceField();
                        IntrospectedTable relationTable = relation.getTargetTable();
                        IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(relationTable);
                        hostTopLevelClass.addImportedType(field.getType());
                        String mapperClass = String.format("getMapper(%s.class)", field.getType().getShortName());

                        createMethod.addBodyLine(String.format("%s %s = row.get%s();", field.getType().getShortName(), field.getName(),
                                StringUtils.capitalize(field.getName())));
                        createMethod.addBodyLine(String.format("if (%s != null && row.get%s() == null) {", field.getName(), StringUtils.capitalize(relationField.getName())));
                        createMethod.addBodyLine(String.format("%s.insert(%s);", mapperClass, field.getName()));
                        createMethod.addBodyLine(String.format("row.set%s(%s.get%s());", StringUtils.capitalize(relationField.getName()), field.getName(), StringUtils.capitalize(pk.getJavaProperty())));
                        createMethod.addBodyLine("}");

                        String pluralityName = field.getName() + "s";
                        batchCreateMethod.addBodyLine(String.format("List<%s> %s = new ArrayList<>();", field.getType().getShortName(), pluralityName));
                        batchCreateMethod.addBodyLine(String.format("for (%s record : records) {", recordType.getShortName()));
                        batchCreateMethod.addBodyLine(String.format("%s %s = record.get%s();", field.getType().getShortName(), field.getName(), StringUtils.capitalize(field.getName())));
                        batchCreateMethod.addBodyLine(String.format("Optional.ofNullable(%s).ifPresent(%s::add);", field.getName(), pluralityName));
                        batchCreateMethod.addBodyLine("}");
                        batchCreateMethod.addBodyLine(String.format("%s.insertMultiple(%s);", mapperClass, pluralityName));
                        batchCreateMethod.addBodyLine(String.format("records.forEach(record -> record.set%s(record.get%s().get%s()));",
                                StringUtils.capitalize(relationField.getName()),
                                StringUtils.capitalize(field.getName()), StringUtils.capitalize(pk.getJavaProperty())));
                        hostTopLevelClass.addImportedType("java.util.Optional");
                        hostTopLevelClass.addImportedType("java.util.List");
                        hostTopLevelClass.addImportedType("java.util.ArrayList");


                        updateMethod.addBodyLine(String.format("%s %s = row.get%s();", field.getType().getShortName(), field.getName(),
                                StringUtils.capitalize(field.getName())));
                        updateMethod.addBodyLine(String.format("if (%s != null) {", field.getName()));
                        updateMethod.addBodyLine(String.format("if (%s.get%s() != null) {", field.getName(), StringUtils.capitalize(pk.getJavaProperty())));
                        updateMethod.addBodyLine(String.format("%s.updateByPrimaryKey(%s);", mapperClass, field.getName()));
                        updateMethod.addBodyLine("} else {");
                        updateMethod.addBodyLine(String.format("%s.insert(%s);", mapperClass, field.getName()));
                        updateMethod.addBodyLine("}");
                        updateMethod.addBodyLine(String.format("row.set%s(%s.get%s());", StringUtils.capitalize(relationField.getName()), field.getName(), StringUtils.capitalize(pk.getJavaProperty())));
                        updateMethod.addBodyLine("}");

                    }
                }
            }

            createMethod.addBodyLine("int count = getMapper().insert(row);");
            createMethod.addBodyLine("if (count == 0) {");
            createMethod.addBodyLine("throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);");
            createMethod.addBodyLine("}");

            updateMethod.addBodyLine("int count = getMapper().updateByPrimaryKey(row);");
            updateMethod.addBodyLine("if (count == 0) {");
            updateMethod.addBodyLine(String.format("throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.get%s());", StringUtils.capitalize(pkColumn.getJavaProperty())));
            updateMethod.addBodyLine("}");
            imports.add(new FullyQualifiedJavaType("io.entframework.kernel.db.api.exception.DaoException"));
            imports.add(new FullyQualifiedJavaType("io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum"));

            batchCreateMethod.addBodyLine("getMapper().insertMultiple(records);");

            if (GeneratorUtils.hasRelation(modelClass, JoinTarget.JoinType.MORE)) {
                List<Field> fields = GeneratorUtils.getRelatedFields(modelClass, JoinTarget.JoinType.MORE);
                if (fields.size() > 0) {
                    for (Field field : fields) {
                        Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
                        Field sourceField = relation.getSourceField();
                        String targetFieldName = relation.getTargetColumn().getJavaProperty();
                        hostTopLevelClass.addImportedType(field.getType());
                        String relationFieldType = field.getType().getTypeArguments().get(0).getShortName();
                        FullyQualifiedJavaType oneColumnMapperType = getMapperJavaType(relationFieldType);
                        String oneColumnMapperField = StringUtils.uncapitalize(oneColumnMapperType.getShortName());
                        //Field mapperField = findMapperField(hostTopLevelClass, oneColumnMapperField, oneColumnMapperType);
                        String mapperClass = String.format("getMapper(%s.class)", relationFieldType);
                        createMethod.addBodyLine(String.format("%s %s = row.get%s();", field.getType().getShortName(), field.getName(),
                                StringUtils.capitalize(field.getName())));
                        createMethod.addBodyLine(String.format("if (%s != null && !%s.isEmpty()) {", field.getName(), field.getName()));
                        createMethod.addBodyLine(String.format("%s.forEach(r -> r.set%s(row.get%s()));", field.getName(), StringUtils.capitalize(targetFieldName), StringUtils.capitalize(sourceField.getName())));
                        createMethod.addBodyLine(String.format("%s.insertMultiple(%s);", mapperClass, field.getName()));
                        createMethod.addBodyLine("}");

                        String pluralityName = "all" + StringUtils.capitalize(field.getName());
                        FullyQualifiedJavaType fieldType = field.getType().getTypeArguments().get(0);
                        batchCreateMethod.addBodyLine(String.format("List<%s> %s = new ArrayList<>();", fieldType.getShortName(), pluralityName));

                        batchCreateMethod.addBodyLine(String.format("for (%s row : records) {", recordType.getShortName()));
                        batchCreateMethod.addBodyLine(String.format("%s %s = row.get%s();", field.getType().getShortName(), field.getName(),
                                StringUtils.capitalize(field.getName())));
                        batchCreateMethod.addBodyLine(String.format("if (%s != null && !%s.isEmpty()) {", field.getName(), field.getName()));
                        batchCreateMethod.addBodyLine(String.format("%s.forEach(r -> r.set%s(row.get%s()));", field.getName(), StringUtils.capitalize(targetFieldName), StringUtils.capitalize(sourceField.getName())));
                        batchCreateMethod.addBodyLine(String.format("%s.addAll(%s);", pluralityName, field.getName()));
                        batchCreateMethod.addBodyLine("}");
                        batchCreateMethod.addBodyLine("}");
                        batchCreateMethod.addBodyLine(String.format("%s.insertMultiple(%s);", mapperClass, pluralityName));

                        updateMethod.addBodyLine(String.format("%s %s = row.get%s();", field.getType().getShortName(), field.getName(),
                                StringUtils.capitalize(field.getName())));
                        updateMethod.addBodyLine(String.format("if (%s != null && !%s.isEmpty()) {", field.getName(), field.getName()));
                        updateMethod.addBodyLine(String.format("%s.forEach(r -> r.set%s(row.get%s()));", field.getName(), StringUtils.capitalize(targetFieldName), StringUtils.capitalize(sourceField.getName())));
                        FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType(relationFieldType);
                        hostTopLevelClass.addImportedType(mapperSupportJavaType);

                        IntrospectedTable targetTable = relation.getTargetColumn().getIntrospectedTable();
                        Optional<IntrospectedColumn> logicDelete = GeneratorUtils.getLogicDeleteColumn(targetTable);
                        if (logicDelete.isPresent()) {
                            updateMethod.addBodyLine("// " + GeneratorUtils.getFileDescription(targetTable) + "启用了逻辑筛选");
                            updateMethod.addBodyLine(String.format("%s.update(c -> c.set(%s.%s).equalTo(YesOrNotEnum.Y)", mapperClass, mapperSupportJavaType.getShortName(), logicDelete.get().getJavaProperty()));
                            imports.add(new FullyQualifiedJavaType("io.entframework.kernel.rule.enums.YesOrNotEnum"));
                            imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlBuilder"));
                            updateMethod.addBodyLine(String.format("        .where(%s.%s, SqlBuilder.isEqualTo(row.get%s())));", mapperSupportJavaType.getShortName(), targetFieldName, StringUtils.capitalize(sourceField.getName())));
                        } else {
                            updateMethod.addBodyLine(String.format("%s.delete(c -> c.where(%s.%s, isEqualTo(row.get%s())));", mapperClass, mapperSupportJavaType.getShortName(), targetFieldName, StringUtils.capitalize(sourceField.getName())));
                        }
                        updateMethod.addBodyLine(String.format("%s.insertMultiple(%s);", mapperClass, field.getName()));
                        updateMethod.addBodyLine("}");
                    }
                }
            }


            createMethod.addBodyLine("return row;");

            updateMethod.addBodyLine("return row;");
            batchCreateMethod.addBodyLine("return records;");
        }


        return MethodAndImports.withMethod(createMethod).withMethod(batchCreateMethod).withMethod(updateMethod)
                .withImports(imports).withStaticImports(staticImports)
                .build();
    }
}

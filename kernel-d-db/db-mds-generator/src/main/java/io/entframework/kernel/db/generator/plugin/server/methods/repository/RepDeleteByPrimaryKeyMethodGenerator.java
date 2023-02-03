/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.generator.plugin.server.methods.repository;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.plugin.server.methods.Utils;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import io.entframework.kernel.rule.util.Maps;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RepDeleteByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {

    public RepDeleteByPrimaryKeyMethodGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!Utils.generateDeleteByPrimaryKey(introspectedTable)) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Method method = new Method("delete"); //$NON-NLS-1$
        method.setAbstract(isAbstract);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "row"));

        if (this.isAbstract) {
            Map<String, Object> variables = Maps.of("RepositoryName", getRepositoryJavaType().getShortName())
                    .and("EntityName", recordType.getShortName()).build();
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("deleteById", "Repository", variables));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        if (!isAbstract) {
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType();
            FullyQualifiedJavaType mapperJavaType = getMapperJavaType();
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());

            IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
            Optional<IntrospectedColumn> logicDelete = GeneratorUtils.getLogicDeleteColumn(introspectedTable);
            Optional<IntrospectedColumn> version = GeneratorUtils.getVersionColumn(introspectedTable);

            method.addBodyLine(String.format("if (row == null || row.get%s() == null) {", StringUtils.capitalize(pk.getJavaProperty())));
            method.addBodyLine("throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);");
            method.addBodyLine("}");
            if (version.isPresent()) {
                IntrospectedColumn versionColumn = version.get();
                method.addBodyLine(String.format("if (row.get%s() == null) {", StringUtils.capitalize(versionColumn.getJavaProperty())));
                method.addBodyLine("throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);");
                method.addBodyLine("}");
            }
            builder.withImport("io.entframework.kernel.rule.exception.base.ServiceException");
            builder.withImport("io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum");
            if (logicDelete.isPresent()) {
                IntrospectedColumn logicDeleteColumn = logicDelete.get();
                method.addBodyLine(String.format("row.set%s(YesOrNotEnum.Y);", StringUtils.capitalize(logicDeleteColumn.getJavaProperty())));
                method.addBodyLine("return getMapper().updateByPrimaryKey(row, true);");
            } else {
                if (version.isPresent()) {
                    builder.withImport(mapperSupportJavaType);
                    builder.withImport(mapperValName);
                    builder.withImport("org.mybatis.dynamic.sql.delete.DeleteDSL");
                    builder.withImport("org.mybatis.dynamic.sql.delete.DeleteModel");
                    builder.withImport("java.util.Objects");
                    builder.withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");
                    method.addBodyLine("return getMapper().delete(c -> {");
                    method.addBodyLine("DeleteDSL<DeleteModel>.DeleteWhereBuilder deleteDSL = c.where();");
                    method.addBodyLine(String.format("deleteDSL.and(%s.%s, isEqualTo(row.get%s()).filter(Objects::nonNull));",
                            mapperSupportJavaType.getShortName(), pk.getJavaProperty(), StringUtils.capitalize(pk.getJavaProperty())));
                    IntrospectedColumn versionColumn = version.get();
                    method.addBodyLine(String.format("deleteDSL.and(%s.%s, isEqualTo(row.get%s()).filter(Objects::nonNull));",
                            mapperSupportJavaType.getShortName(), versionColumn.getJavaProperty(), StringUtils.capitalize(versionColumn.getJavaProperty())));
                    method.addBodyLine("return deleteDSL;");
                    method.addBodyLine("});");
                } else {
                    method.addBodyLine(String.format("return getMapper().deleteByPrimaryKey(row.get%s());", StringUtils.capitalize(pk.getJavaProperty())));
                }

            }


        }

        return builder.build();
    }
}

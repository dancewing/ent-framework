/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.server.methods.service;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SrvBasicSelectDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public SrvBasicSelectDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(getPojoRequestJavaType());

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSLCompleter");
        Method defaultSelectDSL = new Method("defaultSelectDSL"); //$NON-NLS-1$
        defaultSelectDSL.setReturnType(returnType);
        defaultSelectDSL.setDefault(true);
        imports.add(returnType);
        defaultSelectDSL.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        Method getOrderBy = new Method("getOrderBy"); //$NON-NLS-1$
        getOrderBy.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$
        FullyQualifiedJavaType sortJavaType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SortSpecification");
        imports.add(sortJavaType);
        getOrderBy.setReturnType(sortJavaType);
        getOrderBy.setDefault(true);

        FullyQualifiedJavaType queryExpressionDSL = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.QueryExpressionDSL");
        queryExpressionDSL.addTypeArgument(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectModel"));
        Method defaultQuerySelectDSL = new Method("defaultQuerySelectDSL"); //$NON-NLS-1$
        defaultQuerySelectDSL.setReturnType(queryExpressionDSL);
        imports.add(queryExpressionDSL);
        defaultQuerySelectDSL.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$
        defaultQuerySelectDSL.setDefault(true);

        Method applyWhereBuilder = new Method("applyWhereBuilder");
        FullyQualifiedJavaType paramWhereDSL = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.AbstractWhereDSL");
        paramWhereDSL.addTypeArgument(new FullyQualifiedJavaType("?"));
        applyWhereBuilder.addParameter(new Parameter(paramWhereDSL, "whereDSL"));
        applyWhereBuilder.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //
        applyWhereBuilder.setDefault(true);
        imports.add(paramWhereDSL);


        if (this.isAbstract) {
            GeneratorUtils.addComment(defaultSelectDSL, CommentHelper.INSTANCE.getComments("defaultSelectDSL", "Service"));
            GeneratorUtils.addComment(defaultQuerySelectDSL, CommentHelper.INSTANCE.getComments("defaultQuerySelectDSL", "Service"));
            GeneratorUtils.addComment(applyWhereBuilder, CommentHelper.INSTANCE.getComments("applyWhereBuilder", "Service"));
            GeneratorUtils.addComment(getOrderBy, CommentHelper.INSTANCE.getComments("getOrderBy", "Service"));
        } else {
            GeneratorUtils.addComment(defaultSelectDSL, "{@inheritDoc}");
            GeneratorUtils.addComment(defaultQuerySelectDSL, "{@inheritDoc}");
            GeneratorUtils.addComment(applyWhereBuilder, "{@inheritDoc}");
            GeneratorUtils.addComment(getOrderBy, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(defaultSelectDSL)
                .withMethod(defaultQuerySelectDSL)
                .withMethod(applyWhereBuilder)
                .withMethod(getOrderBy)
                .withImports(imports);

        IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
        if (this.isAbstract) {

            defaultSelectDSL.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType();
            FullyQualifiedJavaType mapperJavaType = getMapperJavaType();

            builder.withImport(mapperSupportJavaType)
                    .withImport(new FullyQualifiedJavaType("io.entframework.kernel.db.mds.util.MyBatis3CustomUtils"));

            defaultSelectDSL.addBodyLine("return c -> {");
            defaultSelectDSL.addBodyLine("SortSpecification orderBy = getOrderBy(request);");
            builder.withImport(new FullyQualifiedJavaType("org.apache.commons.lang3.StringUtils"));
            builder.withImport(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SortSpecification"));
            builder.withImport(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SimpleSortSpecification"));
            builder.withImport(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.ColumnSortSpecification"));
            builder.withImport(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlColumn"));
            builder.withImport(new FullyQualifiedJavaType("io.entframework.kernel.db.mds.util.DynamicSqlSupportUtils"));

            defaultSelectDSL.addBodyLine("QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder selectDSL = c.where();");
            defaultSelectDSL.addBodyLine("applyWhereBuilder(selectDSL, request);");
            defaultSelectDSL.addBodyLine("selectDSL.orderBy(orderBy);");
            defaultSelectDSL.addBodyLine("Integer pageNo = request.getPageNo();");
            defaultSelectDSL.addBodyLine("Integer pageSize = request.getPageSize();");
            defaultSelectDSL.addBodyLine("return MyBatis3CustomUtils.buildPagination(c, pageNo, pageSize);");
            defaultSelectDSL.addBodyLine("};");


            applyWhereBuilder.setDefault(true);

            builder.withImport(mapperSupportJavaType);
            builder.withImport("java.util.Objects");
            builder.withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");

            int index = 0;
            List<String> entityProperties = introspectedTable.getAllColumns().stream()
                    .map(IntrospectedColumn::getJavaProperty).collect(Collectors.toList());
            TopLevelClass modelClass = (TopLevelClass) this.introspectedTable.getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
            List<Field> fields = modelClass.getFields().stream()
                    .filter(field -> entityProperties.contains(field.getName())).collect(Collectors.toList());
            int size = fields.size();
            for (Field field : fields) {
                String getMethodName = "get" + StringUtils.capitalize(field.getName());
                String lineEnding = index == size - 1 ? ";" : "";
                if (index == 0) {
                    applyWhereBuilder.addBodyLine(String.format("whereDSL.and(%s.%s, isEqualTo(request.%s()).filter(Objects::nonNull))%s",
                            mapperSupportJavaType.getShortName(), field.getName(), getMethodName, lineEnding));
                } else {
                    if (GeneratorUtils.isLogicDeleteField(field)) {
                        applyWhereBuilder.addBodyLine(String.format("    .and(%s.%s, isEqualTo(%s).filter(Objects::nonNull))%s",
                                mapperSupportJavaType.getShortName(), field.getName(), "YesOrNotEnum.N", lineEnding));
                        builder.withImport(new FullyQualifiedJavaType("io.entframework.kernel.rule.enums.YesOrNotEnum"));
                    } else {
                        applyWhereBuilder.addBodyLine(String.format("    .and(%s.%s, isEqualTo(request.%s()).filter(Objects::nonNull))%s",
                                mapperSupportJavaType.getShortName(), field.getName(), getMethodName, lineEnding));
                    }
                }
                index++;
            }

            getOrderBy.setDefault(true);
            getOrderBy.addBodyLine(String.format("SortSpecification orderBy = SimpleSortSpecification.of(\"%s\").descending();", pk.getActualColumnName()));
            getOrderBy.addBodyLine("if (StringUtils.isNotEmpty(request.getOrderBy())) {");
            getOrderBy.addBodyLine(String.format("SqlColumn<?> column = DynamicSqlSupportUtils.findColumn(%s.%s, request.getOrderBy());",
                    mapperSupportJavaType.getShortName(), StringUtils.uncapitalize(recordType.getShortName())));
            getOrderBy.addBodyLine(String.format("String tableAlias = %s.%s.tableNameAtRuntime();", mapperSupportJavaType.getShortName(),
                    StringUtils.uncapitalize(recordType.getShortName())));
            getOrderBy.addBodyLine("orderBy = new ColumnSortSpecification(tableAlias, column);");
            getOrderBy.addBodyLine("if (StringUtils.equalsIgnoreCase(\"desc\", request.getSortBy())) {");
            getOrderBy.addBodyLine("orderBy = orderBy.descending();");
            getOrderBy.addBodyLine("}");
            getOrderBy.addBodyLine("}");
            getOrderBy.addBodyLine("return orderBy;");


            defaultQuerySelectDSL.setVisibility(JavaVisibility.PUBLIC);
            builder.withImport("org.mybatis.dynamic.sql.SqlBuilder");
            defaultQuerySelectDSL.addBodyLine(String.format("QueryExpressionDSL<SelectModel> start = SqlBuilder.select(%s.selectList).from(%s.%s);",
                    mapperSupportJavaType.getShortName(),
                    mapperSupportJavaType.getShortName(),
                    StringUtils.uncapitalize(recordType.getShortName())));
            defaultQuerySelectDSL.addBodyLine("SortSpecification orderBy = getOrderBy(request);");
            defaultQuerySelectDSL.addBodyLine("QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder queryDSL = start.where();");
            defaultQuerySelectDSL.addBodyLine("applyWhereBuilder(queryDSL, request);");
            defaultQuerySelectDSL.addBodyLine("queryDSL.orderBy(orderBy);");
            defaultQuerySelectDSL.addBodyLine("Integer pageNo = request.getPageNo();");
            defaultQuerySelectDSL.addBodyLine("Integer pageSize = request.getPageSize();");
            defaultQuerySelectDSL.addBodyLine("if (pageSize != null && pageSize >= 0) {");
            defaultQuerySelectDSL.addBodyLine("start.limit(pageSize);");
            defaultQuerySelectDSL.addBodyLine("if (pageNo != null && pageNo - 1 >= 0) {");
            defaultQuerySelectDSL.addBodyLine("int offset = (pageNo - 1) * pageSize;");
            defaultQuerySelectDSL.addBodyLine("start.offset(offset);");
            defaultQuerySelectDSL.addBodyLine("}");
            defaultQuerySelectDSL.addBodyLine("}");
            defaultQuerySelectDSL.addBodyLine("return start;");
        }

        return builder.build();
    }
}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package org.mybatis.generator.runtime.dynamic.sql;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class DynamicSqlMapperExtGenerator extends AbstractJavaClientGenerator {

    // record type for insert, select, update
    protected FullyQualifiedJavaType recordType;

    // id to use for the common result map
    protected String resultMapId;

    // name of the field containing the table in the support class
    protected String tableFieldName;

    protected FragmentGenerator fragmentGenerator;

    protected boolean hasGeneratedKeys;

    public DynamicSqlMapperExtGenerator(String project) {
        super(project, false);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        preCalculate();

        Interface interfaze = createBasicInterface();

        TopLevelClass supportClass = getSupportClass();

        List<CompilationUnit> answer = new ArrayList<>();
        if (context.getPlugins().clientGenerated(interfaze, introspectedTable)) {
            answer.add(interfaze);
        }

        if (context.getPlugins().dynamicSqlSupportGenerated(supportClass, introspectedTable)) {
            answer.add(supportClass);
        }

        return answer;
    }

    protected void preCalculate() {
        recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result"; //$NON-NLS-1$
        tableFieldName = JavaBeansUtil.getValidPropertyName(introspectedTable.getMyBatisDynamicSQLTableObjectName());
        fragmentGenerator = new FragmentGenerator.Builder().withIntrospectedTable(introspectedTable)
                .withResultMapId(resultMapId).withTableFieldName(tableFieldName).build();

        hasGeneratedKeys = introspectedTable.getGeneratedKey().isPresent();
    }

    protected Interface createBasicInterface() {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(interfaze);
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper")); //$NON-NLS-1$
        interfaze.addAnnotation("@Mapper"); //$NON-NLS-1$

        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("io.entframework.kernel.db.mds.mapper.BaseMapper");
        fqjt.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        interfaze.addSuperInterface(fqjt);
        interfaze.addImportedType(fqjt);
        return interfaze;
    }

    protected TopLevelClass getSupportClass() {
        return DynamicSqlSupportClassGenerator.of(introspectedTable, context.getCommentGenerator(), warnings)
                .generate();
    }

    protected boolean generate(Interface interfaze, AbstractMethodGenerator generator) {
        MethodAndImports mi = generator.generateMethodAndImports();
        if (mi != null && generator.callPlugins(mi.getMethod(), interfaze)) {
            interfaze.addMethod(mi.getMethod());
            interfaze.addImportedTypes(mi.getImports());
            interfaze.addStaticImports(mi.getStaticImports());
            return true;
        }
        return false;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return null;
    }

}

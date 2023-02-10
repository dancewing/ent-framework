package io.entframework.kernel.db.generator.plugin.server.methods.repository;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class RepBasicDynamicSQLMethodGenerator extends AbstractMethodGenerator {
    public RepBasicDynamicSQLMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        FullyQualifiedJavaType parameterInsertStatementType = new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.insert.render.InsertStatementProvider"); //$NON-NLS-1$
        parameterInsertStatementType.addTypeArgument(recordType);
        imports.add(parameterInsertStatementType);
        Method insertStatementMethod = new Method("insert"); //$NON-NLS-1$
        insertStatementMethod.setAbstract(isAbstract);
        insertStatementMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Parameter parameterInsert = new Parameter(parameterInsertStatementType, "statement");
        insertStatementMethod.addParameter(parameterInsert); //$NON-NLS-1$

        FullyQualifiedJavaType parameterUpdateStatementType = new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.update.render.UpdateStatementProvider"); //$NON-NLS-1$
        imports.add(parameterUpdateStatementType);
        Method updateStatementMethod = new Method("update"); //$NON-NLS-1$
        updateStatementMethod.setAbstract(isAbstract);
        updateStatementMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        updateStatementMethod.addParameter(new Parameter(parameterUpdateStatementType, "statement")); //$NON-NLS-1$

        FullyQualifiedJavaType parameterDeleteStatementType = new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider"); //$NON-NLS-1$
        imports.add(parameterDeleteStatementType);
        Method deleteStatementMethod = new Method("delete"); //$NON-NLS-1$
        deleteStatementMethod.setAbstract(isAbstract);
        deleteStatementMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        deleteStatementMethod.addParameter(new Parameter(parameterDeleteStatementType, "statement")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(insertStatementMethod, CommentHelper.INSTANCE.getComments("insert", "Repository", "StatementProvider"));
            GeneratorUtils.addComment(updateStatementMethod, CommentHelper.INSTANCE.getComments("update", "Repository", "StatementProvider"));
            GeneratorUtils.addComment(deleteStatementMethod, CommentHelper.INSTANCE.getComments("delete", "Repository", "StatementProvider"));
        } else {
            GeneratorUtils.addComment(insertStatementMethod, "{@inheritDoc}");
            GeneratorUtils.addComment(updateStatementMethod, "{@inheritDoc}");
            GeneratorUtils.addComment(deleteStatementMethod, "{@inheritDoc}");
        }
        if (!isAbstract) {
            FullyQualifiedJavaType mapperJavaType = getMapperJavaType();
            String mapperValName = StringUtils.uncapitalize(mapperJavaType.getShortName());
            imports.add(mapperJavaType);

            insertStatementMethod.addAnnotation("@Override");
            insertStatementMethod.setVisibility(JavaVisibility.PUBLIC);
            insertStatementMethod.addBodyLine(String.format("return %s.insert(statement);",
                    mapperValName));

            updateStatementMethod.addAnnotation("@Override");
            updateStatementMethod.setVisibility(JavaVisibility.PUBLIC);
            updateStatementMethod.addBodyLine(String.format("return %s.update(statement);",
                    mapperValName));

            deleteStatementMethod.addAnnotation("@Override");
            deleteStatementMethod.setVisibility(JavaVisibility.PUBLIC);
            deleteStatementMethod.addBodyLine(String.format("return %s.delete(statement);",
                    mapperValName));

        }

        return MethodAndImports
                .withMethod(insertStatementMethod)
                .withMethod(updateStatementMethod)
                .withMethod(deleteStatementMethod)
                .withImports(imports)
                .build();
    }
}

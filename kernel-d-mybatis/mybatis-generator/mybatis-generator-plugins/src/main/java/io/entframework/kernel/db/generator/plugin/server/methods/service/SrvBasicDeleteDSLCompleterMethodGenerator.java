package io.entframework.kernel.db.generator.plugin.server.methods.service;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class SrvBasicDeleteDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public SrvBasicDeleteDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(getPojoRequestJavaType());

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.delete.DeleteDSLCompleter");
        Method method = new Method("defaultDeleteDSL"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(returnType);
        imports.add(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("defaultDeleteDSL", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }
        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        if (this.isAbstract) {

            method.setVisibility(JavaVisibility.PUBLIC);

            builder.withImport("org.mybatis.dynamic.sql.delete.DeleteDSL");
            builder.withImport("org.mybatis.dynamic.sql.delete.DeleteModel");
            method.addBodyLine("return c -> {");
            method.addBodyLine("DeleteDSL<DeleteModel>.DeleteWhereBuilder deleteDSL = c.where();");
            method.addBodyLine("applyWhereBuilder(deleteDSL, request);");
            method.addBodyLine("return deleteDSL;");
            method.addBodyLine("};");

        }
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);


        return builder.build();
    }
}

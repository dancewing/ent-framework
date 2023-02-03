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

public class SrvCountDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public SrvCountDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(getPojoRequestJavaType());

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.CountDSLCompleter");
        Method method = new Method("defaultCountDSL"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(returnType);
        imports.add(returnType);
        method.addParameter(new Parameter(getPojoRequestJavaType(), "request")); //$NON-NLS-1$

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("defaultCountDSL", "Service"));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);
        // isAbstract 为 false，接口实现类
        if (this.isAbstract) {

            method.setVisibility(JavaVisibility.PUBLIC);
            builder.withImport("org.mybatis.dynamic.sql.select.CountDSL");
            builder.withImport("org.mybatis.dynamic.sql.select.SelectModel");
            method.addBodyLine("return c -> {");
            method.addBodyLine("CountDSL<SelectModel>.CountWhereBuilder countDSL = c.where();");
            method.addBodyLine("applyWhereBuilder(countDSL, request);");
            method.addBodyLine("return countDSL;");
            method.addBodyLine("};");
        }

        return builder.build();
    }
}

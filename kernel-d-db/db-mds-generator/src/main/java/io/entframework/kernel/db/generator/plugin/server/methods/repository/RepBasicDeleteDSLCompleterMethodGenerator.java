package io.entframework.kernel.db.generator.plugin.server.methods.repository;

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

public class RepBasicDeleteDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public RepBasicDeleteDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(recordType);

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.delete.DeleteDSLCompleter");

        Method method = new Method("defaultDeleteDSL"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("defaultDeleteDSL", "Repository"));
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addBodyLine("return c -> {");
            builder.withImport("org.mybatis.dynamic.sql.delete.DeleteDSL");
            builder.withImport("org.mybatis.dynamic.sql.delete.DeleteModel");
            method.addBodyLine("DeleteDSL<DeleteModel>.DeleteWhereBuilder deleteDSL = c.where();");
            method.addBodyLine("applyWhereBuilder(deleteDSL, row);");
            method.addBodyLine("return deleteDSL;");
            method.addBodyLine("};");

        }

        return builder.build();
    }
}

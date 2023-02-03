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

public class RepBasicSelectDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public RepBasicSelectDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(recordType);

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSLCompleter");
        Method method = new Method("defaultSelectDSL"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "pageNo")); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "pageSize")); //$NON-NLS-1$

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);

        if (this.isAbstract) {
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("defaultSelectDSL", "Repository", "Pageable"));

            FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType();
            FullyQualifiedJavaType objectsJavaType = new FullyQualifiedJavaType("java.util.Objects");
            builder.withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");

            method.setVisibility(JavaVisibility.PUBLIC);

            builder.withImport(mapperSupportJavaType)
                    .withImport(objectsJavaType)
                    .withImport(new FullyQualifiedJavaType("io.entframework.kernel.db.mds.util.MyBatis3CustomUtils"));

            builder.withImport("org.mybatis.dynamic.sql.select.QueryExpressionDSL");
            builder.withImport("org.mybatis.dynamic.sql.select.SelectModel");
            method.addBodyLine("return c -> {");
            method.addBodyLine("QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder selectDSL = c.where();");
            method.addBodyLine("applyWhereBuilder(selectDSL, row);");
            method.addBodyLine("return MyBatis3CustomUtils.buildPagination(c, pageNo, pageSize);");
            method.addBodyLine("};");
        }

        return builder.build();
    }
}

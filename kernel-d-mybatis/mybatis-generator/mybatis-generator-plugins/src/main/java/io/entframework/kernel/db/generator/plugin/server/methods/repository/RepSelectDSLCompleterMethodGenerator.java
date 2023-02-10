package io.entframework.kernel.db.generator.plugin.server.methods.repository;

import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import io.entframework.kernel.db.generator.utils.CommentHelper;
import io.entframework.kernel.rule.util.Maps;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RepSelectDSLCompleterMethodGenerator extends AbstractMethodGenerator {
    public RepSelectDSLCompleterMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(recordType);

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSLCompleter");
        Method method = new Method("defaultSelectDSL"); //$NON-NLS-1$
        method.setAbstract(this.isAbstract);
        method.setReturnType(returnType);
        imports.add(returnType);
        method.addParameter(new Parameter(recordType, "row")); //$NON-NLS-1$

        if (this.isAbstract) {
            IntrospectedColumn pk = GeneratorUtils.getPrimaryKey(introspectedTable);
            Map<String, Object> variables = Maps.of("RepositoryName", getRepositoryJavaType().getShortName())
                    .and("EntityName", recordType.getShortName())
                    .and("MapperName", getMapperJavaType().getFullyQualifiedName())
                    .and("ParamType", pk.getFullyQualifiedJavaType().getFullyQualifiedName()).build();
            GeneratorUtils.addComment(method, CommentHelper.INSTANCE.getComments("defaultSelectDSL", "Repository", variables));
        } else {
            GeneratorUtils.addComment(method, "{@inheritDoc}");
        }

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);
        // isAbstract 为 false，接口实现类
        if (!this.isAbstract) {
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addAnnotation("@Override");
            method.addBodyLine("return this.defaultSelectDSL(row, null, null);");

        }

        return builder.build();
    }
}

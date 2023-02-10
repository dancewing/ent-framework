package io.entframework.kernel.db.generator.plugin.server.methods.repository;

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

public class RepWhereBuilderMethodGenerator extends AbstractMethodGenerator {
    public RepWhereBuilderMethodGenerator(BuildConfig config) {
        super(config);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        Method applyWhereBuilder = new Method("applyWhereBuilder");
        FullyQualifiedJavaType paramWhereDSL = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.AbstractWhereDSL");
        paramWhereDSL.addTypeArgument(new FullyQualifiedJavaType("?"));
        applyWhereBuilder.addParameter(new Parameter(paramWhereDSL, "whereDSL"));
        applyWhereBuilder.addParameter(new Parameter(recordType, "row")); //
        applyWhereBuilder.setDefault(true);
        imports.add(recordType);
        imports.add(paramWhereDSL);

        MethodAndImports.Builder builder = MethodAndImports
                .withMethod(applyWhereBuilder)
                .withImports(imports);
        if (this.isAbstract) {

            GeneratorUtils.addComment(applyWhereBuilder, CommentHelper.INSTANCE.getComments("applyWhereBuilder", "Repository"));

            FullyQualifiedJavaType mapperSupportJavaType = getMapperSupportJavaType();
            builder.withImport(mapperSupportJavaType);
            builder.withImport("java.util.Objects");
            builder.withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");

            int index = 0;
            List<String> entityProperties = introspectedTable.getAllColumns().stream()
                    .map(IntrospectedColumn::getJavaProperty).collect(Collectors.toList());
            TopLevelClass modelClass = (TopLevelClass) this.introspectedTable.getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
            List<Field> fields = GeneratorUtils.getFields(modelClass).stream()
                    .filter(field -> entityProperties.contains(field.getName())).collect(Collectors.toList());
            int size = fields.size();
            for (Field field : fields) {
                String getMethodName = "get" + StringUtils.capitalize(field.getName());
                String lineEnding = index == size - 1 ? ";" : "";
                if (index == 0) {
                    applyWhereBuilder.addBodyLine(String.format("whereDSL.and(%s.%s, isEqualTo(row.%s()).filter(Objects::nonNull))%s",
                            mapperSupportJavaType.getShortName(), field.getName(), getMethodName, lineEnding));
                } else {
                    if (GeneratorUtils.isLogicDeleteField(field)) {
                        applyWhereBuilder.addBodyLine(String.format("    .and(%s.%s, isEqualTo(%s).filter(Objects::nonNull))%s",
                                mapperSupportJavaType.getShortName(), field.getName(), "YesOrNotEnum.N", lineEnding));
                        builder.withImport(new FullyQualifiedJavaType("io.entframework.kernel.rule.enums.YesOrNotEnum"));
                    } else {
                        applyWhereBuilder.addBodyLine(String.format("    .and(%s.%s, isEqualTo(row.%s()).filter(Objects::nonNull))%s",
                                mapperSupportJavaType.getShortName(), field.getName(), getMethodName, lineEnding));
                    }
                }
                index++;
            }
        }


        return builder.build();
    }
}

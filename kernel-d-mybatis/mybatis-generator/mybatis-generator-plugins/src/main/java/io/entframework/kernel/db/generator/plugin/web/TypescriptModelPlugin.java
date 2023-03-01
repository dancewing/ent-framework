package io.entframework.kernel.db.generator.plugin.web;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.FieldAndImports;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.generator.PojoFieldsGenerator;
import io.entframework.kernel.db.generator.plugin.web.runtime.DefaultTypescriptFormatter;
import io.entframework.kernel.db.generator.plugin.web.runtime.FullyQualifiedTypescriptType;
import io.entframework.kernel.db.generator.plugin.web.runtime.GeneratedTypescriptFile;
import io.entframework.kernel.db.generator.plugin.web.runtime.TypescriptTopLevelClass;
import io.entframework.kernel.db.generator.utils.ClassInfo;
import io.entframework.kernel.db.generator.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成Typescript的Request类
 */
public class TypescriptModelPlugin extends AbstractWebPlugin {

    private final List<GeneratedFile> generatedFiles = new ArrayList<>();

    private ClassInfo pojoRequestRootClassInfo;

    protected String pojoRequestRootClass = "";

    @Override
    public boolean validate(List<String> warnings) {
        boolean validate = super.validate(warnings);

        this.pojoRequestRootClass = this.properties.getProperty("pojoRequestRootClass");

        if (StringUtils.isNotEmpty(this.pojoRequestRootClass)) {
            this.pojoRequestRootClassInfo = ClassInfo.getInstance(this.pojoRequestRootClass);
        }

        return validate;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        if (this.pojoRequestRootClassInfo != null) {
            TopLevelClass pojoRequestRootClass = this.pojoRequestRootClassInfo.toTopLevelClass();
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    this.typescriptModelPackage + "." + pojoRequestRootClass.getType().getShortName());
            TypescriptTopLevelClass tsRootModelClass = new TypescriptTopLevelClass(
                    WebUtils.convertToTypescriptImportType(this.projectRootAlias, fqjt));

            convertToTypescriptTopLevelClass(pojoRequestRootClass, tsRootModelClass);

            tsRootModelClass.setWriteMode(WriteMode.SKIP_ON_EXIST);

            this.generatedFiles.add(new GeneratedTypescriptFile(tsRootModelClass,
                    context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                    new DefaultTypescriptFormatter(this.context), this.projectRootAlias));
        }
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 设置java文件不写磁盘
        topLevelClass.setWriteMode(WriteMode.NEVER);
        generatedFiles.add(generatePojoClass(topLevelClass, introspectedTable));
        return true;
    }

    private GeneratedFile generatePojoClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String modelObjectName = topLevelClass.getType().getShortNameWithoutTypeArguments();
        FullyQualifiedTypescriptType tsBaseModelJavaType = WebUtils.convertToTypescriptImportType(this.projectRootAlias,
                new FullyQualifiedTypescriptType(this.typescriptModelPackage + "." + modelObjectName));
        TypescriptTopLevelClass tsBaseModelClass = new TypescriptTopLevelClass(tsBaseModelJavaType);
        tsBaseModelClass.setWriteMode(this.writeMode == null ? WriteMode.OVER_WRITE : this.writeMode);

        tsBaseModelClass.setVisibility(JavaVisibility.PUBLIC);

        GeneratorUtils.addComment(tsBaseModelClass, topLevelClass.getDescription() + " 服务请求类");

        FullyQualifiedJavaType tsRequestJavaType = new FullyQualifiedJavaType(modelObjectName);
        TypescriptTopLevelClass tsRequestClass = new TypescriptTopLevelClass(tsRequestJavaType);

        PojoFieldsGenerator pojoFieldsGenerator = new PojoFieldsGenerator(this.context, this.codingStyle,
                this.typescriptModelPackage, "", this.typescriptModelPackage, "", tsBaseModelJavaType);

        FieldAndImports fieldAndImports = pojoFieldsGenerator.generatePojoRequest(topLevelClass, introspectedTable);
        TopLevelClass tmpRequestClass = new TopLevelClass(tsRequestJavaType);
        fieldAndImports.getFields().forEach(tmpRequestClass::addField);
        fieldAndImports.getImports().forEach(tmpRequestClass::addImportedType);

        // 补全其他字段，比如在BaseEntity父类中的
        /*
         * List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
         * columns.forEach(column -> { if (!GeneratorUtils.hasField(tmpRequestClass,
         * column.getJavaProperty())) { Field field = new Field(column.getJavaProperty(),
         * column.getFullyQualifiedJavaType());
         * field.setDescription(GeneratorUtils.getFieldDescription(column));
         * field.setAttribute(Constants.FIELD_EXT_ATTR, true);
         * GeneratorUtils.addFieldComment(field, column); tmpRequestClass.addField(field);
         * } });
         */

        convertToTypescriptTopLevelClass(tmpRequestClass, tsRequestClass);

        if (StringUtils.isNotEmpty(this.pojoRequestRootClass)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.pojoRequestRootClass);
            String fileName = WebUtils.getFileName(fqjt.getShortName());
            String tsRootClass = this.typescriptModelPackage + "." + fileName + "." + fqjt.getShortName();
            tsRequestClass.setSuperClass(tsRootClass);
            tsRequestClass.addImportedType(new FullyQualifiedTypescriptType(this.projectRootAlias, tsRootClass));
        }
        tsBaseModelClass.addImportedTypes(tsRequestClass.getImportedTypes());
        tsBaseModelClass.addInnerClass(tsRequestClass);

        introspectedTable.setAttribute(Constants.INTROSPECTED_TABLE_WRAPPER_TYPESCRIPT_CLASS, tsRequestClass);

        tsBaseModelClass.setAttribute(Constants.WEB_PROJECT_ROOT_ALIAS, this.projectRootAlias);
        tsBaseModelClass.getInnerEnums().addAll(topLevelClass.getInnerEnums());

        InitializationBlock initializationBlock = new InitializationBlock();
        initializationBlock.addBodyLine(
                String.format("export type %sPageModel = BasicFetchResult<%s>;", modelObjectName, modelObjectName));
        tsBaseModelClass
                .addImportedType(new FullyQualifiedTypescriptType("", "fe-ent-core.lib.logics.model.BasicFetchResult"));
        tsBaseModelClass.addInitializationBlock(initializationBlock);

        return new GeneratedTypescriptFile(tsBaseModelClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                new DefaultTypescriptFormatter(this.context), this.projectRootAlias);
    }

    /**
     * 转换Field类型
     * @param topLevelClass
     * @param tsRequestClass
     */
    private void convertToTypescriptTopLevelClass(TopLevelClass topLevelClass, TypescriptTopLevelClass tsRequestClass) {
        List<Field> fields = topLevelClass.getFields();
        for (Field field : fields) {
            FullyQualifiedJavaType fieldType = field.getType();
            Field pojoRequestField = new Field(field);
            if (GeneratorUtils.isRelationField(field)) {
                if (fieldType.getTypeArguments().size() > 0) {
                    // 集合类型
                    fieldType = WebUtils.convertToTypescriptImportType(this.projectRootAlias,
                            fieldType.getTypeArguments().get(0));
                    pojoRequestField.setType(fieldType);
                    tsRequestClass.addImportedType(fieldType);
                }
                else {
                    fieldType = WebUtils.convertToTypescriptImportType(this.projectRootAlias, fieldType);
                    pojoRequestField.setType(fieldType);
                    tsRequestClass.addImportedType(fieldType);
                }
            }
            else if (GeneratorUtils.isInnerEnum(field)) {
                // 转换成TypeScriptJavaType，这个的field type 还是 java形式的
                FullyQualifiedJavaType fqjt = field.getType();
                String hostFileName = WebUtils.getFileName(StringUtils.substringAfterLast(fqjt.getPackageName(), "."));

                pojoRequestField.setType(new FullyQualifiedTypescriptType(this.projectRootAlias,
                        this.typescriptModelPackage + "." + hostFileName + "." + fqjt.getShortName()));
            }
            else {
                // 判断是否需要生成Field对应的class文件
                String javaType = fieldType.getFullyQualifiedNameWithoutTypeParameters();
                if (!StringUtils.startsWith(javaType, "java")) {
                    ClassInfo classInfo = ClassInfo.getInstance(javaType);
                    if (classInfo != null) {
                        if (classInfo.isEnum()) {
                            TopLevelEnumeration topLevelEnumeration = classInfo
                                    .toTopLevelEnumeration(this.enumTargetPackage);
                            FullyQualifiedJavaType fqjt = topLevelEnumeration.getType();
                            fieldType = new FullyQualifiedTypescriptType(this.projectRootAlias, fqjt.getPackageName()
                                    + "." + WebUtils.getFileName(fqjt.getShortName()) + "." + fqjt.getShortName());
                            pojoRequestField.setType(fieldType);
                            pojoRequestField.setAttribute(Constants.TABLE_ENUM_FIELD_ATTR, topLevelEnumeration);
                            this.generatedFiles.add(new GeneratedTypescriptFile(topLevelEnumeration,
                                    context.getJavaModelGeneratorConfiguration().getTargetProject(),
                                    context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                    new DefaultTypescriptFormatter(this.context), this.projectRootAlias));
                        }
                    }
                    else {
                        fieldType = FullyQualifiedJavaType.getObjectInstance();
                        pojoRequestField.setType(fieldType);
                    }
                }

                tsRequestClass.addImportedType(fieldType);
            }

            pojoRequestField.setVisibility(JavaVisibility.PRIVATE);
            tsRequestClass.addField(pojoRequestField);
        }
    }

    public List<GeneratedFile> contextGenerateAdditionalFiles() {
        return generatedFiles;
    }

}

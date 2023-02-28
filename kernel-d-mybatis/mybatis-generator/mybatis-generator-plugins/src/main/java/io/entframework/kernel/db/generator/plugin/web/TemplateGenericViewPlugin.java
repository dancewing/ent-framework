package io.entframework.kernel.db.generator.plugin.web;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.web.runtime.ModelObject;
import io.entframework.kernel.db.generator.plugin.web.runtime.TemplateGeneratedFile;
import io.entframework.kernel.db.generator.plugin.web.runtime.TypescriptTopLevelClass;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有数据数据模型构建完成后
 * 通常根据所有的数据模型，生成路由信息等操作
 */
public class TemplateGenericViewPlugin extends AbstractTemplatePlugin {

    private GeneratedFile generateModelClass() {

        FullyQualifiedJavaType tsBaseModelJavaType = new FullyQualifiedJavaType(this.targetPackage  + "." + this.fileName);
        TypescriptTopLevelClass tsGenericClass = new TypescriptTopLevelClass(tsBaseModelJavaType);

        tsGenericClass.setWriteMode(this.writeMode == null ? WriteMode.SKIP_ON_EXIST : this.writeMode);

        Map<String, Object> data = new HashMap<>();
        data.put("projectRootAlias", this.projectRootAlias);
        data.put("modelPath", this.modelPath);
        data.putAll(getAdditionalPropertyMap());

        List<ModelObject> modelObjects = new ArrayList<>();

        List<IntrospectedTable> tables = this.context.getIntrospectedTables();
        for (IntrospectedTable table : tables) {
            TopLevelClass topLevelClass = (TopLevelClass) table.getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
            if (topLevelClass != null) {
                ModelObject.Builder builder = ModelObject.builder();
                String modelObjectName = topLevelClass.getType().getShortName();
                builder.name(modelObjectName)
                        .camelModelName(JavaBeansUtil.convertCamelCase(modelObjectName, "-"))
                        .description(topLevelClass.getDescription())
                        .type(modelObjectName);
                modelObjects.add(builder.build());
            }
        }

        data.put("models", modelObjects);

        return new TemplateGeneratedFile(tsGenericClass,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                data,
                this.templatePath,
                this.fileName,
                this.fileExt);
    }

    @Override
    public List<GeneratedFile> contextGenerateAdditionalFiles() {
        List<GeneratedFile> generatedFiles = new ArrayList<>();
        generatedFiles.add(generateModelClass());
        return generatedFiles;
    }
}

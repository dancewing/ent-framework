package io.entframework.kernel.db.generator.plugin;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.server.AbstractServerPlugin;
import io.entframework.kernel.db.generator.utils.EnumInfo;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * type or status enum 插件
 */
public class EnumTypeStatusPlugin extends AbstractServerPlugin {

    /**
     * 自动扫描
     */
    private final static String PRO_AUTO_SCAN = "autoScan";

    /**
     * 需要生成Enum的Column
     */
    public final static String PRO_ENUM_COLUMNS = "enumColumns";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        Map<String, EnumInfo> enumColumnsMap = new LinkedHashMap<>();
        String autoScan = this.properties.getProperty(PRO_AUTO_SCAN);
        // 是否开启了自动扫描
        if (StringUtility.stringHasValue(autoScan) && !StringUtility.isTrue(autoScan)) {
            // 获取全局配置
            String enumColumns = this.properties.getProperty(EnumTypeStatusPlugin.PRO_ENUM_COLUMNS);
            // 如果有局部配置，则附加上去
            String tableEnumColumns = introspectedTable
                    .getTableConfigurationProperty(EnumTypeStatusPlugin.PRO_ENUM_COLUMNS);
            if (tableEnumColumns != null) {
                enumColumns = enumColumns == null ? "" : (enumColumns + ",");
                enumColumns += introspectedTable.getTableConfigurationProperty(EnumTypeStatusPlugin.PRO_ENUM_COLUMNS);
            }

            if (StringUtility.stringHasValue(enumColumns)) {
                // 切分
                String[] enumColumnsStrs = enumColumns.split(",");
                for (String enumColumnsStr : enumColumnsStrs) {
                    IntrospectedColumn column = GeneratorUtils.safeGetIntrospectedColumnByColumn(introspectedTable,
                            enumColumnsStr);
                    if (column != null) {
                        try {
                            EnumInfo enumInfo = new EnumInfo(column);
                            // 解析注释
                            enumInfo.parseRemarks();
                            if (enumInfo.hasItems()) {
                                enumColumnsMap.put(column.getJavaProperty(), enumInfo);
                            }
                        }
                        catch (EnumInfo.CannotParseException e) {
                        }
                        catch (EnumInfo.NotSupportTypeException e) {
                        }
                    }
                }
            }
        }
        else {
            for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
                try {
                    EnumInfo enumInfo = new EnumInfo(column);
                    // 解析注释
                    enumInfo.parseRemarks();
                    if (enumInfo.hasItems()) {
                        enumColumnsMap.put(column.getJavaProperty(), enumInfo);
                    }
                }
                catch (Exception e) {
                    // nothing
                }
            }
        }
        introspectedTable.setAttribute(Constants.TABLE_ENUM_COLUMN_ATTR, enumColumnsMap);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        Map<String, EnumInfo> enumColumnsMap = (Map<String, EnumInfo>) introspectedTable
                .getAttribute(Constants.TABLE_ENUM_COLUMN_ATTR);
        if (enumColumnsMap != null && enumColumnsMap.get(field.getName()) != null) {
            EnumInfo enumInfo = enumColumnsMap.get(field.getName());
            TopLevelEnumeration innerEnum = enumInfo.generateEnum(topLevelClass);
            // 重置FieldType
            field.setType(innerEnum.getType());
            field.setDescription(enumInfo.getDescription());

            FullyQualifiedJavaType columnJavaType = new FullyQualifiedJavaType(
                    topLevelClass.getType().getFullyQualifiedName() + "." + innerEnum.getType());
            introspectedColumn.setFullyQualifiedJavaType(columnJavaType);

            innerEnum.setAttribute(Constants.TABLE_ENUM_FIELD_ATTR_SOURCE, enumInfo);

            field.setAttribute(Constants.TABLE_ENUM_FIELD_ATTR, innerEnum);

            topLevelClass.addInnerEnum(innerEnum);
        }
        return true;
    }

}

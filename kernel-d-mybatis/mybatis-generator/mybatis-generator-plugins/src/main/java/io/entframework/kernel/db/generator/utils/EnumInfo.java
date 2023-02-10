package io.entframework.kernel.db.generator.utils;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumInfo {

    private final static Class<?> ENUM_HANDLER = EnumHandler.class;
    private final static Class<?> ENUM_VALUE = EnumValue.class;

    private final static String REMARKS_REGEX = "([\\u4e00-\\u9fa5a-zA-Z0-9]+)\\[([a-zA-Z0-9]+\\(\\S*\\):\\S*\\,?\\s*)*\\]";

    private final static String ITEM_REGEX = "(\\w+)\\s*\\(\\s*([\\u4e00-\\u9fa5_\\-a-zA-Z0-9]+)\\s*\\)\\s*:\\s*([\\u4e00-\\u9fa5_\\-a-zA-Z0-9]+)";
    private final static Pattern REMARKS_PATTERN = Pattern.compile(REMARKS_REGEX);

    private final List<EnumItemInfo> items = new ArrayList<>();
    private final IntrospectedColumn column;
    /**
     * 描述字段
     */
    private String description = "";

    public EnumInfo() {
        this.column = null;
    }

    public EnumInfo(IntrospectedColumn column) throws NotSupportTypeException, CannotParseException {
        String javaType = column.getFullyQualifiedJavaType().getFullyQualifiedName();
        if (!(Short.class.getTypeName().equals(javaType)
                || Integer.class.getTypeName().equals(javaType)
                || Long.class.getTypeName().equals(javaType)
                || Boolean.class.getTypeName().equals(javaType)
                || Double.class.getTypeName().equals(javaType)
                || Float.class.getTypeName().equals(javaType)
                || BigDecimal.class.getTypeName().equals(javaType)
                || Byte.class.getTypeName().equals(javaType)
                || String.class.getTypeName().equals(javaType))) {
            throw new NotSupportTypeException();
        } else {
            this.column = column;
        }
    }

    /**
     * 添加Enum Item
     */
    public void addItem(String name, String comment, Object value) {
        items.add(new EnumItemInfo(this.column, name, comment, value));
    }

    /**
     * 判断是否有节点
     */
    public boolean hasItems() {
        return items.size() > 0;
    }

    /**
     * 解析注释
     */
    public void parseRemarks() throws CannotParseException {
        String remarks = this.column.getRemarks();
        if (StringUtility.stringHasValue(remarks) && remarks.matches(REMARKS_REGEX)) {
            // 提取信息
            Matcher matcher = REMARKS_PATTERN.matcher(remarks);
            if (matcher.find() && matcher.groupCount() == 2) {
                this.description = matcher.group(1);
                String enumInfoStr = matcher.group(2);
                // 根据逗号切分
                String[] enumInfoStrs = enumInfoStr.split(",");

                Pattern pattern;
                //提取每个节点信息
                for (String enumInfoItemStr : enumInfoStrs) {
                    pattern = Pattern.compile(ITEM_REGEX);
                    matcher = pattern.matcher(enumInfoItemStr.trim());
                    if (matcher.find() && matcher.groupCount() == 3) {
                        this.addItem(matcher.group(1), matcher.group(3), matcher.group(2));
                    }
                }
            }
        }
    }

    /**
     * Getter method for property <tt>items</tt>.
     *
     * @return property value of items
     * @author hewei
     */
    public List<EnumItemInfo> getItems() {
        return items;
    }

    public static class NotSupportTypeException extends Exception {
    }

    public static class CannotParseException extends Exception {
    }

    public String getDescription() {
        return description;
    }

    public TopLevelEnumeration generateEnum(TopLevelClass topLevelClass) {
        String enumName = StringUtils.capitalize(column.getJavaProperty());
        TopLevelEnumeration innerEnum = new TopLevelEnumeration(new FullyQualifiedJavaType(enumName));
        innerEnum.setVisibility(JavaVisibility.PUBLIC);
        // 生成枚举
        for (EnumItemInfo item : this.items) {
            innerEnum.addEnumConstant(item.getConstant() + "(" + item.getValue() + ", \"" + item.getLabel() + "\")");
        }
        innerEnum.addAnnotation("@EnumHandler");
        topLevelClass.addImportedType(ENUM_HANDLER.getName());

        // 生成属性和构造函数
        Field fValue = new Field("value", column.getFullyQualifiedJavaType());
        fValue.setVisibility(JavaVisibility.PRIVATE);
        fValue.setFinal(true);
        fValue.addAnnotation("@JsonValue");
        fValue.addAnnotation("@EnumValue");
        topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonValue");
        topLevelClass.addImportedType(ENUM_VALUE.getName());

        innerEnum.addField(fValue);

        Field fName = new Field("label", FullyQualifiedJavaType.getStringInstance());
        fName.setVisibility(JavaVisibility.PRIVATE);
        fName.setFinal(true);
        innerEnum.addField(fName);

        Method mInc = new Method(enumName);
        mInc.setConstructor(true);
        mInc.addBodyLine("this.value = value;");
        mInc.addBodyLine("this.label = label;");
        mInc.addParameter(new Parameter(fValue.getType(), "value"));
        mInc.addParameter(new Parameter(fName.getType(), "label"));
        FormatTools.addMethodWithBestPosition(innerEnum, mInc);

        // 获取value的方法
        Method mValue = JavaElementGeneratorTools.generateGetterMethod(fValue);
        FormatTools.addMethodWithBestPosition(innerEnum, mValue);

        Method mValue1 = JavaElementGeneratorTools.generateGetterMethod(fValue);
        mValue1.setName("value");
        FormatTools.addMethodWithBestPosition(innerEnum, mValue1);

        // 获取name的方法
        Method mName = JavaElementGeneratorTools.generateGetterMethod(fName);
        FormatTools.addMethodWithBestPosition(innerEnum, mName);

        // parseValue 方法
        Method mParseValue = JavaElementGeneratorTools.generateMethod(
                "parseValue",
                JavaVisibility.PUBLIC,
                innerEnum.getType(),
                new Parameter(fValue.getType(), "value")
        );
        mParseValue.addAnnotation("@JsonCreator");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.fasterxml.jackson.annotation.JsonCreator"));
        mParseValue.setStatic(true);
        mParseValue.addBodyLine("if (value != null) {");
        mParseValue.addBodyLine("for (" + innerEnum.getType().getShortName() + " item : values()) {");
        mParseValue.addBodyLine("if (item.value.equals(value)) {");
        mParseValue.addBodyLine("return item;");
        mParseValue.addBodyLine("}");
        mParseValue.addBodyLine("}");
        mParseValue.addBodyLine("}");
        mParseValue.addBodyLine("return null;");
        FormatTools.addMethodWithBestPosition(innerEnum, mParseValue);

        return innerEnum;
    }


    public static class EnumItemInfo {
        private final IntrospectedColumn column;
        private final String constant;
        private final String label;
        private final Object value;

        public EnumItemInfo(IntrospectedColumn column, String name, String label, Object value) {
            this.column = column;
            this.constant = name.trim();
            this.label = label.trim();
            this.value = value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getConstant() {
            return constant.toUpperCase();
        }

        public String getValue() {
            String javaType = this.column.getFullyQualifiedJavaType().getShortName();

            if (Objects.isNull(value) || "NULL".equalsIgnoreCase(String.valueOf(value))) {
                return "null";
            } else if ("String".equals(javaType)) {
                return "\"" + value + "\"";
            } else {
                return "new " + javaType + "(\"" + value + "\")";
            }
        }

        /**
         * Getter method for property <tt>value</tt>.
         *
         * @return property value of value
         * @author hewei
         */
        public Object getOriginalValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        String remarks = "注释[success(0):成功,,fail(1):失败,,,fdd(3):失败]";
        Matcher matcher = REMARKS_PATTERN.matcher(remarks);
        System.out.println(matcher.groupCount());
    }
}

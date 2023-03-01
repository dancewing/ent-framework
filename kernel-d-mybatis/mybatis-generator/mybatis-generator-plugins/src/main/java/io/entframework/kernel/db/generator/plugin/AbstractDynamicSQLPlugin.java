/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.List;

/**
 * <a href="http://www.mybatis.org/generator/reference/pluggingIn.html">Plugin
 * Lifecycle</a>
 */
public abstract class AbstractDynamicSQLPlugin extends PluginAdapter {

    protected Log log;

    protected WriteMode writeMode;

    protected WriteMode convert(String mode) {
        WriteMode[] vals = WriteMode.values();
        for (WriteMode m : vals) {
            if (StringUtils.equals(mode, m.name())) {
                return m;
            }
        }
        return null;
    }

    protected AbstractDynamicSQLPlugin() {
        log = LogFactory.getLog(AbstractDynamicSQLPlugin.class);
    }

    protected void doIfNullCheck(String fieldPrefix, boolean ifNullCheck, XmlElement trimElement, StringBuilder sb,
            IntrospectedColumn introspectedColumn) {
        VisitableElement content;
        if (ifNullCheck) {
            content = wrapIfNullCheckForJavaProperty(fieldPrefix, new TextElement(sb.toString()), introspectedColumn);
        }
        else {
            content = new TextElement(sb.toString());
        }
        trimElement.addElement(content);
    }

    protected XmlElement wrapIfNullCheckForJavaProperty(String fieldPrefix, TextElement content,
            IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        XmlElement isNotNullElement = new XmlElement("if");
        sb.append(introspectedColumn.getJavaProperty(fieldPrefix));
        sb.append(" != null");
        isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
        isNotNullElement.addElement(content);
        return isNotNullElement;
    }

    protected void generateParametersSeparateByComma(String fieldPrefix, boolean ifNullCheck, boolean withParenthesis,
            List<IntrospectedColumn> columns, XmlElement parent) {
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));
        if (withParenthesis) {
            trimElement.addAttribute(new Attribute("prefix", "("));
            trimElement.addAttribute(new Attribute("suffix", ")"));
        }

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, fieldPrefix));
            sb.append(",");

            doIfNullCheck(fieldPrefix, ifNullCheck, trimElement, sb, introspectedColumn);
        }
        parent.addElement(trimElement);
    }

    protected void generateParametersSeparateByCommaWithParenthesis(String fieldPrefix,
            List<IntrospectedColumn> columns, XmlElement parent) {
        generateParametersSeparateByCommaWithParenthesis(fieldPrefix, false, columns, parent);
    }

    protected void generateParametersSeparateByCommaWithParenthesis(String fieldPrefix, boolean ifNullCheck,
            List<IntrospectedColumn> columns, XmlElement parent) {
        generateParametersSeparateByComma(fieldPrefix, ifNullCheck, true, columns, parent);
    }

    protected void generateActualColumnNamesWithParenthesis(List<IntrospectedColumn> columns, XmlElement parent) {
        generateActualColumnNamesWithParenthesis("", false, columns, parent);
    }

    protected void generateActualColumnNamesWithParenthesis(String fieldPrefix, boolean ifNullCheck,
            List<IntrospectedColumn> columns, XmlElement parent) {
        generateActualColumnNamesWithParenthesis(fieldPrefix, null, ifNullCheck, columns, parent);
    }

    protected void generateActualColumnNamesWithParenthesis(String fieldPrefix, String columnPrefix,
            boolean ifNullCheck, List<IntrospectedColumn> columns, XmlElement parent) {
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));
        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("suffix", ")"));

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            sb.setLength(0);
            sb.append(columnPrefix == null ? "" : columnPrefix);
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(",");

            doIfNullCheck(fieldPrefix, ifNullCheck, trimElement, sb, introspectedColumn);
        }

        parent.addElement(trimElement);
    }

    protected void generateWhereConditions(String fieldPrefix, List<IntrospectedColumn> columns, XmlElement parent) {
        generateWhereConditions(fieldPrefix, false, columns, parent);
    }

    protected void generateWhereConditions(String fieldPrefix, boolean ifNullCheck, List<IntrospectedColumn> columns,
            XmlElement parent) {
        generateWhereConditions(fieldPrefix, null, ifNullCheck, columns, parent);
    }

    protected void generateWhereConditions(String fieldPrefix, String columnPrefix, boolean ifNullCheck,
            List<IntrospectedColumn> columns, XmlElement parent) {
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            sb.setLength(0);
            sb.append(columnPrefix == null ? "" : columnPrefix);
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, fieldPrefix));
            sb.append(",");

            doIfNullCheck(fieldPrefix, ifNullCheck, trimElement, sb, introspectedColumn);
        }

        XmlElement where = new XmlElement("where");
        where.addElement(trimElement);
        parent.addElement(where);
    }

    public String upperCaseFirstChar(final String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }

    public String lowerCaseFirstChar(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

    public String camelName(String name) {
        StringBuilder result = new StringBuilder();
        if (name == null || name.isEmpty()) {
            return "";
        }
        else if (!name.contains("_")) {
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        String[] camels = name.split("_");
        for (String camel : camels) {
            if (camel.isEmpty()) {
                continue;
            }
            if (result.length() == 0) {
                result.append(camel.toLowerCase());
            }
            else {
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

}

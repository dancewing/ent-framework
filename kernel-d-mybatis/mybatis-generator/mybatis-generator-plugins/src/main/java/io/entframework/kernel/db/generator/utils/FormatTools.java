/*
 * ******************************************************************************
 *  * Copyright (c) 2017-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.utils;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * --------------------------------------------------------------------------- 格式化工具，优化输出
 * ---------------------------------------------------------------------------
 *
 * @author: hewei
 * @time:2017/6/30 10:53
 * ---------------------------------------------------------------------------
 */
public class FormatTools {

	/**
	 * 在最佳位置添加方法
	 * @param innerEnum
	 * @param method
	 */
	public static void addMethodWithBestPosition(InnerEnum innerEnum, Method method) {
		addMethodWithBestPosition(method, innerEnum.getMethods());
	}

	/**
	 * 找出节点ID值
	 * @param element
	 * @return
	 */
	private static String getIdFromElement(XmlElement element) {
		for (Attribute attribute : element.getAttributes()) {
			if ("id".equals(attribute.getName())) {
				return attribute.getValue();
			}
		}
		return null;
	}

	/**
	 * 获取最佳添加位置
	 * @param method
	 * @param methods
	 * @return
	 */
	private static void addMethodWithBestPosition(Method method, List<Method> methods) {
		int index = -1;
		for (int i = 0; i < methods.size(); i++) {
			Method m = methods.get(i);
			if (m.getName().equals(method.getName())) {
				if (m.getParameters().size() <= method.getParameters().size()) {
					index = i + 1;
				}
				else {
					index = i;
				}
			}
			else if (m.getName().startsWith(method.getName())) {
				if (index == -1) {
					index = i;
				}
			}
			else if (method.getName().startsWith(m.getName())) {
				index = i + 1;
			}
		}
		if (index == -1 || index >= methods.size()) {
			methods.add(methods.size(), method);
		}
		else {
			methods.add(index, method);
		}
	}

	/**
	 * 替换已有方法注释
	 * @param commentGenerator
	 * @param method
	 * @param introspectedTable
	 */
	public static void replaceGeneralMethodComment(CommentGenerator commentGenerator, Method method,
			IntrospectedTable introspectedTable) {
		method.getJavaDocLines().clear();
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
	}

}

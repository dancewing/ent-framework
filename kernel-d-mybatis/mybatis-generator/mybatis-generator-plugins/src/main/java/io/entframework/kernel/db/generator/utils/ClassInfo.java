/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.utils;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.web.runtime.FullyQualifiedTypescriptType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.TopLevelEnumeration;
import org.mybatis.generator.internal.ObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ClassInfo {

	private static final Map<String, ClassInfo> ROOT_CLASS_INFO_MAP;

	private static Class<?> supperEnum = null;

	static {
		ROOT_CLASS_INFO_MAP = Collections.synchronizedMap(new HashMap<>());
		try {
			supperEnum = ObjectFactory.externalClassForName("io.entframework.kernel.rule.enums.SupperEnum");
		}
		catch (Exception e) {
			// ignore
		}
	}

	public static ClassInfo getInstance(String className) {
		return ROOT_CLASS_INFO_MAP.computeIfAbsent(className, ClassInfo::parse);
	}

	/**
	 * Clears the internal map containing root class info. This method should be called at
	 * the beginning of a generation run to clear the cached root class info in case there
	 * has been a change. For example, when using the eclipse launcher, the cache would be
	 * kept until eclipse was restarted.
	 *
	 */
	public static void reset() {
		ROOT_CLASS_INFO_MAP.clear();
	}

	private FullyQualifiedJavaType javaType;

	private boolean genericMode = false;

	private boolean isEnum = false;

	private List<Field> enuConstants = new ArrayList<>();

	private List<Field> memberFields = new ArrayList<>();

	public boolean isEnum() {
		return isEnum;
	}

	private static ClassInfo parse(String className) {

		ClassInfo classInfo = new ClassInfo(className);

		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(className);
		String nameWithoutGenerics = fqjt.getFullyQualifiedNameWithoutTypeParameters();
		if (!nameWithoutGenerics.equals(className)) {
			classInfo.genericMode = true;
		}
		classInfo.javaType = fqjt;
		Class<?> clazz = null;
		try {
			clazz = ObjectFactory.externalClassForName(nameWithoutGenerics);
		}
		catch (Exception ex) {
			// ignore
		}
		if (clazz != null) {
			if (clazz.isEnum() && supperEnum != null && supperEnum.isAssignableFrom(clazz)) {
				classInfo.isEnum = true;
				parseEnum(classInfo, clazz);
			}
			else {
				parseClass(classInfo, clazz);
			}
		}

		return classInfo;
	}

	private static void parseClass(ClassInfo classInfo, Class<?> clazz) {
		List<java.lang.reflect.Field> fields = FieldUtils.getAllFieldsList(clazz);
		fields.forEach(field -> {
			if (!Modifier.isStatic(field.getModifiers())) {
				classInfo.memberFields.add(field);
			}
		});
	}

	private static void parseEnum(ClassInfo classInfo, Class<?> clazz) {
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		Arrays.asList(fields).forEach(field -> {
			if (field.isEnumConstant()) {
				classInfo.enuConstants.add(field);
			}
			else if (!Modifier.isStatic(field.getModifiers())) {
				classInfo.memberFields.add(field);
			}
		});
	}

	private ClassInfo(String className) {
		super();
		this.javaType = new FullyQualifiedTypescriptType(className);
	}

	public TopLevelClass toTopLevelClass() {
		TopLevelClass topLevelClass = new TopLevelClass(javaType);
		if (this.memberFields != null) {
			for (Field field : this.memberFields) {
				FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(field.getType().getName());
				org.mybatis.generator.api.dom.java.Field javaField = new org.mybatis.generator.api.dom.java.Field(
						field.getName(), fqjt);
				topLevelClass.addField(javaField);
				topLevelClass.addImportedType(fqjt);
			}
		}
		return topLevelClass;
	}

	public TopLevelEnumeration toTopLevelEnumeration(String enumTargetPackage) {
		TopLevelEnumeration topLevelEnumeration = new TopLevelEnumeration(
				enumTargetPackage + "." + this.javaType.getShortName());
		if (this.enuConstants != null) {
			EnumInfo enumInfo = new EnumInfo();
			for (Field field : this.enuConstants) {
				try {
					Object enumValue = field.get(null);
					if (enumValue != null) {
						Method nameField = enumValue.getClass().getMethod("name");
						Method labelField = enumValue.getClass().getMethod("getLabel");
						Method valueField = enumValue.getClass().getMethod("getValue");
						enumInfo.addItem((String) nameField.invoke(enumValue), (String) labelField.invoke(enumValue),
								valueField.invoke(enumValue));
					}
				}
				catch (Exception ex) {

				}

			}
			topLevelEnumeration.setAttribute(Constants.TABLE_ENUM_FIELD_ATTR_SOURCE, enumInfo);
			for (Field field : this.memberFields) {
				FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(field.getType().getName());
				org.mybatis.generator.api.dom.java.Field javaField = new org.mybatis.generator.api.dom.java.Field(
						field.getName(), fqjt);
				topLevelEnumeration.addField(javaField);
				topLevelEnumeration.addImportedType(fqjt);
			}
		}
		return topLevelEnumeration;
	}

	public static void main(String[] args) {
		ClassInfo classInfo = ClassInfo.getInstance("io.entframework.kernel.rule.enums.StatusEnum");
		TopLevelEnumeration topLevelEnumeration = classInfo.toTopLevelEnumeration("");
		ClassInfo classInfo2 = ClassInfo.getInstance("io.entframework.kernel.rule.pojo.request.BaseRequest");
		classInfo2.toTopLevelClass();
	}

}

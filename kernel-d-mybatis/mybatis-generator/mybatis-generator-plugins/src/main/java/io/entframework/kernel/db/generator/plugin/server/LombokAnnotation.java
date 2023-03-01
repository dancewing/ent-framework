package io.entframework.kernel.db.generator.plugin.server;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.Collection;
import java.util.Collections;

public enum LombokAnnotation {

	// lombok annotations
	GETTER("getter", "@Getter", "lombok.Getter"), SETTER("setter", "@Setter", "lombok.Setter"),
	DATA("data", "@Data", "lombok.Data"),
	EqualsAndHashCode("equalsAndHashCode", "@EqualsAndHashCode(callSuper = true)", "lombok.EqualsAndHashCode"),
	BUILDER("builder", "@Builder(builderClassName = \"Builder\")", "lombok.Builder"),
	ALL_ARGS_CONSTRUCTOR("allArgsConstructor", "@AllArgsConstructor", "lombok.AllArgsConstructor"),
	NO_ARGS_CONSTRUCTOR("noArgsConstructor", "@NoArgsConstructor", "lombok.NoArgsConstructor"),
	TO_STRING("toString", "@ToString", "lombok.ToString"), SLF4J("Slf4j", "@Slf4j", "lombok.extern.slf4j.Slf4j"),;

	private final String paramName;

	private final String name;

	private final FullyQualifiedJavaType javaType;

	LombokAnnotation(String paramName, String name, String className) {
		this.paramName = paramName;
		this.name = name;
		this.javaType = new FullyQualifiedJavaType(className);
	}

	public static LombokAnnotation getValueOf(String paramName) {
		for (LombokAnnotation annotation : LombokAnnotation.values()) {
			if (String.CASE_INSENSITIVE_ORDER.compare(paramName, annotation.paramName) == 0) {
				return annotation;
			}
		}
		return null;
	}

	public static Collection<LombokAnnotation> getDependencies(LombokAnnotation annotation) {
		if (annotation == ALL_ARGS_CONSTRUCTOR) {
			return Collections.singleton(NO_ARGS_CONSTRUCTOR);
		}
		else {
			return Collections.emptyList();
		}
	}

	public String getParamName() {
		return paramName;
	}

	public String getName() {
		return name;
	}

	public FullyQualifiedJavaType getJavaType() {
		return javaType;
	}

}

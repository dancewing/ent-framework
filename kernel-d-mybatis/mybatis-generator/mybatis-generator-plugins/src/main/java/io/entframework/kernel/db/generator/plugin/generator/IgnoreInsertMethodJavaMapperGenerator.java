package io.entframework.kernel.db.generator.plugin.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JoinEntry;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodParts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IgnoreInsertMethodJavaMapperGenerator extends AbstractJavaMapperMethodGenerator {

	private static final String MYBATIS3_CUSTOM_UTILS = "io.entframework.kernel.db.mybatis.util.MyBatis3CustomUtils";

	private final String tableFieldName;

	private final FragmentGenerator fragmentGenerator;

	private final FullyQualifiedJavaType recordType;

	private final String resultMapId;

	public IgnoreInsertMethodJavaMapperGenerator(Context context, IntrospectedTable table) {
		this.context = context;
		this.introspectedTable = table;
		this.progressCallback = new VerboseProgressCallback();
		this.warnings = new ArrayList<>();
		this.resultMapId = JoinEntry
				.getJoinResultMapId(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
		this.tableFieldName = JavaBeansUtil
				.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
		this.recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		this.fragmentGenerator = new FragmentGenerator.Builder().withIntrospectedTable(introspectedTable)
				.withResultMapId(resultMapId).withTableFieldName(tableFieldName).build();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		MethodAndImports ignoreInsertMethodAndImports = generateIgnoreInsertMethodAndImports();
		if (ignoreInsertMethodAndImports != null) {
			interfaze.addMethod(ignoreInsertMethodAndImports.getMethod());
			interfaze.addImportedTypes(ignoreInsertMethodAndImports.getImports());
			interfaze.addStaticImports(ignoreInsertMethodAndImports.getStaticImports());
		}
		MethodAndImports defaultIgnoreInsertMethodAndImports = generateDefaultIgnoreInsertMethodAndImports();
		if (defaultIgnoreInsertMethodAndImports != null) {
			interfaze.addMethod(defaultIgnoreInsertMethodAndImports.getMethod());
			interfaze.addImportedTypes(defaultIgnoreInsertMethodAndImports.getImports());
			interfaze.addStaticImports(defaultIgnoreInsertMethodAndImports.getStaticImports());
		}
	}

	/**
	 * 添加leftJoinSelectOne方法
	 * @return
	 */
	private MethodAndImports generateIgnoreInsertMethodAndImports() {
		if (!introspectedTable.getRules().generateSelectByExampleWithBLOBs()
				&& !introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
			return null;
		}

		Set<FullyQualifiedJavaType> imports = new HashSet<>();

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(
				"org.mybatis.dynamic.sql.insert.render.InsertStatementProvider");
		parameterType.addTypeArgument(recordType);
		FullyQualifiedJavaType adapter = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.SqlProviderAdapter");
		FullyQualifiedJavaType annotation = new FullyQualifiedJavaType("org.apache.ibatis.annotations.InsertProvider");
		FullyQualifiedJavaType options = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options");

		imports.add(parameterType);
		imports.add(adapter);
		imports.add(annotation);
		imports.add(options);
		imports.add(FullyQualifiedJavaType.getNewListInstance());
		imports.add(recordType);

		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		returnType.addTypeArgument(recordType);

		Method method = new Method("ignoreInsert");
		method.setAbstract(true);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(parameterType, "insertStatement"));
		context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);

		method.addAnnotation("@InsertProvider(type = SqlProviderAdapter.class, method = \"insert\")");
		if (introspectedTable.getGeneratedKey().isPresent()) {
			method.addAnnotation("@Options(useGeneratedKeys = true, keyProperty = \"row.id\")");
		}
		else {
			method.addAnnotation("@Options(useGeneratedKeys = false, keyProperty = \"row.id\")");
		}

		MethodAndImports.Builder builder = MethodAndImports.withMethod(method).withImports(imports);
		if (introspectedTable.isConstructorBased()) {
			MethodParts methodParts = fragmentGenerator.getAnnotatedConstructorArgs();
			acceptParts(builder, method, methodParts);
		}
		return builder.build();
	}

	/**
	 * 添加leftJoinSelectOne方法
	 * @return
	 */
	private MethodAndImports generateDefaultIgnoreInsertMethodAndImports() {
		Set<FullyQualifiedJavaType> imports = new HashSet<>();

		boolean reuseResultMap = introspectedTable.getRules().generateSelectByExampleWithBLOBs()
				|| introspectedTable.getRules().generateSelectByExampleWithoutBLOBs();

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(MYBATIS3_CUSTOM_UTILS);
		returnType.addTypeArgument(recordType);

		imports.add(returnType);
		imports.add(recordType);

		Method method = new Method("ignoreInsert");
		method.setDefault(true);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(recordType, "row"));
		context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
		MethodAndImports.Builder builder = MethodAndImports.withMethod(method).withImports(imports);

		builder.withImport(new FullyQualifiedJavaType("io.entframework.kernel.db.mybatis.util.MyBatis3CustomUtils"));
		method.addBodyLine("return MyBatis3CustomUtils.ignoreInsert(this::ignoreInsert, row, " + tableFieldName //$NON-NLS-1$
				+ ", c ->"); //$NON-NLS-1$

		List<IntrospectedColumn> columns = ListUtilities
				.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
		boolean first = true;
		for (IntrospectedColumn column : columns) {
			String fieldName = column.getJavaProperty();
			if (column.isSequenceColumn()) {
				if (first) {
					method.addBodyLine("    c.map(" + fieldName //$NON-NLS-1$
							+ ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
							+ "\")"); //$NON-NLS-1$
					first = false;
				}
				else {
					method.addBodyLine("    .map(" + fieldName //$NON-NLS-1$
							+ ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
							+ "\")"); //$NON-NLS-1$
				}
			}
			else {
				String methodName = JavaBeansUtil.getGetterMethodName(column.getJavaProperty(),
						column.getFullyQualifiedJavaType());
				if (first) {
					method.addBodyLine("    c.map(" + fieldName //$NON-NLS-1$
							+ ").toPropertyWhenPresent(\"" + column.getJavaProperty() //$NON-NLS-1$
							+ "\", row::" + methodName //$NON-NLS-1$
							+ ")"); //$NON-NLS-1$
					first = false;
				}
				else {
					method.addBodyLine("    .map(" + fieldName //$NON-NLS-1$
							+ ").toPropertyWhenPresent(\"" + column.getJavaProperty() //$NON-NLS-1$
							+ "\", row::" + methodName //$NON-NLS-1$
							+ ")"); //$NON-NLS-1$
				}
			}
		}

		method.addBodyLine(");"); //$NON-NLS-1$

		return builder.build();
	}

	private void acceptParts(MethodAndImports.Builder builder, Method method, MethodParts methodParts) {
		for (Parameter parameter : methodParts.getParameters()) {
			method.addParameter(parameter);
		}

		for (String annotation : methodParts.getAnnotations()) {
			method.addAnnotation(annotation);
		}

		method.addBodyLines(methodParts.getBodyLines());
		builder.withImports(methodParts.getImports());
	}

}

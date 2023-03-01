package io.entframework.kernel.db.generator.utils;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.config.Relation;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import io.entframework.kernel.db.generator.plugin.web.runtime.FullyQualifiedTypescriptType;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.JoinTarget;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WebUtils {

	public static String getFileName(TopLevelClass topLevelClass) {
		String shortName = topLevelClass.getType().getShortNameWithoutTypeArguments();
		return getFileName(shortName); // $NON-NLS-1$
	}

	public static String getFileName(String shortName) {
		return JavaBeansUtil.getValidPropertyName(shortName);
		// return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, shortName);
		// //$NON-NLS-1$
	}

	public static FullyQualifiedTypescriptType convertToTypescriptImportType(String projectRootAlias,
			FullyQualifiedJavaType type) {
		String shortName = type.getShortName();
		String packageName = type.getPackageName();
		return new FullyQualifiedTypescriptType(projectRootAlias,
				packageName + "." + getFileName(shortName) + "." + shortName);
	}

	public static List<Field> getFieldsWithoutPrimaryKey(List<Field> fields, String pkField) {
		return fields.stream().filter(field -> !StringUtils.equals(field.getName(), pkField))
				.collect(Collectors.toList());
	}

	/**
	 * 筛选列表展示字段
	 * @param fields
	 * @param pkField
	 * @return
	 */
	public static List<Field> getListFields(List<Field> fields) {
		List<Field> manyToOneFields = GeneratorUtils.getRelatedFields(fields, JoinTarget.JoinType.ONE);
		return fields.stream().filter(field -> {
			if (GeneratorUtils.isRelationField(field)) {
				Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
				if (relation.getJoinType() == JoinTarget.JoinType.MORE) {
					return false;
				}
			}
			if (GeneratorUtils.isLogicDeleteField(field) || GeneratorUtils.isVersionField(field)) {
				return false;
			}

			if (manyToOneFields.size() > 0 && getBeRelatedCommonField(field, manyToOneFields).isPresent()) {
				return false;
			}
			return true;
		}).collect(Collectors.toList());
	}

	private static Optional<Field> getBeRelatedCommonField(Field field, List<Field> manyToOneFields) {
		return manyToOneFields.stream().filter(field1 -> {
			Relation relation = (Relation) field1.getAttribute(Constants.FIELD_RELATION);
			return StringUtils.equals(field.getName(), relation.getSourceField().getName());
		}).findFirst();
	}

	/**
	 * 筛选Form输入字段
	 * @param fields
	 * @param pkField
	 * @return
	 */
	public static List<Field> getInputFields(List<Field> fields) {
		List<Field> manyToOneFields = GeneratorUtils.getRelatedFields(fields, JoinTarget.JoinType.ONE);
		return fields.stream().filter(field -> {
			if (GeneratorUtils.isRelationField(field)) {
				return false;
			}
			if (GeneratorUtils.isLogicDeleteField(field) || GeneratorUtils.isVersionField(field)) {
				return false;
			}
			Optional<Field> beRelated = getBeRelatedCommonField(field, manyToOneFields);
			if (beRelated.isPresent()) {
				field.setAttribute(Constants.TARGET_FIELD_RELATION,
						beRelated.get().getAttribute(Constants.FIELD_RELATION));
				return true;
			}
			return field.getAttribute(Constants.FIELD_EXT_ATTR) == null;
		}).collect(Collectors.toList());
	}

	public static List<Field> getRelationFields(List<Field> fields) {
		return GeneratorUtils.getRelatedFields(fields, JoinTarget.JoinType.ONE);
	}

}

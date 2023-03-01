package io.entframework.kernel.db.generator.plugin.web.runtime;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.config.Relation;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.JoinTarget;

import java.time.LocalDateTime;

public class ModelField {

	private final Field field;

	private final IntrospectedColumn column;

	public ModelField(Field field, IntrospectedColumn column) {
		this.field = field;
		this.column = column;
	}

	public FullyQualifiedJavaType getJavaType() {
		return field.getType();
	}

	public String getName() {
		return field.getName();
	}

	public String getDescription() {
		return field.getDescription();
	}

	public int getLength() {
		return column.getLength();
	}

	public boolean isRequired() {
		return !column.isNullable();
	}

	public int getScale() {
		return column.getScale();
	}

	public String getFieldType() {

		if (StringUtils.equalsAny(this.field.getType().getShortName(), "boolean", "Boolean")) {
			return "boolean";
		}
		if (this.field.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR) != null) {
			return "enum";
		}
		if (this.field.getAttribute(Constants.TARGET_FIELD_RELATION) != null) {
			return "relation";
		}
		if (this.column.isStringColumn()) {
			return "string";
		}
		if (this.column.isNumberColumn()) {
			return "number";
		}
		if (this.column.isBLOBColumn()) {
			return "blob";
		}
		if (this.column.isJDBCDateColumn()) {
			return "date";
		}
		if (this.column.isJDBCTimeColumn()) {
			return "time";
		}
		if (StringUtils.equals(LocalDateTime.class.getName(),
				this.column.getFullyQualifiedJavaType().getFullyQualifiedName())) {
			return "date-time";
		}
		return "string";
	}

	public String getRemarks() {
		return column.getRemarks();
	}

	public String getDefaultValue() {
		return column.getDefaultValue();
	}

	public boolean isBasic() {
		return field.getAttribute(Constants.FIELD_EXT_ATTR) == null;
	}

	public boolean isRelationField() {
		return GeneratorUtils.isRelationField(this.field);
	}

	public boolean isRelationOne() {
		return isRelationField() && isRelationType(JoinTarget.JoinType.ONE);
	}

	public boolean isRelationMany() {
		return isRelationField() && isRelationType(JoinTarget.JoinType.MORE);
	}

	private boolean isRelationType(JoinTarget.JoinType joinType) {
		Relation relation = (Relation) field.getAttribute(Constants.FIELD_RELATION);
		return relation.getJoinType() == joinType;
	}

	public Relation getRelation() {
		return (Relation) field.getAttribute(Constants.FIELD_RELATION);
	}

	public Relation getTargetRelation() {
		return (Relation) field.getAttribute(Constants.TARGET_FIELD_RELATION);
	}

}

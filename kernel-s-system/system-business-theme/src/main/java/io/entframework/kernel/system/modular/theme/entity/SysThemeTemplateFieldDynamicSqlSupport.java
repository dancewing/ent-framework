package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysThemeTemplateFieldDynamicSqlSupport {

	public static final SysThemeTemplateField sysThemeTemplateField = new SysThemeTemplateField();

	public static final SqlColumn<Long> fieldId = sysThemeTemplateField.fieldId;

	public static final SqlColumn<String> fieldName = sysThemeTemplateField.fieldName;

	public static final SqlColumn<String> fieldCode = sysThemeTemplateField.fieldCode;

	public static final SqlColumn<ThemeFieldTypeEnum> fieldType = sysThemeTemplateField.fieldType;

	public static final SqlColumn<YesOrNotEnum> fieldRequired = sysThemeTemplateField.fieldRequired;

	public static final SqlColumn<Integer> fieldLength = sysThemeTemplateField.fieldLength;

	public static final SqlColumn<String> fieldDescription = sysThemeTemplateField.fieldDescription;

	public static final SqlColumn<LocalDateTime> createTime = sysThemeTemplateField.createTime;

	public static final SqlColumn<Long> createUser = sysThemeTemplateField.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysThemeTemplateField.updateTime;

	public static final SqlColumn<Long> updateUser = sysThemeTemplateField.updateUser;

	public static final SqlColumn<String> createUserName = sysThemeTemplateField.createUserName;

	public static final SqlColumn<String> updateUserName = sysThemeTemplateField.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(fieldId, fieldName, fieldCode, fieldType,
			fieldRequired, fieldLength, fieldDescription, createTime, createUser, updateTime, updateUser,
			createUserName, updateUserName);

	public static final class SysThemeTemplateField extends AliasableSqlTable<SysThemeTemplateField> {

		public final SqlColumn<Long> fieldId = column("field_id", JDBCType.BIGINT);

		public final SqlColumn<String> fieldName = column("field_name", JDBCType.VARCHAR);

		public final SqlColumn<String> fieldCode = column("field_code", JDBCType.VARCHAR);

		public final SqlColumn<ThemeFieldTypeEnum> fieldType = column("field_type", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> fieldRequired = column("field_required", JDBCType.CHAR);

		public final SqlColumn<Integer> fieldLength = column("field_length", JDBCType.INTEGER);

		public final SqlColumn<String> fieldDescription = column("field_description", JDBCType.VARCHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysThemeTemplateField() {
			super("sys_theme_template_field", SysThemeTemplateField::new);
		}

	}

}
package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysThemeDynamicSqlSupport {

	public static final SysTheme sysTheme = new SysTheme();

	public static final SqlColumn<Long> themeId = sysTheme.themeId;

	public static final SqlColumn<String> themeName = sysTheme.themeName;

	public static final SqlColumn<Long> templateId = sysTheme.templateId;

	public static final SqlColumn<YesOrNotEnum> statusFlag = sysTheme.statusFlag;

	public static final SqlColumn<LocalDateTime> createTime = sysTheme.createTime;

	public static final SqlColumn<Long> createUser = sysTheme.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysTheme.updateTime;

	public static final SqlColumn<Long> updateUser = sysTheme.updateUser;

	public static final SqlColumn<String> createUserName = sysTheme.createUserName;

	public static final SqlColumn<String> updateUserName = sysTheme.updateUserName;

	public static final SqlColumn<String> themeValue = sysTheme.themeValue;

	public static final BasicColumn[] selectList = BasicColumn.columnList(themeId, themeName, templateId, statusFlag,
			createTime, createUser, updateTime, updateUser, createUserName, updateUserName, themeValue);

	public static final class SysTheme extends AliasableSqlTable<SysTheme> {

		public final SqlColumn<Long> themeId = column("theme_id", JDBCType.BIGINT);

		public final SqlColumn<String> themeName = column("theme_name", JDBCType.VARCHAR);

		public final SqlColumn<Long> templateId = column("template_id", JDBCType.BIGINT);

		public final SqlColumn<YesOrNotEnum> statusFlag = column("status_flag", JDBCType.CHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> themeValue = column("theme_value", JDBCType.LONGVARCHAR);

		public SysTheme() {
			super("sys_theme", SysTheme::new);
		}

	}

}
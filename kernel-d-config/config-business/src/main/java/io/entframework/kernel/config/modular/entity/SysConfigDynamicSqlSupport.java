package io.entframework.kernel.config.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysConfigDynamicSqlSupport {

	public static final SysConfig sysConfig = new SysConfig();

	public static final SqlColumn<Long> configId = sysConfig.configId;

	public static final SqlColumn<String> configName = sysConfig.configName;

	public static final SqlColumn<String> configCode = sysConfig.configCode;

	public static final SqlColumn<String> configValue = sysConfig.configValue;

	public static final SqlColumn<YesOrNotEnum> sysFlag = sysConfig.sysFlag;

	public static final SqlColumn<String> remark = sysConfig.remark;

	public static final SqlColumn<StatusEnum> statusFlag = sysConfig.statusFlag;

	public static final SqlColumn<String> groupCode = sysConfig.groupCode;

	public static final SqlColumn<YesOrNotEnum> delFlag = sysConfig.delFlag;

	public static final SqlColumn<LocalDateTime> createTime = sysConfig.createTime;

	public static final SqlColumn<Long> createUser = sysConfig.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysConfig.updateTime;

	public static final SqlColumn<Long> updateUser = sysConfig.updateUser;

	public static final SqlColumn<String> createUserName = sysConfig.createUserName;

	public static final SqlColumn<String> updateUserName = sysConfig.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(configId, configName, configCode, configValue,
			sysFlag, remark, statusFlag, groupCode, delFlag, createTime, createUser, updateTime, updateUser,
			createUserName, updateUserName);

	public static final class SysConfig extends AliasableSqlTable<SysConfig> {

		public final SqlColumn<Long> configId = column("config_id", JDBCType.BIGINT);

		public final SqlColumn<String> configName = column("config_name", JDBCType.VARCHAR);

		public final SqlColumn<String> configCode = column("config_code", JDBCType.VARCHAR);

		public final SqlColumn<String> configValue = column("config_value", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> sysFlag = column("sys_flag", JDBCType.CHAR);

		public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

		public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

		public final SqlColumn<String> groupCode = column("group_code", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysConfig() {
			super("sys_config", SysConfig::new);
		}

	}

}
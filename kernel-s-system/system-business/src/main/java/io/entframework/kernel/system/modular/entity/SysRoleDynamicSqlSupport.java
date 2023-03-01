package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysRoleDynamicSqlSupport {

	public static final SysRole sysRole = new SysRole();

	public static final SqlColumn<Long> roleId = sysRole.roleId;

	public static final SqlColumn<String> roleName = sysRole.roleName;

	public static final SqlColumn<String> roleCode = sysRole.roleCode;

	public static final SqlColumn<BigDecimal> roleSort = sysRole.roleSort;

	public static final SqlColumn<DataScopeTypeEnum> dataScopeType = sysRole.dataScopeType;

	public static final SqlColumn<StatusEnum> statusFlag = sysRole.statusFlag;

	public static final SqlColumn<String> remark = sysRole.remark;

	public static final SqlColumn<YesOrNotEnum> delFlag = sysRole.delFlag;

	public static final SqlColumn<LocalDateTime> createTime = sysRole.createTime;

	public static final SqlColumn<Long> createUser = sysRole.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysRole.updateTime;

	public static final SqlColumn<Long> updateUser = sysRole.updateUser;

	public static final SqlColumn<YesOrNotEnum> roleSystemFlag = sysRole.roleSystemFlag;

	public static final SqlColumn<String> roleTypeCode = sysRole.roleTypeCode;

	public static final SqlColumn<String> createUserName = sysRole.createUserName;

	public static final SqlColumn<String> updateUserName = sysRole.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(roleId, roleName, roleCode, roleSort,
			dataScopeType, statusFlag, remark, delFlag, createTime, createUser, updateTime, updateUser, roleSystemFlag,
			roleTypeCode, createUserName, updateUserName);

	public static final class SysRole extends AliasableSqlTable<SysRole> {

		public final SqlColumn<Long> roleId = column("role_id", JDBCType.BIGINT);

		public final SqlColumn<String> roleName = column("role_name", JDBCType.VARCHAR);

		public final SqlColumn<String> roleCode = column("role_code", JDBCType.VARCHAR);

		public final SqlColumn<BigDecimal> roleSort = column("role_sort", JDBCType.DECIMAL);

		public final SqlColumn<DataScopeTypeEnum> dataScopeType = column("data_scope_type", JDBCType.TINYINT);

		public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

		public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<YesOrNotEnum> roleSystemFlag = column("role_system_flag", JDBCType.CHAR);

		public final SqlColumn<String> roleTypeCode = column("role_type_code", JDBCType.VARCHAR);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysRole() {
			super("sys_role", SysRole::new);
		}

	}

}
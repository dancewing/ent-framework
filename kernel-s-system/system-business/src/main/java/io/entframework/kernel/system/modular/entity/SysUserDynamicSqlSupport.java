package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.rule.enums.GenderEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.UserStatusEnum;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysUserDynamicSqlSupport {

	public static final SysUser sysUser = new SysUser();

	public static final SqlColumn<Long> userId = sysUser.userId;

	public static final SqlColumn<String> realName = sysUser.realName;

	public static final SqlColumn<String> nickName = sysUser.nickName;

	public static final SqlColumn<String> account = sysUser.account;

	public static final SqlColumn<String> password = sysUser.password;

	public static final SqlColumn<Long> avatar = sysUser.avatar;

	public static final SqlColumn<LocalDate> birthday = sysUser.birthday;

	public static final SqlColumn<GenderEnum> sex = sysUser.sex;

	public static final SqlColumn<String> email = sysUser.email;

	public static final SqlColumn<String> phone = sysUser.phone;

	public static final SqlColumn<String> tel = sysUser.tel;

	public static final SqlColumn<YesOrNotEnum> superAdminFlag = sysUser.superAdminFlag;

	public static final SqlColumn<Long> orgId = sysUser.orgId;

	public static final SqlColumn<Long> positionId = sysUser.positionId;

	public static final SqlColumn<UserStatusEnum> statusFlag = sysUser.statusFlag;

	public static final SqlColumn<Integer> loginCount = sysUser.loginCount;

	public static final SqlColumn<String> lastLoginIp = sysUser.lastLoginIp;

	public static final SqlColumn<LocalDateTime> lastLoginTime = sysUser.lastLoginTime;

	public static final SqlColumn<YesOrNotEnum> delFlag = sysUser.delFlag;

	public static final SqlColumn<LocalDateTime> createTime = sysUser.createTime;

	public static final SqlColumn<Long> createUser = sysUser.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysUser.updateTime;

	public static final SqlColumn<Long> updateUser = sysUser.updateUser;

	public static final SqlColumn<String> createUserName = sysUser.createUserName;

	public static final SqlColumn<String> updateUserName = sysUser.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(userId, realName, nickName, account, password,
			avatar, birthday, sex, email, phone, tel, superAdminFlag, orgId, positionId, statusFlag, loginCount,
			lastLoginIp, lastLoginTime, delFlag, createTime, createUser, updateTime, updateUser, createUserName,
			updateUserName);

	public static final class SysUser extends AliasableSqlTable<SysUser> {

		public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

		public final SqlColumn<String> realName = column("real_name", JDBCType.VARCHAR);

		public final SqlColumn<String> nickName = column("nick_name", JDBCType.VARCHAR);

		public final SqlColumn<String> account = column("account", JDBCType.VARCHAR);

		public final SqlColumn<String> password = column("`password`", JDBCType.VARCHAR);

		public final SqlColumn<Long> avatar = column("avatar", JDBCType.BIGINT);

		public final SqlColumn<LocalDate> birthday = column("birthday", JDBCType.DATE);

		public final SqlColumn<GenderEnum> sex = column("sex", JDBCType.CHAR);

		public final SqlColumn<String> email = column("email", JDBCType.VARCHAR);

		public final SqlColumn<String> phone = column("phone", JDBCType.VARCHAR);

		public final SqlColumn<String> tel = column("tel", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> superAdminFlag = column("super_admin_flag", JDBCType.CHAR);

		public final SqlColumn<Long> orgId = column("org_id", JDBCType.BIGINT);

		public final SqlColumn<Long> positionId = column("position_id", JDBCType.BIGINT);

		public final SqlColumn<UserStatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

		public final SqlColumn<Integer> loginCount = column("login_count", JDBCType.INTEGER);

		public final SqlColumn<String> lastLoginIp = column("last_login_ip", JDBCType.VARCHAR);

		public final SqlColumn<LocalDateTime> lastLoginTime = column("last_login_time", JDBCType.TIMESTAMP);

		public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysUser() {
			super("sys_user", SysUser::new);
		}

	}

}
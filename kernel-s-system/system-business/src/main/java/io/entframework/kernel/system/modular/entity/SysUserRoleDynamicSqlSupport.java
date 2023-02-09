package io.entframework.kernel.system.modular.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysUserRoleDynamicSqlSupport {
    public static final SysUserRole sysUserRole = new SysUserRole();

    public static final SqlColumn<Long> userRoleId = sysUserRole.userRoleId;

    public static final SqlColumn<Long> userId = sysUserRole.userId;

    public static final SqlColumn<Long> roleId = sysUserRole.roleId;

    public static final SqlColumn<LocalDateTime> createTime = sysUserRole.createTime;

    public static final SqlColumn<Long> createUser = sysUserRole.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysUserRole.updateTime;

    public static final SqlColumn<Long> updateUser = sysUserRole.updateUser;

    public static final SqlColumn<String> createUserName = sysUserRole.createUserName;

    public static final SqlColumn<String> updateUserName = sysUserRole.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(userRoleId, userId, roleId, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysUserRole extends AliasableSqlTable<SysUserRole> {
        public final SqlColumn<Long> userRoleId = column("user_role_id", JDBCType.BIGINT);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Long> roleId = column("role_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysUserRole() {
            super("sys_user_role", SysUserRole::new);
        }
    }
}
package io.entframework.kernel.system.modular.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysUserDataScopeDynamicSqlSupport {
    public static final SysUserDataScope sysUserDataScope = new SysUserDataScope();

    public static final SqlColumn<Long> userDataScopeId = sysUserDataScope.userDataScopeId;

    public static final SqlColumn<Long> userId = sysUserDataScope.userId;

    public static final SqlColumn<Long> orgId = sysUserDataScope.orgId;

    public static final SqlColumn<LocalDateTime> createTime = sysUserDataScope.createTime;

    public static final SqlColumn<Long> createUser = sysUserDataScope.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysUserDataScope.updateTime;

    public static final SqlColumn<Long> updateUser = sysUserDataScope.updateUser;

    public static final SqlColumn<String> createUserName = sysUserDataScope.createUserName;

    public static final SqlColumn<String> updateUserName = sysUserDataScope.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(userDataScopeId, userId, orgId, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysUserDataScope extends AliasableSqlTable<SysUserDataScope> {
        public final SqlColumn<Long> userDataScopeId = column("user_data_scope_id", JDBCType.BIGINT);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Long> orgId = column("org_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysUserDataScope() {
            super("sys_user_data_scope", SysUserDataScope::new);
        }
    }
}
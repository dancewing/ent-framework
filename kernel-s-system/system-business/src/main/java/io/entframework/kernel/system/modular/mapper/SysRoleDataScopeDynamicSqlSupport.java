package io.entframework.kernel.system.modular.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysRoleDataScopeDynamicSqlSupport {
    public static final SysRoleDataScope sysRoleDataScope = new SysRoleDataScope();

    public static final SqlColumn<Long> roleDataScopeId = sysRoleDataScope.roleDataScopeId;

    public static final SqlColumn<Long> roleId = sysRoleDataScope.roleId;

    public static final SqlColumn<Long> organizationId = sysRoleDataScope.organizationId;

    public static final SqlColumn<LocalDateTime> createTime = sysRoleDataScope.createTime;

    public static final SqlColumn<Long> createUser = sysRoleDataScope.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysRoleDataScope.updateTime;

    public static final SqlColumn<Long> updateUser = sysRoleDataScope.updateUser;

    public static final SqlColumn<String> createUserName = sysRoleDataScope.createUserName;

    public static final SqlColumn<String> updateUserName = sysRoleDataScope.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(roleDataScopeId, roleId, organizationId, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysRoleDataScope extends AliasableSqlTable<SysRoleDataScope> {
        public final SqlColumn<Long> roleDataScopeId = column("role_data_scope_id", JDBCType.BIGINT);

        public final SqlColumn<Long> roleId = column("role_id", JDBCType.BIGINT);

        public final SqlColumn<Long> organizationId = column("organization_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysRoleDataScope() {
            super("sys_role_data_scope", SysRoleDataScope::new);
        }
    }
}
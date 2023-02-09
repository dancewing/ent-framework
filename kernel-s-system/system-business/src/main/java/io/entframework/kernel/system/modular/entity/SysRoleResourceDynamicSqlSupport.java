package io.entframework.kernel.system.modular.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysRoleResourceDynamicSqlSupport {
    public static final SysRoleResource sysRoleResource = new SysRoleResource();

    public static final SqlColumn<Long> roleResourceId = sysRoleResource.roleResourceId;

    public static final SqlColumn<Long> roleId = sysRoleResource.roleId;

    public static final SqlColumn<String> resourceCode = sysRoleResource.resourceCode;

    public static final SqlColumn<LocalDateTime> createTime = sysRoleResource.createTime;

    public static final SqlColumn<Long> createUser = sysRoleResource.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysRoleResource.updateTime;

    public static final SqlColumn<Long> updateUser = sysRoleResource.updateUser;

    public static final SqlColumn<String> createUserName = sysRoleResource.createUserName;

    public static final SqlColumn<String> updateUserName = sysRoleResource.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(roleResourceId, roleId, resourceCode, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysRoleResource extends AliasableSqlTable<SysRoleResource> {
        public final SqlColumn<Long> roleResourceId = column("role_resource_id", JDBCType.BIGINT);

        public final SqlColumn<Long> roleId = column("role_id", JDBCType.BIGINT);

        public final SqlColumn<String> resourceCode = column("resource_code", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysRoleResource() {
            super("sys_role_resource", SysRoleResource::new);
        }
    }
}
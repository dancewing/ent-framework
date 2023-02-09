package io.entframework.kernel.system.modular.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysRoleMenuDynamicSqlSupport {
    public static final SysRoleMenu sysRoleMenu = new SysRoleMenu();

    public static final SqlColumn<Long> roleMenuId = sysRoleMenu.roleMenuId;

    public static final SqlColumn<Long> roleId = sysRoleMenu.roleId;

    public static final SqlColumn<Long> menuId = sysRoleMenu.menuId;

    public static final SqlColumn<LocalDateTime> createTime = sysRoleMenu.createTime;

    public static final SqlColumn<Long> createUser = sysRoleMenu.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysRoleMenu.updateTime;

    public static final SqlColumn<Long> updateUser = sysRoleMenu.updateUser;

    public static final SqlColumn<String> createUserName = sysRoleMenu.createUserName;

    public static final SqlColumn<String> updateUserName = sysRoleMenu.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(roleMenuId, roleId, menuId, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysRoleMenu extends AliasableSqlTable<SysRoleMenu> {
        public final SqlColumn<Long> roleMenuId = column("role_menu_id", JDBCType.BIGINT);

        public final SqlColumn<Long> roleId = column("role_id", JDBCType.BIGINT);

        public final SqlColumn<Long> menuId = column("menu_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysRoleMenu() {
            super("sys_role_menu", SysRoleMenu::new);
        }
    }
}
package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.LinkOpenTypeEnum;
import io.entframework.kernel.system.api.enums.MenuTypeEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysMenuDynamicSqlSupport {
    public static final SysMenu sysMenu = new SysMenu();

    public static final SqlColumn<Long> menuId = sysMenu.menuId;

    public static final SqlColumn<Long> menuParentId = sysMenu.menuParentId;

    public static final SqlColumn<String> menuPids = sysMenu.menuPids;

    public static final SqlColumn<String> menuName = sysMenu.menuName;

    public static final SqlColumn<MenuTypeEnum> menuType = sysMenu.menuType;

    public static final SqlColumn<String> menuCode = sysMenu.menuCode;

    public static final SqlColumn<Long> appId = sysMenu.appId;

    public static final SqlColumn<BigDecimal> menuSort = sysMenu.menuSort;

    public static final SqlColumn<StatusEnum> statusFlag = sysMenu.statusFlag;

    public static final SqlColumn<String> remark = sysMenu.remark;

    public static final SqlColumn<String> router = sysMenu.router;

    public static final SqlColumn<String> icon = sysMenu.icon;

    public static final SqlColumn<LinkOpenTypeEnum> linkOpenType = sysMenu.linkOpenType;

    public static final SqlColumn<String> linkUrl = sysMenu.linkUrl;

    public static final SqlColumn<String> activeUrl = sysMenu.activeUrl;

    public static final SqlColumn<YesOrNotEnum> visible = sysMenu.visible;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysMenu.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysMenu.createTime;

    public static final SqlColumn<Long> createUser = sysMenu.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysMenu.updateTime;

    public static final SqlColumn<Long> updateUser = sysMenu.updateUser;

    public static final SqlColumn<String> createUserName = sysMenu.createUserName;

    public static final SqlColumn<String> updateUserName = sysMenu.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(menuId, menuParentId, menuPids, menuName, menuType, menuCode, appId, menuSort, statusFlag, remark, router, icon, linkOpenType, linkUrl, activeUrl, visible, delFlag, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysMenu extends AliasableSqlTable<SysMenu> {
        public final SqlColumn<Long> menuId = column("menu_id", JDBCType.BIGINT);

        public final SqlColumn<Long> menuParentId = column("menu_parent_id", JDBCType.BIGINT);

        public final SqlColumn<String> menuPids = column("menu_pids", JDBCType.VARCHAR);

        public final SqlColumn<String> menuName = column("menu_name", JDBCType.VARCHAR);

        public final SqlColumn<MenuTypeEnum> menuType = column("menu_type", JDBCType.BIGINT);

        public final SqlColumn<String> menuCode = column("menu_code", JDBCType.VARCHAR);

        public final SqlColumn<Long> appId = column("app_id", JDBCType.BIGINT);

        public final SqlColumn<BigDecimal> menuSort = column("menu_sort", JDBCType.DECIMAL);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public final SqlColumn<String> router = column("router", JDBCType.VARCHAR);

        public final SqlColumn<String> icon = column("icon", JDBCType.VARCHAR);

        public final SqlColumn<LinkOpenTypeEnum> linkOpenType = column("link_open_type", JDBCType.TINYINT);

        public final SqlColumn<String> linkUrl = column("link_url", JDBCType.VARCHAR);

        public final SqlColumn<String> activeUrl = column("active_url", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> visible = column("visible", JDBCType.CHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysMenu() {
            super("sys_menu", SysMenu::new);
        }
    }
}
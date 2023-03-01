package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysAppDynamicSqlSupport {

    public static final SysApp sysApp = new SysApp();

    public static final SqlColumn<Long> appId = sysApp.appId;

    public static final SqlColumn<String> appName = sysApp.appName;

    public static final SqlColumn<String> appCode = sysApp.appCode;

    public static final SqlColumn<String> entryPath = sysApp.entryPath;

    public static final SqlColumn<String> appIcon = sysApp.appIcon;

    public static final SqlColumn<YesOrNotEnum> activeFlag = sysApp.activeFlag;

    public static final SqlColumn<StatusEnum> statusFlag = sysApp.statusFlag;

    public static final SqlColumn<Integer> appSort = sysApp.appSort;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysApp.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysApp.createTime;

    public static final SqlColumn<Long> createUser = sysApp.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysApp.updateTime;

    public static final SqlColumn<Long> updateUser = sysApp.updateUser;

    public static final SqlColumn<String> createUserName = sysApp.createUserName;

    public static final SqlColumn<String> updateUserName = sysApp.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(appId, appName, appCode, entryPath, appIcon,
            activeFlag, statusFlag, appSort, delFlag, createTime, createUser, updateTime, updateUser, createUserName,
            updateUserName);

    public static final class SysApp extends AliasableSqlTable<SysApp> {

        public final SqlColumn<Long> appId = column("app_id", JDBCType.BIGINT);

        public final SqlColumn<String> appName = column("app_name", JDBCType.VARCHAR);

        public final SqlColumn<String> appCode = column("app_code", JDBCType.VARCHAR);

        public final SqlColumn<String> entryPath = column("entry_path", JDBCType.VARCHAR);

        public final SqlColumn<String> appIcon = column("app_icon", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> activeFlag = column("active_flag", JDBCType.CHAR);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<Integer> appSort = column("app_sort", JDBCType.INTEGER);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysApp() {
            super("sys_app", SysApp::new);
        }

    }

}
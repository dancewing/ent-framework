package io.entframework.kernel.system.modular.loginlog.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysLoginLogDynamicSqlSupport {
    public static final SysLoginLog sysLoginLog = new SysLoginLog();

    public static final SqlColumn<Long> llgId = sysLoginLog.llgId;

    public static final SqlColumn<String> llgName = sysLoginLog.llgName;

    public static final SqlColumn<String> llgSucceed = sysLoginLog.llgSucceed;

    public static final SqlColumn<String> llgIpAddress = sysLoginLog.llgIpAddress;

    public static final SqlColumn<Long> userId = sysLoginLog.userId;

    public static final SqlColumn<String> loginAccount = sysLoginLog.loginAccount;

    public static final SqlColumn<LocalDateTime> createTime = sysLoginLog.createTime;

    public static final SqlColumn<String> llgMessage = sysLoginLog.llgMessage;

    public static final BasicColumn[] selectList = BasicColumn.columnList(llgId, llgName, llgSucceed, llgIpAddress, userId, loginAccount, createTime, llgMessage);

    public static final class SysLoginLog extends AliasableSqlTable<SysLoginLog> {
        public final SqlColumn<Long> llgId = column("llg_id", JDBCType.BIGINT);

        public final SqlColumn<String> llgName = column("llg_name", JDBCType.VARCHAR);

        public final SqlColumn<String> llgSucceed = column("llg_succeed", JDBCType.VARCHAR);

        public final SqlColumn<String> llgIpAddress = column("llg_ip_address", JDBCType.VARCHAR);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<String> loginAccount = column("login_account", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> llgMessage = column("llg_message", JDBCType.LONGVARCHAR);

        public SysLoginLog() {
            super("sys_login_log", SysLoginLog::new);
        }
    }
}
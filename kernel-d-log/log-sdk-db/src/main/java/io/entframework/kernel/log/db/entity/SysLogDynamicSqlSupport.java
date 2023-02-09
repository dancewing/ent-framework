package io.entframework.kernel.log.db.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysLogDynamicSqlSupport {
    public static final SysLog sysLog = new SysLog();

    public static final SqlColumn<Long> logId = sysLog.logId;

    public static final SqlColumn<String> logName = sysLog.logName;

    public static final SqlColumn<String> logContent = sysLog.logContent;

    public static final SqlColumn<String> appName = sysLog.appName;

    public static final SqlColumn<String> requestUrl = sysLog.requestUrl;

    public static final SqlColumn<String> serverIp = sysLog.serverIp;

    public static final SqlColumn<String> clientIp = sysLog.clientIp;

    public static final SqlColumn<Long> userId = sysLog.userId;

    public static final SqlColumn<String> httpMethod = sysLog.httpMethod;

    public static final SqlColumn<String> clientBrowser = sysLog.clientBrowser;

    public static final SqlColumn<String> clientOs = sysLog.clientOs;

    public static final SqlColumn<LocalDateTime> createTime = sysLog.createTime;

    public static final SqlColumn<String> createUserName = sysLog.createUserName;

    public static final SqlColumn<String> requestParams = sysLog.requestParams;

    public static final SqlColumn<String> requestResult = sysLog.requestResult;

    public static final BasicColumn[] selectList = BasicColumn.columnList(logId, logName, logContent, appName, requestUrl, serverIp, clientIp, userId, httpMethod, clientBrowser, clientOs, createTime, createUserName, requestParams, requestResult);

    public static final class SysLog extends AliasableSqlTable<SysLog> {
        public final SqlColumn<Long> logId = column("log_id", JDBCType.BIGINT);

        public final SqlColumn<String> logName = column("log_name", JDBCType.VARCHAR);

        public final SqlColumn<String> logContent = column("log_content", JDBCType.VARCHAR);

        public final SqlColumn<String> appName = column("app_name", JDBCType.VARCHAR);

        public final SqlColumn<String> requestUrl = column("request_url", JDBCType.VARCHAR);

        public final SqlColumn<String> serverIp = column("server_ip", JDBCType.VARCHAR);

        public final SqlColumn<String> clientIp = column("client_ip", JDBCType.VARCHAR);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<String> httpMethod = column("http_method", JDBCType.VARCHAR);

        public final SqlColumn<String> clientBrowser = column("client_browser", JDBCType.VARCHAR);

        public final SqlColumn<String> clientOs = column("client_os", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> requestParams = column("request_params", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> requestResult = column("request_result", JDBCType.LONGVARCHAR);

        public SysLog() {
            super("sys_log", SysLog::new);
        }
    }
}
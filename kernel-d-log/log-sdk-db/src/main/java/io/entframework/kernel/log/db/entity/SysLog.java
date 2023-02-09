package io.entframework.kernel.log.db.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 日志记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_log", sqlSupport = SysLogDynamicSqlSupport.class, tableProperty = "sysLog")
public class SysLog {
    /**
     * 主键
     */
    @Id
    @Column(name = "log_id", jdbcType = JDBCType.BIGINT)
    private Long logId;

    /**
     * 日志的名称，一般为业务名称
     */
    @Column(name = "log_name", jdbcType = JDBCType.VARCHAR)
    private String logName;

    /**
     * 日志记录的内容
     */
    @Column(name = "log_content", jdbcType = JDBCType.VARCHAR)
    private String logContent;

    /**
     * 服务名称，一般为spring.application.name
     */
    @Column(name = "app_name", jdbcType = JDBCType.VARCHAR)
    private String appName;

    /**
     * 当前用户请求的url
     */
    @Column(name = "request_url", jdbcType = JDBCType.VARCHAR)
    private String requestUrl;

    /**
     * 当前服务器的ip
     */
    @Column(name = "server_ip", jdbcType = JDBCType.VARCHAR)
    private String serverIp;

    /**
     * 客户端的ip
     */
    @Column(name = "client_ip", jdbcType = JDBCType.VARCHAR)
    private String clientIp;

    /**
     * 用户id
     */
    @Column(name = "user_id", jdbcType = JDBCType.BIGINT)
    private Long userId;

    /**
     * 请求http方法
     */
    @Column(name = "http_method", jdbcType = JDBCType.VARCHAR)
    private String httpMethod;

    /**
     * 客户浏览器标识
     */
    @Column(name = "client_browser", jdbcType = JDBCType.VARCHAR)
    private String clientBrowser;

    /**
     * 客户操作系统
     */
    @Column(name = "client_os", jdbcType = JDBCType.VARCHAR)
    private String clientOs;

    /**
     * 创建时间
     */
    @Column(name = "create_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime createTime;

    /**
     * 创建人账号
     */
    @Column(name = "create_user_name", jdbcType = JDBCType.VARCHAR)
    private String createUserName;

    /**
     * http或方法的请求参数体
     */
    @Column(name = "request_params", jdbcType = JDBCType.LONGVARCHAR)
    private String requestParams;

    /**
     * http或方法的请求结果
     */
    @Column(name = "request_result", jdbcType = JDBCType.LONGVARCHAR)
    private String requestResult;
}
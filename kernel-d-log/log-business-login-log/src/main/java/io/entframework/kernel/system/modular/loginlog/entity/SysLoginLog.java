package io.entframework.kernel.system.modular.loginlog.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 登录记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_login_log", sqlSupport = SysLoginLogDynamicSqlSupport.class, tableProperty = "sysLoginLog")
public class SysLoginLog {
    /**
     * 主键
     */
    @Id
    @Column(name = "llg_id", jdbcType = JDBCType.BIGINT)
    private Long llgId;

    /**
     * 日志名称
     */
    @Column(name = "llg_name", jdbcType = JDBCType.VARCHAR)
    private String llgName;

    /**
     * 是否执行成功
     */
    @Column(name = "llg_succeed", jdbcType = JDBCType.VARCHAR)
    private String llgSucceed;

    /**
     * 登录ip
     */
    @Column(name = "llg_ip_address", jdbcType = JDBCType.VARCHAR)
    private String llgIpAddress;

    /**
     * 用户id
     */
    @Column(name = "user_id", jdbcType = JDBCType.BIGINT)
    private Long userId;

    /**
     * 用户账号
     */
    @Column(name = "login_account", jdbcType = JDBCType.VARCHAR)
    private String loginAccount;

    /**
     * 创建时间
     */
    @Column(name = "create_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime createTime;

    /**
     * 具体消息
     */
    @Column(name = "llg_message", jdbcType = JDBCType.LONGVARCHAR)
    private String llgMessage;
}
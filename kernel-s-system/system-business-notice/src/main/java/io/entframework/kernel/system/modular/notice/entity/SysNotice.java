package io.entframework.kernel.system.modular.notice.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 通知管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_notice", sqlSupport = SysNoticeDynamicSqlSupport.class, tableProperty = "sysNotice")
public class SysNotice extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "notice_id", jdbcType = JDBCType.BIGINT)
    private Long noticeId;

    /**
     * 通知标题
     */
    @Column(name = "notice_title", jdbcType = JDBCType.VARCHAR)
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @Column(name = "notice_summary", jdbcType = JDBCType.VARCHAR)
    private String noticeSummary;

    /**
     * 通知内容
     */
    @Column(name = "notice_content", jdbcType = JDBCType.VARCHAR)
    private String noticeContent;

    /**
     * 优先级
     */
    @Column(name = "priority_level", jdbcType = JDBCType.VARCHAR)
    private String priorityLevel;

    /**
     * 开始时间
     */
    @Column(name = "notice_begin_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime noticeBeginTime;

    /**
     * 结束时间
     */
    @Column(name = "notice_end_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime noticeEndTime;

    /**
     * 通知范围（用户id字符串）
     */
    @Column(name = "notice_scope", jdbcType = JDBCType.VARCHAR)
    private String noticeScope;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    private static final long serialVersionUID = 1L;
}
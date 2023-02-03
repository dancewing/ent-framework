package io.entframework.kernel.message.db.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.message.api.enums.MessageBusinessTypeEnum;
import io.entframework.kernel.message.api.enums.MessagePriorityLevelEnum;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统消息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_message")
public class SysMessage extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "message_id", jdbcType = JDBCType.BIGINT)
    private Long messageId;

    /**
     * 接收用户id
     */
    @Column(name = "receive_user_id", jdbcType = JDBCType.BIGINT)
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    @Column(name = "send_user_id", jdbcType = JDBCType.BIGINT)
    private Long sendUserId;

    /**
     * 消息标题
     */
    @Column(name = "message_title", jdbcType = JDBCType.VARCHAR)
    private String messageTitle;

    /**
     * 消息内容
     */
    @Column(name = "message_content", jdbcType = JDBCType.VARCHAR)
    private String messageContent;

    /**
     * 消息类型
     */
    @Column(name = "message_type", jdbcType = JDBCType.VARCHAR)
    private String messageType;

    /**
     * 优先级
     */
    @Column(name = "priority_level", jdbcType = JDBCType.VARCHAR)
    private MessagePriorityLevelEnum priorityLevel;

    /**
     * 消息发送时间
     */
    @Column(name = "message_send_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime messageSendTime;

    /**
     * 业务id
     */
    @Column(name = "business_id", jdbcType = JDBCType.BIGINT)
    private Long businessId;

    /**
     * 业务类型(根据业务id和业务类型可以确定业务数据)
     */
    @Column(name = "business_type", jdbcType = JDBCType.VARCHAR)
    private MessageBusinessTypeEnum businessType;

    /**
     * 阅读状态：0-未读，1-已读
     */
    @Column(name = "read_flag", jdbcType = JDBCType.CHAR)
    private MessageReadFlagEnum readFlag;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum delFlag;

    private static final long serialVersionUID = 1L;
}
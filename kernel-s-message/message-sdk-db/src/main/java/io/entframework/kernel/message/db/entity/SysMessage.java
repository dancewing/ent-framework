package io.entframework.kernel.message.db.entity;

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
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 系统消息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_message", sqlSupport = SysMessageDynamicSqlSupport.class, tableProperty = "sysMessage")
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

    public SysMessage messageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public SysMessage receiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
        return this;
    }

    public SysMessage sendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
        return this;
    }

    public SysMessage messageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
        return this;
    }

    public SysMessage messageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public SysMessage messageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public SysMessage priorityLevel(MessagePriorityLevelEnum priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public SysMessage messageSendTime(LocalDateTime messageSendTime) {
        this.messageSendTime = messageSendTime;
        return this;
    }

    public SysMessage businessId(Long businessId) {
        this.businessId = businessId;
        return this;
    }

    public SysMessage businessType(MessageBusinessTypeEnum businessType) {
        this.businessType = businessType;
        return this;
    }

    public SysMessage readFlag(MessageReadFlagEnum readFlag) {
        this.readFlag = readFlag;
        return this;
    }

    public SysMessage delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }
}
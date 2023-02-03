package io.entframework.kernel.message.db.mapper;

import io.entframework.kernel.message.api.enums.MessageBusinessTypeEnum;
import io.entframework.kernel.message.api.enums.MessagePriorityLevelEnum;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysMessageDynamicSqlSupport {
    public static final SysMessage sysMessage = new SysMessage();

    public static final SqlColumn<Long> messageId = sysMessage.messageId;

    public static final SqlColumn<Long> receiveUserId = sysMessage.receiveUserId;

    public static final SqlColumn<Long> sendUserId = sysMessage.sendUserId;

    public static final SqlColumn<String> messageTitle = sysMessage.messageTitle;

    public static final SqlColumn<String> messageContent = sysMessage.messageContent;

    public static final SqlColumn<String> messageType = sysMessage.messageType;

    public static final SqlColumn<MessagePriorityLevelEnum> priorityLevel = sysMessage.priorityLevel;

    public static final SqlColumn<LocalDateTime> messageSendTime = sysMessage.messageSendTime;

    public static final SqlColumn<Long> businessId = sysMessage.businessId;

    public static final SqlColumn<MessageBusinessTypeEnum> businessType = sysMessage.businessType;

    public static final SqlColumn<MessageReadFlagEnum> readFlag = sysMessage.readFlag;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysMessage.delFlag;

    public static final SqlColumn<Long> createUser = sysMessage.createUser;

    public static final SqlColumn<LocalDateTime> createTime = sysMessage.createTime;

    public static final SqlColumn<Long> updateUser = sysMessage.updateUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysMessage.updateTime;

    public static final SqlColumn<String> createUserName = sysMessage.createUserName;

    public static final SqlColumn<String> updateUserName = sysMessage.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(messageId, receiveUserId, sendUserId, messageTitle, messageContent, messageType, priorityLevel, messageSendTime, businessId, businessType, readFlag, delFlag, createUser, createTime, updateUser, updateTime, createUserName, updateUserName);

    public static final class SysMessage extends AliasableSqlTable<SysMessage> {
        public final SqlColumn<Long> messageId = column("message_id", JDBCType.BIGINT);

        public final SqlColumn<Long> receiveUserId = column("receive_user_id", JDBCType.BIGINT);

        public final SqlColumn<Long> sendUserId = column("send_user_id", JDBCType.BIGINT);

        public final SqlColumn<String> messageTitle = column("message_title", JDBCType.VARCHAR);

        public final SqlColumn<String> messageContent = column("message_content", JDBCType.VARCHAR);

        public final SqlColumn<String> messageType = column("message_type", JDBCType.VARCHAR);

        public final SqlColumn<MessagePriorityLevelEnum> priorityLevel = column("priority_level", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> messageSendTime = column("message_send_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> businessId = column("business_id", JDBCType.BIGINT);

        public final SqlColumn<MessageBusinessTypeEnum> businessType = column("business_type", JDBCType.VARCHAR);

        public final SqlColumn<MessageReadFlagEnum> readFlag = column("read_flag", JDBCType.CHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysMessage() {
            super("sys_message", SysMessage::new);
        }
    }
}
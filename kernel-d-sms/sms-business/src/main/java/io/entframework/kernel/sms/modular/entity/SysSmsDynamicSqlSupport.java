package io.entframework.kernel.sms.modular.entity;

import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysSmsDynamicSqlSupport {
    public static final SysSms sysSms = new SysSms();

    public static final SqlColumn<Long> smsId = sysSms.smsId;

    public static final SqlColumn<String> phoneNumber = sysSms.phoneNumber;

    public static final SqlColumn<String> validateCode = sysSms.validateCode;

    public static final SqlColumn<String> templateCode = sysSms.templateCode;

    public static final SqlColumn<String> bizId = sysSms.bizId;

    public static final SqlColumn<SmsSendStatusEnum> statusFlag = sysSms.statusFlag;

    public static final SqlColumn<SmsSendSourceEnum> source = sysSms.source;

    public static final SqlColumn<LocalDateTime> invalidTime = sysSms.invalidTime;

    public static final SqlColumn<LocalDateTime> createTime = sysSms.createTime;

    public static final SqlColumn<Long> createUser = sysSms.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysSms.updateTime;

    public static final SqlColumn<Long> updateUser = sysSms.updateUser;

    public static final SqlColumn<String> createUserName = sysSms.createUserName;

    public static final SqlColumn<String> updateUserName = sysSms.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(smsId, phoneNumber, validateCode, templateCode, bizId, statusFlag, source, invalidTime, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysSms extends AliasableSqlTable<SysSms> {
        public final SqlColumn<Long> smsId = column("sms_id", JDBCType.BIGINT);

        public final SqlColumn<String> phoneNumber = column("phone_number", JDBCType.VARCHAR);

        public final SqlColumn<String> validateCode = column("validate_code", JDBCType.VARCHAR);

        public final SqlColumn<String> templateCode = column("template_code", JDBCType.VARCHAR);

        public final SqlColumn<String> bizId = column("biz_id", JDBCType.VARCHAR);

        public final SqlColumn<SmsSendStatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<SmsSendSourceEnum> source = column("`source`", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> invalidTime = column("invalid_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysSms() {
            super("sys_sms", SysSms::new);
        }
    }
}
package io.entframework.kernel.i18n.modular.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TranslationDynamicSqlSupport {
    public static final Translation translation = new Translation();

    public static final SqlColumn<Long> tranId = translation.tranId;

    public static final SqlColumn<String> tranCode = translation.tranCode;

    public static final SqlColumn<String> tranName = translation.tranName;

    public static final SqlColumn<String> tranLanguageCode = translation.tranLanguageCode;

    public static final SqlColumn<String> tranValue = translation.tranValue;

    public static final SqlColumn<LocalDateTime> createTime = translation.createTime;

    public static final SqlColumn<Long> createUser = translation.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = translation.updateTime;

    public static final SqlColumn<Long> updateUser = translation.updateUser;

    public static final SqlColumn<String> createUserName = translation.createUserName;

    public static final SqlColumn<String> updateUserName = translation.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(tranId, tranCode, tranName, tranLanguageCode, tranValue, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class Translation extends AliasableSqlTable<Translation> {
        public final SqlColumn<Long> tranId = column("tran_id", JDBCType.BIGINT);

        public final SqlColumn<String> tranCode = column("tran_code", JDBCType.VARCHAR);

        public final SqlColumn<String> tranName = column("tran_name", JDBCType.VARCHAR);

        public final SqlColumn<String> tranLanguageCode = column("tran_language_code", JDBCType.VARCHAR);

        public final SqlColumn<String> tranValue = column("tran_value", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public Translation() {
            super("sys_translation", Translation::new);
        }
    }
}
package io.entframework.kernel.db.mds.example.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class AutoIncrementDynamicSqlSupport {
    public static final AutoIncrement autoIncrement = new AutoIncrement();

    public static final SqlColumn<Long> id = autoIncrement.id;

    public static final SqlColumn<String> username = autoIncrement.username;

    public static final SqlColumn<LocalDateTime> createTime = autoIncrement.createTime;

    public static final SqlColumn<Long> createUser = autoIncrement.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = autoIncrement.updateTime;

    public static final SqlColumn<Long> updateUser = autoIncrement.updateUser;

    public static final SqlColumn<String> createUserName = autoIncrement.createUserName;

    public static final SqlColumn<String> updateUserName = autoIncrement.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, username, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class AutoIncrement extends AliasableSqlTable<AutoIncrement> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> username = column("username", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public AutoIncrement() {
            super("exam_auto_increment", AutoIncrement::new);
        }
    }
}
package io.entframework.kernel.system.modular.home.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysStatisticsCountDynamicSqlSupport {

    public static final SysStatisticsCount sysStatisticsCount = new SysStatisticsCount();

    public static final SqlColumn<Long> statCountId = sysStatisticsCount.statCountId;

    public static final SqlColumn<Long> userId = sysStatisticsCount.userId;

    public static final SqlColumn<Long> statUrlId = sysStatisticsCount.statUrlId;

    public static final SqlColumn<Integer> statCount = sysStatisticsCount.statCount;

    public static final SqlColumn<LocalDateTime> createTime = sysStatisticsCount.createTime;

    public static final SqlColumn<Long> createUser = sysStatisticsCount.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysStatisticsCount.updateTime;

    public static final SqlColumn<Long> updateUser = sysStatisticsCount.updateUser;

    public static final SqlColumn<String> createUserName = sysStatisticsCount.createUserName;

    public static final SqlColumn<String> updateUserName = sysStatisticsCount.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(statCountId, userId, statUrlId, statCount,
            createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysStatisticsCount extends AliasableSqlTable<SysStatisticsCount> {

        public final SqlColumn<Long> statCountId = column("stat_count_id", JDBCType.BIGINT);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Long> statUrlId = column("stat_url_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> statCount = column("stat_count", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysStatisticsCount() {
            super("sys_statistics_count", SysStatisticsCount::new);
        }

    }

}
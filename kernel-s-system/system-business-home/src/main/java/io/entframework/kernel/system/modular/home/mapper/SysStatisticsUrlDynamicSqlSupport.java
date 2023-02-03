package io.entframework.kernel.system.modular.home.mapper;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysStatisticsUrlDynamicSqlSupport {
    public static final SysStatisticsUrl sysStatisticsUrl = new SysStatisticsUrl();

    public static final SqlColumn<Long> statUrlId = sysStatisticsUrl.statUrlId;

    public static final SqlColumn<String> statName = sysStatisticsUrl.statName;

    public static final SqlColumn<Long> statMenuId = sysStatisticsUrl.statMenuId;

    public static final SqlColumn<String> statUrl = sysStatisticsUrl.statUrl;

    public static final SqlColumn<YesOrNotEnum> alwaysShow = sysStatisticsUrl.alwaysShow;

    public static final SqlColumn<LocalDateTime> createTime = sysStatisticsUrl.createTime;

    public static final SqlColumn<Long> createUser = sysStatisticsUrl.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysStatisticsUrl.updateTime;

    public static final SqlColumn<Long> updateUser = sysStatisticsUrl.updateUser;

    public static final SqlColumn<String> createUserName = sysStatisticsUrl.createUserName;

    public static final SqlColumn<String> updateUserName = sysStatisticsUrl.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(statUrlId, statName, statMenuId, statUrl, alwaysShow, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysStatisticsUrl extends AliasableSqlTable<SysStatisticsUrl> {
        public final SqlColumn<Long> statUrlId = column("stat_url_id", JDBCType.BIGINT);

        public final SqlColumn<String> statName = column("stat_name", JDBCType.VARCHAR);

        public final SqlColumn<Long> statMenuId = column("stat_menu_id", JDBCType.BIGINT);

        public final SqlColumn<String> statUrl = column("stat_url", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> alwaysShow = column("always_show", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysStatisticsUrl() {
            super("sys_statistics_url", SysStatisticsUrl::new);
        }
    }
}
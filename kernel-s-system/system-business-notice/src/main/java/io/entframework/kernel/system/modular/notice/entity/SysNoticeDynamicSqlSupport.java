package io.entframework.kernel.system.modular.notice.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysNoticeDynamicSqlSupport {
    public static final SysNotice sysNotice = new SysNotice();

    public static final SqlColumn<Long> noticeId = sysNotice.noticeId;

    public static final SqlColumn<String> noticeTitle = sysNotice.noticeTitle;

    public static final SqlColumn<String> noticeSummary = sysNotice.noticeSummary;

    public static final SqlColumn<String> noticeContent = sysNotice.noticeContent;

    public static final SqlColumn<String> priorityLevel = sysNotice.priorityLevel;

    public static final SqlColumn<LocalDateTime> noticeBeginTime = sysNotice.noticeBeginTime;

    public static final SqlColumn<LocalDateTime> noticeEndTime = sysNotice.noticeEndTime;

    public static final SqlColumn<String> noticeScope = sysNotice.noticeScope;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysNotice.delFlag;

    public static final SqlColumn<Long> createUser = sysNotice.createUser;

    public static final SqlColumn<LocalDateTime> createTime = sysNotice.createTime;

    public static final SqlColumn<Long> updateUser = sysNotice.updateUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysNotice.updateTime;

    public static final SqlColumn<String> createUserName = sysNotice.createUserName;

    public static final SqlColumn<String> updateUserName = sysNotice.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(noticeId, noticeTitle, noticeSummary, noticeContent, priorityLevel, noticeBeginTime, noticeEndTime, noticeScope, delFlag, createUser, createTime, updateUser, updateTime, createUserName, updateUserName);

    public static final class SysNotice extends AliasableSqlTable<SysNotice> {
        public final SqlColumn<Long> noticeId = column("notice_id", JDBCType.BIGINT);

        public final SqlColumn<String> noticeTitle = column("notice_title", JDBCType.VARCHAR);

        public final SqlColumn<String> noticeSummary = column("notice_summary", JDBCType.VARCHAR);

        public final SqlColumn<String> noticeContent = column("notice_content", JDBCType.VARCHAR);

        public final SqlColumn<String> priorityLevel = column("priority_level", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> noticeBeginTime = column("notice_begin_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> noticeEndTime = column("notice_end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> noticeScope = column("notice_scope", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysNotice() {
            super("sys_notice", SysNotice::new);
        }
    }
}
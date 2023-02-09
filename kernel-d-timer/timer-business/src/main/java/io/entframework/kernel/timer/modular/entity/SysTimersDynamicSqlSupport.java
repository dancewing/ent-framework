package io.entframework.kernel.timer.modular.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysTimersDynamicSqlSupport {
    public static final SysTimers sysTimers = new SysTimers();

    public static final SqlColumn<Long> timerId = sysTimers.timerId;

    public static final SqlColumn<String> timerName = sysTimers.timerName;

    public static final SqlColumn<String> actionClass = sysTimers.actionClass;

    public static final SqlColumn<String> cron = sysTimers.cron;

    public static final SqlColumn<String> params = sysTimers.params;

    public static final SqlColumn<TimerJobStatusEnum> jobStatus = sysTimers.jobStatus;

    public static final SqlColumn<String> remark = sysTimers.remark;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysTimers.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysTimers.createTime;

    public static final SqlColumn<Long> createUser = sysTimers.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysTimers.updateTime;

    public static final SqlColumn<Long> updateUser = sysTimers.updateUser;

    public static final SqlColumn<String> createUserName = sysTimers.createUserName;

    public static final SqlColumn<String> updateUserName = sysTimers.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(timerId, timerName, actionClass, cron, params, jobStatus, remark, delFlag, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysTimers extends AliasableSqlTable<SysTimers> {
        public final SqlColumn<Long> timerId = column("timer_id", JDBCType.BIGINT);

        public final SqlColumn<String> timerName = column("timer_name", JDBCType.VARCHAR);

        public final SqlColumn<String> actionClass = column("action_class", JDBCType.VARCHAR);

        public final SqlColumn<String> cron = column("cron", JDBCType.VARCHAR);

        public final SqlColumn<String> params = column("params", JDBCType.VARCHAR);

        public final SqlColumn<TimerJobStatusEnum> jobStatus = column("job_status", JDBCType.INTEGER);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysTimers() {
            super("sys_timers", SysTimers::new);
        }
    }
}
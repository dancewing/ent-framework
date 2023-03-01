package io.entframework.kernel.timer.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 定时任务
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_timers", sqlSupport = SysTimersDynamicSqlSupport.class, tableProperty = "sysTimers")
public class SysTimers extends BaseEntity {
    /**
     * 定时器id
     */
    @Id
    @Column(name = "timer_id", jdbcType = JDBCType.BIGINT)
    private Long timerId;

    /**
     * 任务名称
     */
    @Column(name = "timer_name", jdbcType = JDBCType.VARCHAR)
    private String timerName;

    /**
     * 执行任务的class的类名（实现了TimerAction接口的类的全称）
     */
    @Column(name = "action_class", jdbcType = JDBCType.VARCHAR)
    private String actionClass;

    /**
     * 定时任务表达式
     */
    @Column(name = "cron", jdbcType = JDBCType.VARCHAR)
    private String cron;

    /**
     * 参数
     */
    @Column(name = "params", jdbcType = JDBCType.VARCHAR)
    private String params;

    /**
     * 状态：1-运行，2-停止
     */
    @Column(name = "job_status", jdbcType = JDBCType.INTEGER)
    private TimerJobStatusEnum jobStatus;

    /**
     * 备注信息
     */
    @Column(name = "remark", jdbcType = JDBCType.VARCHAR)
    private String remark;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    public SysTimers timerId(Long timerId) {
        this.timerId = timerId;
        return this;
    }

    public SysTimers timerName(String timerName) {
        this.timerName = timerName;
        return this;
    }

    public SysTimers actionClass(String actionClass) {
        this.actionClass = actionClass;
        return this;
    }

    public SysTimers cron(String cron) {
        this.cron = cron;
        return this;
    }

    public SysTimers params(String params) {
        this.params = params;
        return this;
    }

    public SysTimers jobStatus(TimerJobStatusEnum jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public SysTimers remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SysTimers delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }
}
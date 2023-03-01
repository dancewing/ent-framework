package io.entframework.kernel.system.modular.home.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
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
 * 常用功能列表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_statistics_url", sqlSupport = SysStatisticsUrlDynamicSqlSupport.class, tableProperty = "sysStatisticsUrl")
public class SysStatisticsUrl extends BaseEntity implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "stat_url_id", jdbcType = JDBCType.BIGINT)
    private Long statUrlId;

    /**
     * 被统计名称
     */
    @Column(name = "stat_name", jdbcType = JDBCType.VARCHAR)
    private String statName;

    /**
     * 被统计菜单ID
     */
    @Column(name = "stat_menu_id", jdbcType = JDBCType.BIGINT)
    private Long statMenuId;

    /**
     * 被统计的URL
     */
    @Column(name = "stat_url", jdbcType = JDBCType.VARCHAR)
    private String statUrl;

    /**
     * 是否常驻显示，Y-是，N-否
     */
    @Column(name = "always_show", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum alwaysShow;

    private static final long serialVersionUID = 1L;

    public SysStatisticsUrl statUrlId(Long statUrlId) {
        this.statUrlId = statUrlId;
        return this;
    }

    public SysStatisticsUrl statName(String statName) {
        this.statName = statName;
        return this;
    }

    public SysStatisticsUrl statMenuId(Long statMenuId) {
        this.statMenuId = statMenuId;
        return this;
    }

    public SysStatisticsUrl statUrl(String statUrl) {
        this.statUrl = statUrl;
        return this;
    }

    public SysStatisticsUrl alwaysShow(YesOrNotEnum alwaysShow) {
        this.alwaysShow = alwaysShow;
        return this;
    }
}
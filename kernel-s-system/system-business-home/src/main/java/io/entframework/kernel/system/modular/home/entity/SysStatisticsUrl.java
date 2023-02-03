package io.entframework.kernel.system.modular.home.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 常用功能列表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_statistics_url")
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
}
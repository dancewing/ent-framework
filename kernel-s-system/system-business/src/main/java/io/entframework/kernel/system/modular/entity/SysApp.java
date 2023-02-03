package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统应用
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_app")
public class SysApp extends BaseEntity implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "app_id", jdbcType = JDBCType.BIGINT)
    private Long appId;

    /**
     * 应用名称
     */
    @Column(name = "app_name", jdbcType = JDBCType.VARCHAR)
    private String appName;

    /**
     * 编码
     */
    @Column(name = "app_code", jdbcType = JDBCType.VARCHAR)
    private String appCode;

    /**
     * 入口路径
     */
    @Column(name = "entry_path", jdbcType = JDBCType.VARCHAR)
    private String entryPath;

    /**
     * 应用图标
     */
    @Column(name = "app_icon", jdbcType = JDBCType.VARCHAR)
    private String appIcon;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @Column(name = "active_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum activeFlag;

    /**
     * 状态：1-启用，2-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 排序
     */
    @Column(name = "app_sort", jdbcType = JDBCType.INTEGER)
    private Integer appSort;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    private static final long serialVersionUID = 1L;
}
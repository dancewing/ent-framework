package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 组织机构信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "hr_organization", sqlSupport = HrOrganizationDynamicSqlSupport.class, tableProperty = "hrOrganization")
public class HrOrganization extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "org_id", jdbcType = JDBCType.BIGINT)
    private Long orgId;

    /**
     * 父id，一级节点父id是0
     */
    @Column(name = "org_parent_id", jdbcType = JDBCType.BIGINT)
    private Long orgParentId;

    /**
     * 父ids
     */
    @Column(name = "org_pids", jdbcType = JDBCType.VARCHAR)
    private String orgPids;

    /**
     * 组织名称
     */
    @Column(name = "org_name", jdbcType = JDBCType.VARCHAR)
    private String orgName;

    /**
     * 组织编码
     */
    @Column(name = "org_code", jdbcType = JDBCType.VARCHAR)
    private String orgCode;

    /**
     * 排序
     */
    @Column(name = "org_sort", jdbcType = JDBCType.DECIMAL)
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 描述
     */
    @Column(name = "org_remark", jdbcType = JDBCType.VARCHAR)
    private String orgRemark;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    private static final long serialVersionUID = 1L;
}
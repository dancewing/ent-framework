package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
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
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 系统角色
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_role", sqlSupport = SysRoleDynamicSqlSupport.class, tableProperty = "sysRole")
public class SysRole extends BaseEntity implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "role_id", jdbcType = JDBCType.BIGINT)
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name", jdbcType = JDBCType.VARCHAR)
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code", jdbcType = JDBCType.VARCHAR)
    private String roleCode;

    /**
     * 序号
     */
    @Column(name = "role_sort", jdbcType = JDBCType.DECIMAL)
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据
     */
    @Column(name = "data_scope_type", jdbcType = JDBCType.TINYINT)
    private DataScopeTypeEnum dataScopeType;

    /**
     * 状态：1-启用，2-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 备注
     */
    @Column(name = "remark", jdbcType = JDBCType.VARCHAR)
    private String remark;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    /**
     * 是否是系统角色:Y-是,N-否
     */
    @Column(name = "role_system_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum roleSystemFlag;

    /**
     * 字典:角色类型
     */
    @Column(name = "role_type_code", jdbcType = JDBCType.VARCHAR)
    private String roleTypeCode;

    private static final long serialVersionUID = 1L;

    public SysRole roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public SysRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public SysRole roleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public SysRole roleSort(BigDecimal roleSort) {
        this.roleSort = roleSort;
        return this;
    }

    public SysRole dataScopeType(DataScopeTypeEnum dataScopeType) {
        this.dataScopeType = dataScopeType;
        return this;
    }

    public SysRole statusFlag(StatusEnum statusFlag) {
        this.statusFlag = statusFlag;
        return this;
    }

    public SysRole remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SysRole delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SysRole roleSystemFlag(YesOrNotEnum roleSystemFlag) {
        this.roleSystemFlag = roleSystemFlag;
        return this;
    }

    public SysRole roleTypeCode(String roleTypeCode) {
        this.roleTypeCode = roleTypeCode;
        return this;
    }
}
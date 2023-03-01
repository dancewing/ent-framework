package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
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
 * 角色数据范围
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_role_data_scope", sqlSupport = SysRoleDataScopeDynamicSqlSupport.class, tableProperty = "sysRoleDataScope")
public class SysRoleDataScope extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "role_data_scope_id", jdbcType = JDBCType.BIGINT)
    private Long roleDataScopeId;

    /**
     * 角色id
     */
    @Column(name = "role_id", jdbcType = JDBCType.BIGINT)
    private Long roleId;

    /**
     * 机构id
     */
    @Column(name = "organization_id", jdbcType = JDBCType.BIGINT)
    private Long organizationId;

    private static final long serialVersionUID = 1L;

    public SysRoleDataScope roleDataScopeId(Long roleDataScopeId) {
        this.roleDataScopeId = roleDataScopeId;
        return this;
    }

    public SysRoleDataScope roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public SysRoleDataScope organizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }
}
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
 * 角色资源关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_role_resource", sqlSupport = SysRoleResourceDynamicSqlSupport.class, tableProperty = "sysRoleResource")
public class SysRoleResource extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "role_resource_id", jdbcType = JDBCType.BIGINT)
    private Long roleResourceId;

    /**
     * 角色id
     */
    @Column(name = "role_id", jdbcType = JDBCType.BIGINT)
    private Long roleId;

    /**
     * 资源编码
     */
    @Column(name = "resource_code", jdbcType = JDBCType.VARCHAR)
    private String resourceCode;

    private static final long serialVersionUID = 1L;

    public SysRoleResource roleResourceId(Long roleResourceId) {
        this.roleResourceId = roleResourceId;
        return this;
    }

    public SysRoleResource roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public SysRoleResource resourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
        return this;
    }
}
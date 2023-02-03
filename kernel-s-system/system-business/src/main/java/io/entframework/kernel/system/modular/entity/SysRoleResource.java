package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色资源关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_role_resource")
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
}
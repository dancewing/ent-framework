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
 * 角色数据范围
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_role_data_scope")
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
}
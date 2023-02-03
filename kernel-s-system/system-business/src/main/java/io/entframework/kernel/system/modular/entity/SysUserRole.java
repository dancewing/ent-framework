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
 * 用户角色关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_user_role")
public class SysUserRole extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "user_role_id", jdbcType = JDBCType.BIGINT)
    private Long userRoleId;

    /**
     * 用户id
     */
    @Column(name = "user_id", jdbcType = JDBCType.BIGINT)
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id", jdbcType = JDBCType.BIGINT)
    private Long roleId;

    private static final long serialVersionUID = 1L;
}
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
 * 角色菜单关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_role_menu")
public class SysRoleMenu extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "role_menu_id", jdbcType = JDBCType.BIGINT)
    private Long roleMenuId;

    /**
     * 角色id
     */
    @Column(name = "role_id", jdbcType = JDBCType.BIGINT)
    private Long roleId;

    /**
     * 菜单id
     */
    @Column(name = "menu_id", jdbcType = JDBCType.BIGINT)
    private Long menuId;

    private static final long serialVersionUID = 1L;
}
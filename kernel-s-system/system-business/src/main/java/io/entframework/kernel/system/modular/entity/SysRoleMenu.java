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
 * 角色菜单关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_role_menu", sqlSupport = SysRoleMenuDynamicSqlSupport.class, tableProperty = "sysRoleMenu")
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

    public SysRoleMenu roleMenuId(Long roleMenuId) {
        this.roleMenuId = roleMenuId;
        return this;
    }

    public SysRoleMenu roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public SysRoleMenu menuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }
}
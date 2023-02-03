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
 * 菜单资源绑定
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_menu_resource")
public class SysMenuResource extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "menu_resource_id", jdbcType = JDBCType.BIGINT)
    private Long menuResourceId;

    /**
     * 菜单或按钮id
     */
    @Column(name = "menu_id", jdbcType = JDBCType.BIGINT)
    private Long menuId;

    /**
     * 资源的编码
     */
    @Column(name = "resource_code", jdbcType = JDBCType.VARCHAR)
    private String resourceCode;

    private static final long serialVersionUID = 1L;
}
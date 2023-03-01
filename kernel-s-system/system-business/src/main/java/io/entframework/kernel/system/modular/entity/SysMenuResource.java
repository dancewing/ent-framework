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
 * 菜单资源绑定
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_menu_resource", sqlSupport = SysMenuResourceDynamicSqlSupport.class,
		tableProperty = "sysMenuResource")
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

	public SysMenuResource menuResourceId(Long menuResourceId) {
		this.menuResourceId = menuResourceId;
		return this;
	}

	public SysMenuResource menuId(Long menuId) {
		this.menuId = menuId;
		return this;
	}

	public SysMenuResource resourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
		return this;
	}

}
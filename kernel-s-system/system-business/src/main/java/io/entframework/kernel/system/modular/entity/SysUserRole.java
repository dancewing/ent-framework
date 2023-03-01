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
 * 用户角色关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_user_role", sqlSupport = SysUserRoleDynamicSqlSupport.class, tableProperty = "sysUserRole")
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

	public SysUserRole userRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
		return this;
	}

	public SysUserRole userId(Long userId) {
		this.userId = userId;
		return this;
	}

	public SysUserRole roleId(Long roleId) {
		this.roleId = roleId;
		return this;
	}

}
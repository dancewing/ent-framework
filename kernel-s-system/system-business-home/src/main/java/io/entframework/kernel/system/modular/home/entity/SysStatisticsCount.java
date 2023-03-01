package io.entframework.kernel.system.modular.home.entity;

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
 * 常用功能的统计次数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_statistics_count", sqlSupport = SysStatisticsCountDynamicSqlSupport.class,
		tableProperty = "sysStatisticsCount")
public class SysStatisticsCount extends BaseEntity implements Serializable {

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "stat_count_id", jdbcType = JDBCType.BIGINT)
	private Long statCountId;

	/**
	 * 用户id
	 */
	@Column(name = "user_id", jdbcType = JDBCType.BIGINT)
	private Long userId;

	/**
	 * 访问的地址
	 */
	@Column(name = "stat_url_id", jdbcType = JDBCType.BIGINT)
	private Long statUrlId;

	/**
	 * 访问的次数
	 */
	@Column(name = "stat_count", jdbcType = JDBCType.INTEGER)
	private Integer statCount;

	private static final long serialVersionUID = 1L;

	public SysStatisticsCount statCountId(Long statCountId) {
		this.statCountId = statCountId;
		return this;
	}

	public SysStatisticsCount userId(Long userId) {
		this.userId = userId;
		return this;
	}

	public SysStatisticsCount statUrlId(Long statUrlId) {
		this.statUrlId = statUrlId;
		return this;
	}

	public SysStatisticsCount statCount(Integer statCount) {
		this.statCount = statCount;
		return this;
	}

}
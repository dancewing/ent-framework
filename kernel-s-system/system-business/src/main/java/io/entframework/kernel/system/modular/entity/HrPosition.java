package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * 职位信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "hr_position", sqlSupport = HrPositionDynamicSqlSupport.class, tableProperty = "hrPosition")
public class HrPosition extends BaseEntity implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@Column(name = "position_id", jdbcType = JDBCType.BIGINT)
	private Long positionId;

	/**
	 * 职位名称
	 */
	@Column(name = "position_name", jdbcType = JDBCType.VARCHAR)
	private String positionName;

	/**
	 * 职位编码
	 */
	@Column(name = "position_code", jdbcType = JDBCType.VARCHAR)
	private String positionCode;

	/**
	 * 排序
	 */
	@Column(name = "position_sort", jdbcType = JDBCType.DECIMAL)
	private BigDecimal positionSort;

	/**
	 * 状态：1-启用，2-禁用
	 */
	@Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
	private StatusEnum statusFlag;

	/**
	 * 备注
	 */
	@Column(name = "position_remark", jdbcType = JDBCType.VARCHAR)
	private String positionRemark;

	/**
	 * 删除标记：Y-已删除，N-未删除
	 */
	@Column(name = "del_flag", jdbcType = JDBCType.CHAR)
	@LogicDelete
	private YesOrNotEnum delFlag;

	private static final long serialVersionUID = 1L;

	public HrPosition positionId(Long positionId) {
		this.positionId = positionId;
		return this;
	}

	public HrPosition positionName(String positionName) {
		this.positionName = positionName;
		return this;
	}

	public HrPosition positionCode(String positionCode) {
		this.positionCode = positionCode;
		return this;
	}

	public HrPosition positionSort(BigDecimal positionSort) {
		this.positionSort = positionSort;
		return this;
	}

	public HrPosition statusFlag(StatusEnum statusFlag) {
		this.statusFlag = statusFlag;
		return this;
	}

	public HrPosition positionRemark(String positionRemark) {
		this.positionRemark = positionRemark;
		return this;
	}

	public HrPosition delFlag(YesOrNotEnum delFlag) {
		this.delFlag = delFlag;
		return this;
	}

}
package io.entframework.kernel.db.mds.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.JoinColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.annotation.OneToMany;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 班级
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "exam_class_grade", sqlSupport = ClassGradeDynamicSqlSupport.class, tableProperty = "classGrade")
public class ClassGrade extends BaseEntity implements Serializable {

	/**
	 * ID
	 */
	@Id
	@Column(name = "id", jdbcType = JDBCType.BIGINT)
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name", jdbcType = JDBCType.VARCHAR)
	private String name;

	/**
	 * 描述
	 */
	@Column(name = "description", jdbcType = JDBCType.VARCHAR)
	private String description;

	/**
	 * 类型[ADVANCE(0):高级,COMMON(1):普通]
	 */
	@Column(name = "grade_type", jdbcType = JDBCType.VARCHAR)
	private GradeType gradeType;

	/**
	 * 开学时间
	 */
	@Column(name = "start_time", jdbcType = JDBCType.TIMESTAMP)
	private LocalDateTime startTime;

	/**
	 * 班主任
	 */
	@Column(name = "regulator_id", jdbcType = JDBCType.BIGINT)
	private Long regulatorId;

	/**
	 * 学生
	 */
	@OneToMany
	@JoinColumn(target = Student.class, left = "id", right = "gradeId")
	private List<Student> students;

	/**
	 * 班主任
	 */
	@ManyToOne
	@JoinColumn(target = Teacher.class, left = "regulatorId", right = "id")
	private Teacher regulator;

	private static final long serialVersionUID = 1L;

	public ClassGrade id(Long id) {
		this.id = id;
		return this;
	}

	public ClassGrade name(String name) {
		this.name = name;
		return this;
	}

	public ClassGrade description(String description) {
		this.description = description;
		return this;
	}

	public ClassGrade gradeType(GradeType gradeType) {
		this.gradeType = gradeType;
		return this;
	}

	public ClassGrade startTime(LocalDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public ClassGrade regulatorId(Long regulatorId) {
		this.regulatorId = regulatorId;
		return this;
	}

	@EnumHandler
	public enum GradeType {

		ADVANCE("0", "高级"), COMMON("1", "普通");

		@JsonValue
		@EnumValue
		private final String value;

		private final String label;

		GradeType(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return this.value;
		}

		public String value() {
			return this.value;
		}

		public String getLabel() {
			return this.label;
		}

		@JsonCreator
		public static GradeType parseValue(String value) {
			if (value != null) {
				for (GradeType item : values()) {
					if (item.value.equals(value)) {
						return item;
					}
				}
			}
			return null;
		}

	}

}
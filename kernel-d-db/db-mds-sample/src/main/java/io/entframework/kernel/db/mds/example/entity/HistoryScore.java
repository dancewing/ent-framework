package io.entframework.kernel.db.mds.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.db.dao.mybatis.handler.ScoreMapHandler;
import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 考试记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "exam_history_score", sqlSupport = HistoryScoreDynamicSqlSupport.class, tableProperty = "historyScore")
public class HistoryScore extends BaseEntity implements Serializable {

	/**
	 * Id
	 */
	@Id
	@Column(name = "id", jdbcType = JDBCType.BIGINT)
	private Long id;

	/**
	 * 学生ID
	 */
	@Column(name = "student_id", jdbcType = JDBCType.BIGINT)
	private Long studentId;

	/**
	 * 考试时间
	 */
	@Column(name = "exam_time", jdbcType = JDBCType.TIMESTAMP)
	private LocalDateTime examTime;

	/**
	 * 考试类型[MONTHLY(0):月考,MID_TERM(1):期中,FINAL(2):期末]
	 */
	@Column(name = "exam_type", jdbcType = JDBCType.VARCHAR)
	private ExamType examType;

	/**
	 * 总分数
	 */
	@Column(name = "total_score", jdbcType = JDBCType.INTEGER)
	private Integer totalScore;

	/**
	 * 分数
	 */
	@Column(name = "score", jdbcType = JDBCType.LONGVARCHAR, typeHandler = ScoreMapHandler.class)
	private Map<String, Integer> score;

	private static final long serialVersionUID = 1L;

	public HistoryScore id(Long id) {
		this.id = id;
		return this;
	}

	public HistoryScore studentId(Long studentId) {
		this.studentId = studentId;
		return this;
	}

	public HistoryScore examTime(LocalDateTime examTime) {
		this.examTime = examTime;
		return this;
	}

	public HistoryScore examType(ExamType examType) {
		this.examType = examType;
		return this;
	}

	public HistoryScore totalScore(Integer totalScore) {
		this.totalScore = totalScore;
		return this;
	}

	public HistoryScore score(Map<String, Integer> score) {
		this.score = score;
		return this;
	}

	@EnumHandler
	public enum ExamType {

		MONTHLY("0", "月考"), MID_TERM("1", "期中"), FINAL("2", "期末");

		@JsonValue
		@EnumValue
		private final String value;

		private final String label;

		ExamType(String value, String label) {
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
		public static ExamType parseValue(String value) {
			if (value != null) {
				for (ExamType item : values()) {
					if (item.value.equals(value)) {
						return item;
					}
				}
			}
			return null;
		}

	}

}
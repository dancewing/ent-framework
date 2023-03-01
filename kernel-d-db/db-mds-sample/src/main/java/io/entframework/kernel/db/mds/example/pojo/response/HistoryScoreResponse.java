package io.entframework.kernel.db.mds.example.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.db.mds.example.entity.HistoryScore.ExamType;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 考试记录 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HistoryScoreResponse extends BaseResponse {

	/**
	 * Id
	 */
	@ChineseDescription("Id")
	private Long id;

	/**
	 * 学生ID
	 */
	@ChineseDescription("学生ID")
	private Long studentId;

	/**
	 * 考试时间
	 */
	@ChineseDescription("考试时间")
	private LocalDateTime examTime;

	/**
	 * 考试类型[MONTHLY(0):月考,MID_TERM(1):期中,FINAL(2):期末]
	 */
	@ChineseDescription("考试类型")
	private ExamType examType;

	/**
	 * 总分数
	 */
	@ChineseDescription("总分数")
	private Integer totalScore;

	/**
	 * 分数
	 */
	@ChineseDescription("分数")
	private Map<String, Integer> score;

}
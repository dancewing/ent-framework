package io.entframework.kernel.db.mds.example.pojo.request;

import io.entframework.kernel.db.mds.example.entity.HistoryScore.ExamType;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 考试记录 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HistoryScoreRequest extends BaseRequest {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
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

    @NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
    @ChineseDescription("ID集合")
    private java.util.List<Long> ids;

}
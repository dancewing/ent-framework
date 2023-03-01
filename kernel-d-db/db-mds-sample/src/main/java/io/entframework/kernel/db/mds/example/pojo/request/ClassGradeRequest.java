package io.entframework.kernel.db.mds.example.pojo.request;

import io.entframework.kernel.db.mds.example.entity.ClassGrade.GradeType;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 班级 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassGradeRequest extends BaseRequest {

	/**
	 * ID
	 */
	@NotNull(message = "ID不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("ID")
	private Long id;

	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空", groups = { add.class, update.class })
	@ChineseDescription("名称")
	private String name;

	/**
	 * 描述
	 */
	@ChineseDescription("描述")
	private String description;

	/**
	 * 类型[ADVANCE(0):高级,COMMON(1):普通]
	 */
	@ChineseDescription("类型")
	private GradeType gradeType;

	/**
	 * 开学时间
	 */
	@ChineseDescription("开学时间")
	private LocalDateTime startTime;

	/**
	 * 班主任
	 */
	@ChineseDescription("班主任")
	private Long regulatorId;

	/**
	 * 学生
	 */
	@ChineseDescription("学生")
	private List<StudentRequest> students;

	/**
	 * 班主任
	 */
	@ChineseDescription("班主任")
	private TeacherRequest regulator;

	@NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
	@ChineseDescription("ID集合")
	private List<Long> ids;

}
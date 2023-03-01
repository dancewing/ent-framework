package io.entframework.kernel.db.mds.example.pojo.request;

import io.entframework.kernel.db.mds.example.entity.Student.Gender;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 学生 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest extends BaseRequest {

	/**
	 * Id
	 */
	@NotNull(message = "Id不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("Id")
	private Long id;

	/**
	 * 班级ID
	 */
	@ChineseDescription("班级ID")
	private Long gradeId;

	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空", groups = { add.class, update.class })
	@ChineseDescription("姓名")
	private String name;

	/**
	 * 卡号
	 */
	@NotBlank(message = "卡号不能为空", groups = { add.class, update.class })
	@ChineseDescription("卡号")
	private String cardNum;

	/**
	 * 性别[MALE(0):男,FEMALE(1):女]
	 */
	@ChineseDescription("性别")
	private Gender gender;

	/**
	 * 生日
	 */
	@ChineseDescription("生日")
	private LocalDate birthday;

	/**
	 * 学习课程
	 */
	@ChineseDescription("学习课程")
	private List<String> takeCourses;

	/**
	 * 外国学生
	 */
	@ChineseDescription("外国学生")
	private Boolean fromForeign;

	/**
	 * 籍贯
	 */
	@ChineseDescription("籍贯")
	private String hometown;

	/**
	 * 版本
	 */
	@ChineseDescription("版本")
	private Long version;

	/**
	 * 爱好
	 */
	@ChineseDescription("爱好")
	private String hobbies;

	@NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
	@ChineseDescription("ID集合")
	private List<Long> ids;

}
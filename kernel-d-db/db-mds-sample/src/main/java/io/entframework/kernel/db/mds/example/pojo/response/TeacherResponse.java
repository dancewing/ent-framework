package io.entframework.kernel.db.mds.example.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.db.mds.example.entity.Teacher.Gender;
import io.entframework.kernel.db.mds.ext.dto.TeachProperty;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 教师 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse extends BaseResponse {

	/**
	 * Id
	 */
	@ChineseDescription("Id")
	private Long id;

	/**
	 * 姓名
	 */
	@ChineseDescription("姓名")
	private String name;

	/**
	 * 卡号
	 */
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
	 * 级别
	 */
	@ChineseDescription("级别")
	private Integer workSeniority;

	/**
	 * 状态
	 */
	@ChineseDescription("状态")
	private StatusEnum statusFlag;

	/**
	 * 教授课程
	 */
	@ChineseDescription("教授课程")
	private List<String> techCourses;

	/**
	 * 版本
	 */
	@ChineseDescription("版本")
	private Long version;

	/**
	 * 属性
	 */
	@ChineseDescription("属性")
	private TeachProperty properties;

}
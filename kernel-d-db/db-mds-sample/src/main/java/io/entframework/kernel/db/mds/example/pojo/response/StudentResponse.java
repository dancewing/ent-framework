package io.entframework.kernel.db.mds.example.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.db.mds.example.entity.Student.Gender;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 学生 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse extends BaseResponse {
    /**
     * Id
     */
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
}
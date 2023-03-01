package io.entframework.kernel.db.mds.example.pojo.request;

import io.entframework.kernel.db.mds.example.entity.Teacher.Gender;
import io.entframework.kernel.db.mds.ext.dto.TeachProperty;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
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
 * 教师 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest extends BaseRequest {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
    @ChineseDescription("Id")
    private Long id;

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

    @NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
    @ChineseDescription("ID集合")
    private List<Long> ids;

}
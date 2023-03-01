package io.entframework.kernel.db.mds.example.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.db.mds.example.entity.ClassGrade.GradeType;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 班级 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassGradeResponse extends BaseResponse {

    /**
     * ID
     */
    @ChineseDescription("ID")
    private Long id;

    /**
     * 名称
     */
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
    private List<StudentResponse> students;

    /**
     * 班主任
     */
    @ChineseDescription("班主任")
    private TeacherResponse regulator;

}
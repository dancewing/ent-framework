package io.entframework.kernel.db.mds.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.JoinColumn;
import io.entframework.kernel.db.api.annotation.ManyToOne;
import io.entframework.kernel.db.api.annotation.OneToMany;
import io.entframework.kernel.db.api.annotation.Table;
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

/**
 * 班级
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("exam_class_grade")
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

    @EnumHandler
    public enum GradeType {
        ADVANCE("0", "高级"),
        COMMON("1", "普通");

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
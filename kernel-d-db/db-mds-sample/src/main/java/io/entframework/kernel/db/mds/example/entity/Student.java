package io.entframework.kernel.db.mds.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.db.dao.mybatis.handler.StringListHandler;
import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;
import org.mybatis.dynamic.sql.annotation.Version;

/**
 * 学生
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "exam_student", sqlSupport = StudentDynamicSqlSupport.class, tableProperty = "student")
public class Student extends BaseEntity implements Serializable {
    /**
     * Id
     */
    @Id
    @Column(name = "id", jdbcType = JDBCType.BIGINT)
    private Long id;

    /**
     * 班级ID
     */
    @Column(name = "grade_id", jdbcType = JDBCType.BIGINT)
    private Long gradeId;

    /**
     * 姓名
     */
    @Column(name = "name", jdbcType = JDBCType.VARCHAR)
    private String name;

    /**
     * 卡号
     */
    @Column(name = "card_num", jdbcType = JDBCType.VARCHAR)
    private String cardNum;

    /**
     * 性别[MALE(0):男,FEMALE(1):女]
     */
    @Column(name = "gender", jdbcType = JDBCType.VARCHAR)
    private Gender gender;

    /**
     * 生日
     */
    @Column(name = "birthday", jdbcType = JDBCType.DATE)
    private LocalDate birthday;

    /**
     * 学习课程
     */
    @Column(name = "take_courses", jdbcType = JDBCType.VARCHAR, typeHandler = StringListHandler.class)
    private List<String> takeCourses;

    /**
     * 外国学生
     */
    @Column(name = "from_foreign", jdbcType = JDBCType.BIT)
    private Boolean fromForeign;

    /**
     * 籍贯
     */
    @Column(name = "hometown", jdbcType = JDBCType.VARCHAR)
    private String hometown;

    /**
     * 删除标记
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    /**
     * 版本
     */
    @Column(name = "version_", jdbcType = JDBCType.BIGINT)
    @Version
    private Long version;

    /**
     * 爱好
     */
    @Column(name = "hobbies", jdbcType = JDBCType.LONGVARCHAR)
    private String hobbies;

    private static final long serialVersionUID = 1L;

    @EnumHandler
    public enum Gender {
        MALE("0", "男"),
        FEMALE("1", "女");

        @JsonValue
        @EnumValue
        private final String value;

        private final String label;

        Gender(String value, String label) {
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
        public static Gender parseValue(String value) {
            if (value != null) {
                for (Gender item : values()) {
                    if (item.value.equals(value)) {
                        return item;
                    }
                }
            }
            return null;
        }
    }
}
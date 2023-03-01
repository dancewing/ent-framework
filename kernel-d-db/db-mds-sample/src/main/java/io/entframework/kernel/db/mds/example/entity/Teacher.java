package io.entframework.kernel.db.mds.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.db.dao.mybatis.handler.StringListHandler;
import io.entframework.kernel.db.mds.ext.dto.TeachProperty;
import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.StatusEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;
import org.mybatis.dynamic.sql.annotation.Version;

/**
 * 教师
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "exam_teacher", sqlSupport = TeacherDynamicSqlSupport.class, tableProperty = "teacher")
public class Teacher extends BaseEntity implements Serializable {

    /**
     * Id
     */
    @Id
    @Column(name = "id", jdbcType = JDBCType.BIGINT)
    private Long id;

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
     * 级别
     */
    @Column(name = "work_seniority", jdbcType = JDBCType.INTEGER)
    private Integer workSeniority;

    /**
     * 状态
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 教授课程
     */
    @Column(name = "tech_courses", jdbcType = JDBCType.VARCHAR, typeHandler = StringListHandler.class)
    private List<String> techCourses;

    /**
     * 版本
     */
    @Column(name = "version_", jdbcType = JDBCType.BIGINT)
    @Version
    private Long version;

    /**
     * 属性
     */
    @Column(name = "properties", jdbcType = JDBCType.LONGVARBINARY)
    private TeachProperty properties;

    private static final long serialVersionUID = 1L;

    public Teacher id(Long id) {
        this.id = id;
        return this;
    }

    public Teacher name(String name) {
        this.name = name;
        return this;
    }

    public Teacher cardNum(String cardNum) {
        this.cardNum = cardNum;
        return this;
    }

    public Teacher gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Teacher birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public Teacher workSeniority(Integer workSeniority) {
        this.workSeniority = workSeniority;
        return this;
    }

    public Teacher statusFlag(StatusEnum statusFlag) {
        this.statusFlag = statusFlag;
        return this;
    }

    public Teacher techCourses(List<String> techCourses) {
        this.techCourses = techCourses;
        return this;
    }

    public Teacher version(Long version) {
        this.version = version;
        return this;
    }

    public Teacher properties(TeachProperty properties) {
        this.properties = properties;
        return this;
    }

    @EnumHandler
    public enum Gender {

        MALE("0", "男"), FEMALE("1", "女");

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
package io.entframework.kernel.db.mds.example.entity;

import io.entframework.kernel.db.mds.example.entity.Student.Gender;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class StudentDynamicSqlSupport {
    public static final Student student = new Student();

    public static final SqlColumn<Long> id = student.id;

    public static final SqlColumn<Long> gradeId = student.gradeId;

    public static final SqlColumn<String> name = student.name;

    public static final SqlColumn<String> cardNum = student.cardNum;

    public static final SqlColumn<Gender> gender = student.gender;

    public static final SqlColumn<LocalDate> birthday = student.birthday;

    public static final SqlColumn<List<String>> takeCourses = student.takeCourses;

    public static final SqlColumn<Boolean> fromForeign = student.fromForeign;

    public static final SqlColumn<String> hometown = student.hometown;

    public static final SqlColumn<YesOrNotEnum> delFlag = student.delFlag;

    public static final SqlColumn<Long> version = student.version;

    public static final SqlColumn<LocalDateTime> createTime = student.createTime;

    public static final SqlColumn<Long> createUser = student.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = student.updateTime;

    public static final SqlColumn<Long> updateUser = student.updateUser;

    public static final SqlColumn<String> createUserName = student.createUserName;

    public static final SqlColumn<String> updateUserName = student.updateUserName;

    public static final SqlColumn<String> hobbies = student.hobbies;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, gradeId, name, cardNum, gender, birthday, takeCourses, fromForeign, hometown, delFlag, version, createTime, createUser, updateTime, updateUser, createUserName, updateUserName, hobbies);

    public static final class Student extends AliasableSqlTable<Student> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> gradeId = column("grade_id", JDBCType.BIGINT);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> cardNum = column("card_num", JDBCType.VARCHAR);

        public final SqlColumn<Gender> gender = column("gender", JDBCType.VARCHAR);

        public final SqlColumn<LocalDate> birthday = column("birthday", JDBCType.DATE);

        public final SqlColumn<List<String>> takeCourses = column("take_courses", JDBCType.VARCHAR, "io.entframework.kernel.db.api.handler.StringListHandler");

        public final SqlColumn<Boolean> fromForeign = column("from_foreign", JDBCType.BIT);

        public final SqlColumn<String> hometown = column("hometown", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<Long> version = column("version_", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> hobbies = column("hobbies", JDBCType.LONGVARCHAR);

        public Student() {
            super("exam_student", Student::new);
        }
    }
}
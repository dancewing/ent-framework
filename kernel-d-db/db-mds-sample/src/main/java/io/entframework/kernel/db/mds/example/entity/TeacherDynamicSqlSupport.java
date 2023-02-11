package io.entframework.kernel.db.mds.example.entity;

import io.entframework.kernel.db.mds.example.entity.Teacher.Gender;
import io.entframework.kernel.db.mds.ext.dto.TeachProperty;
import io.entframework.kernel.rule.enums.StatusEnum;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TeacherDynamicSqlSupport {
    public static final Teacher teacher = new Teacher();

    public static final SqlColumn<Long> id = teacher.id;

    public static final SqlColumn<String> name = teacher.name;

    public static final SqlColumn<String> cardNum = teacher.cardNum;

    public static final SqlColumn<Gender> gender = teacher.gender;

    public static final SqlColumn<LocalDate> birthday = teacher.birthday;

    public static final SqlColumn<Integer> workSeniority = teacher.workSeniority;

    public static final SqlColumn<StatusEnum> statusFlag = teacher.statusFlag;

    public static final SqlColumn<List<String>> techCourses = teacher.techCourses;

    public static final SqlColumn<Long> version = teacher.version;

    public static final SqlColumn<LocalDateTime> createTime = teacher.createTime;

    public static final SqlColumn<Long> createUser = teacher.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = teacher.updateTime;

    public static final SqlColumn<Long> updateUser = teacher.updateUser;

    public static final SqlColumn<String> createUserName = teacher.createUserName;

    public static final SqlColumn<String> updateUserName = teacher.updateUserName;

    public static final SqlColumn<TeachProperty> properties = teacher.properties;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, name, cardNum, gender, birthday, workSeniority, statusFlag, techCourses, version, createTime, createUser, updateTime, updateUser, createUserName, updateUserName, properties);

    public static final class Teacher extends AliasableSqlTable<Teacher> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> cardNum = column("card_num", JDBCType.VARCHAR);

        public final SqlColumn<Gender> gender = column("gender", JDBCType.VARCHAR);

        public final SqlColumn<LocalDate> birthday = column("birthday", JDBCType.DATE);

        public final SqlColumn<Integer> workSeniority = column("work_seniority", JDBCType.INTEGER);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<List<String>> techCourses = column("tech_courses", JDBCType.VARCHAR, "io.entframework.kernel.db.api.handler.StringListHandler");

        public final SqlColumn<Long> version = column("version_", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public final SqlColumn<TeachProperty> properties = column("properties", JDBCType.LONGVARBINARY);

        public Teacher() {
            super("exam_teacher", Teacher::new);
        }
    }
}
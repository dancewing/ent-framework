package io.entframework.kernel.db.mds.example.entity;

import io.entframework.kernel.db.mds.example.entity.ClassGrade.GradeType;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ClassGradeDynamicSqlSupport {
    public static final ClassGrade classGrade = new ClassGrade();

    public static final SqlColumn<Long> id = classGrade.id;

    public static final SqlColumn<String> name = classGrade.name;

    public static final SqlColumn<String> description = classGrade.description;

    public static final SqlColumn<GradeType> gradeType = classGrade.gradeType;

    public static final SqlColumn<LocalDateTime> startTime = classGrade.startTime;

    public static final SqlColumn<Long> regulatorId = classGrade.regulatorId;

    public static final SqlColumn<LocalDateTime> createTime = classGrade.createTime;

    public static final SqlColumn<Long> createUser = classGrade.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = classGrade.updateTime;

    public static final SqlColumn<Long> updateUser = classGrade.updateUser;

    public static final SqlColumn<String> createUserName = classGrade.createUserName;

    public static final SqlColumn<String> updateUserName = classGrade.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, name, description, gradeType, startTime, regulatorId, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class ClassGrade extends AliasableSqlTable<ClassGrade> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("description", JDBCType.VARCHAR);

        public final SqlColumn<GradeType> gradeType = column("grade_type", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> regulatorId = column("regulator_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public ClassGrade() {
            super("exam_class_grade", ClassGrade::new);
        }
    }
}
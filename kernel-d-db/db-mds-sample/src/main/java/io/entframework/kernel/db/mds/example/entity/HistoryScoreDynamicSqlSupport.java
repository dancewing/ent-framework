package io.entframework.kernel.db.mds.example.entity;

import io.entframework.kernel.db.mds.example.entity.HistoryScore.ExamType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.Map;

public final class HistoryScoreDynamicSqlSupport {
    public static final HistoryScore historyScore = new HistoryScore();

    public static final SqlColumn<Long> id = historyScore.id;

    public static final SqlColumn<Long> studentId = historyScore.studentId;

    public static final SqlColumn<LocalDateTime> examTime = historyScore.examTime;

    public static final SqlColumn<ExamType> examType = historyScore.examType;

    public static final SqlColumn<Integer> totalScore = historyScore.totalScore;

    public static final SqlColumn<LocalDateTime> createTime = historyScore.createTime;

    public static final SqlColumn<Long> createUser = historyScore.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = historyScore.updateTime;

    public static final SqlColumn<Long> updateUser = historyScore.updateUser;

    public static final SqlColumn<String> createUserName = historyScore.createUserName;

    public static final SqlColumn<String> updateUserName = historyScore.updateUserName;

    public static final SqlColumn<Map<String, Integer>> score = historyScore.score;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, studentId, examTime, examType, totalScore, createTime, createUser, updateTime, updateUser, createUserName, updateUserName, score);

    public static final class HistoryScore extends AliasableSqlTable<HistoryScore> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> studentId = column("student_id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> examTime = column("exam_time", JDBCType.TIMESTAMP);

        public final SqlColumn<ExamType> examType = column("exam_type", JDBCType.VARCHAR);

        public final SqlColumn<Integer> totalScore = column("total_score", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public final SqlColumn<Map<String, Integer>> score = column("score", JDBCType.LONGVARCHAR, "io.entframework.kernel.db.dao.mybatis.handler.ScoreMapHandler");

        public HistoryScore() {
            super("exam_history_score", HistoryScore::new);
        }
    }
}
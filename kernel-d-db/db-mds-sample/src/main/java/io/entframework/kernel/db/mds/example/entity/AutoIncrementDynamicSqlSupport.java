package io.entframework.kernel.db.mds.example.entity;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class AutoIncrementDynamicSqlSupport {
    public static final AutoIncrement autoIncrement = new AutoIncrement();

    public static final SqlColumn<Integer> id = autoIncrement.id;

    public static final SqlColumn<String> username = autoIncrement.username;

    public static final BasicColumn[] selectList = BasicColumn.columnList(id, username);

    public static final class AutoIncrement extends AliasableSqlTable<AutoIncrement> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> username = column("username", JDBCType.VARCHAR);

        public AutoIncrement() {
            super("exam_auto_increment", AutoIncrement::new);
        }
    }
}
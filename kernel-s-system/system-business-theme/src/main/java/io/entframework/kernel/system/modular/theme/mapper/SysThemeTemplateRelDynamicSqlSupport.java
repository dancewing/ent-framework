package io.entframework.kernel.system.modular.theme.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysThemeTemplateRelDynamicSqlSupport {
    public static final SysThemeTemplateRel sysThemeTemplateRel = new SysThemeTemplateRel();

    public static final SqlColumn<Long> relationId = sysThemeTemplateRel.relationId;

    public static final SqlColumn<Long> templateId = sysThemeTemplateRel.templateId;

    public static final SqlColumn<String> fieldCode = sysThemeTemplateRel.fieldCode;

    public static final SqlColumn<LocalDateTime> createTime = sysThemeTemplateRel.createTime;

    public static final SqlColumn<Long> createUser = sysThemeTemplateRel.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysThemeTemplateRel.updateTime;

    public static final SqlColumn<Long> updateUser = sysThemeTemplateRel.updateUser;

    public static final SqlColumn<String> createUserName = sysThemeTemplateRel.createUserName;

    public static final SqlColumn<String> updateUserName = sysThemeTemplateRel.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(relationId, templateId, fieldCode, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysThemeTemplateRel extends AliasableSqlTable<SysThemeTemplateRel> {
        public final SqlColumn<Long> relationId = column("relation_id", JDBCType.BIGINT);

        public final SqlColumn<Long> templateId = column("template_id", JDBCType.BIGINT);

        public final SqlColumn<String> fieldCode = column("field_code", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysThemeTemplateRel() {
            super("sys_theme_template_rel", SysThemeTemplateRel::new);
        }
    }
}
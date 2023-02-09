package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.TemplateTypeEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysThemeTemplateDynamicSqlSupport {
    public static final SysThemeTemplate sysThemeTemplate = new SysThemeTemplate();

    public static final SqlColumn<Long> templateId = sysThemeTemplate.templateId;

    public static final SqlColumn<String> templateName = sysThemeTemplate.templateName;

    public static final SqlColumn<String> templateCode = sysThemeTemplate.templateCode;

    public static final SqlColumn<TemplateTypeEnum> templateType = sysThemeTemplate.templateType;

    public static final SqlColumn<YesOrNotEnum> statusFlag = sysThemeTemplate.statusFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysThemeTemplate.createTime;

    public static final SqlColumn<Long> createUser = sysThemeTemplate.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysThemeTemplate.updateTime;

    public static final SqlColumn<Long> updateUser = sysThemeTemplate.updateUser;

    public static final SqlColumn<String> createUserName = sysThemeTemplate.createUserName;

    public static final SqlColumn<String> updateUserName = sysThemeTemplate.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(templateId, templateName, templateCode, templateType, statusFlag, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysThemeTemplate extends AliasableSqlTable<SysThemeTemplate> {
        public final SqlColumn<Long> templateId = column("template_id", JDBCType.BIGINT);

        public final SqlColumn<String> templateName = column("template_name", JDBCType.VARCHAR);

        public final SqlColumn<String> templateCode = column("template_code", JDBCType.VARCHAR);

        public final SqlColumn<TemplateTypeEnum> templateType = column("template_type", JDBCType.TINYINT);

        public final SqlColumn<YesOrNotEnum> statusFlag = column("status_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysThemeTemplate() {
            super("sys_theme_template", SysThemeTemplate::new);
        }
    }
}
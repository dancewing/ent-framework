package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 系统主题
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_theme", sqlSupport = SysThemeDynamicSqlSupport.class, tableProperty = "sysTheme")
public class SysTheme extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "theme_id", jdbcType = JDBCType.BIGINT)
    private Long themeId;

    /**
     * 主题名称
     */
    @Column(name = "theme_name", jdbcType = JDBCType.VARCHAR)
    private String themeName;

    /**
     * 主题模板id
     */
    @Column(name = "template_id", jdbcType = JDBCType.BIGINT)
    private Long templateId;

    /**
     * 是否启用：Y-启用，N-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum statusFlag;

    /**
     * 主题属性，json格式
     */
    @Column(name = "theme_value", jdbcType = JDBCType.LONGVARCHAR)
    private String themeValue;

    private static final long serialVersionUID = 1L;
}
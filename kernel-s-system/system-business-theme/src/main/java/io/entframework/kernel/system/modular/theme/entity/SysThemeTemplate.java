package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.TemplateTypeEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统主题-模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_theme_template")
public class SysThemeTemplate extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "template_id", jdbcType = JDBCType.BIGINT)
    private Long templateId;

    /**
     * 主题名称
     */
    @Column(name = "template_name", jdbcType = JDBCType.VARCHAR)
    private String templateName;

    /**
     * 主题编码
     */
    @Column(name = "template_code", jdbcType = JDBCType.VARCHAR)
    private String templateCode;

    /**
     * 主题类型：1-系统类型，2-业务类型
     */
    @Column(name = "template_type", jdbcType = JDBCType.TINYINT)
    private TemplateTypeEnum templateType;

    /**
     * 启用状态：Y-启用，N-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum statusFlag;

    private static final long serialVersionUID = 1L;
}
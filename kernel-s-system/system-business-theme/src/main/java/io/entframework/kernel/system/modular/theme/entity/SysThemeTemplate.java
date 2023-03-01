package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.TemplateTypeEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 系统主题-模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_theme_template", sqlSupport = SysThemeTemplateDynamicSqlSupport.class,
        tableProperty = "sysThemeTemplate")
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

    public SysThemeTemplate templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public SysThemeTemplate templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public SysThemeTemplate templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public SysThemeTemplate templateType(TemplateTypeEnum templateType) {
        this.templateType = templateType;
        return this;
    }

    public SysThemeTemplate statusFlag(YesOrNotEnum statusFlag) {
        this.statusFlag = statusFlag;
        return this;
    }

}
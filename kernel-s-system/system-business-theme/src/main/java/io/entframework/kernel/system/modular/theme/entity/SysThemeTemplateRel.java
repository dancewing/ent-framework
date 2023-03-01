package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
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
 * 系统主题-模板配置关联关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_theme_template_rel", sqlSupport = SysThemeTemplateRelDynamicSqlSupport.class,
        tableProperty = "sysThemeTemplateRel")
public class SysThemeTemplateRel extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "relation_id", jdbcType = JDBCType.BIGINT)
    private Long relationId;

    /**
     * 模板主键id
     */
    @Column(name = "template_id", jdbcType = JDBCType.BIGINT)
    private Long templateId;

    /**
     * 属性编码
     */
    @Column(name = "field_code", jdbcType = JDBCType.VARCHAR)
    private String fieldCode;

    private static final long serialVersionUID = 1L;

    public SysThemeTemplateRel relationId(Long relationId) {
        this.relationId = relationId;
        return this;
    }

    public SysThemeTemplateRel templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public SysThemeTemplateRel fieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
        return this;
    }

}
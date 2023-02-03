package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统主题-模板配置关联关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_theme_template_rel")
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
}
package io.entframework.kernel.system.modular.theme.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;
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
 * 系统主题-模板属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_theme_template_field", sqlSupport = SysThemeTemplateFieldDynamicSqlSupport.class, tableProperty = "sysThemeTemplateField")
public class SysThemeTemplateField extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "field_id", jdbcType = JDBCType.BIGINT)
    private Long fieldId;

    /**
     * 属性名称
     */
    @Column(name = "field_name", jdbcType = JDBCType.VARCHAR)
    private String fieldName;

    /**
     * 属性编码
     */
    @Column(name = "field_code", jdbcType = JDBCType.VARCHAR)
    private String fieldCode;

    /**
     * 属性展示类型（字典维护），例如：图片，文本等类型
     */
    @Column(name = "field_type", jdbcType = JDBCType.VARCHAR)
    private ThemeFieldTypeEnum fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @Column(name = "field_required", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum fieldRequired;

    /**
     * 属性值长度
     */
    @Column(name = "field_length", jdbcType = JDBCType.INTEGER)
    private Integer fieldLength;

    /**
     * 属性描述
     */
    @Column(name = "field_description", jdbcType = JDBCType.VARCHAR)
    private String fieldDescription;

    private static final long serialVersionUID = 1L;
}
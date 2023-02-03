package io.entframework.kernel.i18n.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 多语言
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_translation")
public class Translation extends BaseEntity {
    /**
     * 主键id
     */
    @Id
    @Column(name = "tran_id", jdbcType = JDBCType.BIGINT)
    private Long tranId;

    /**
     * 编码
     */
    @Column(name = "tran_code", jdbcType = JDBCType.VARCHAR)
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @Column(name = "tran_name", jdbcType = JDBCType.VARCHAR)
    private String tranName;

    /**
     * 语种字典
     */
    @Column(name = "tran_language_code", jdbcType = JDBCType.VARCHAR)
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @Column(name = "tran_value", jdbcType = JDBCType.VARCHAR)
    private String tranValue;
}
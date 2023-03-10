package io.entframework.kernel.i18n.modular.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
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
 * 多语言
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_translation", sqlSupport = TranslationDynamicSqlSupport.class, tableProperty = "translation")
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

    public Translation tranId(Long tranId) {
        this.tranId = tranId;
        return this;
    }

    public Translation tranCode(String tranCode) {
        this.tranCode = tranCode;
        return this;
    }

    public Translation tranName(String tranName) {
        this.tranName = tranName;
        return this;
    }

    public Translation tranLanguageCode(String tranLanguageCode) {
        this.tranLanguageCode = tranLanguageCode;
        return this;
    }

    public Translation tranValue(String tranValue) {
        this.tranValue = tranValue;
        return this;
    }

}
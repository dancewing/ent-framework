package io.entframework.kernel.dict.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.dict.api.enums.DictTypeClassEnum;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
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
 * 字典类型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_dict_type", sqlSupport = SysDictTypeDynamicSqlSupport.class, tableProperty = "sysDictType")
public class SysDictType extends BaseEntity {
    /**
     * 字典类型id
     */
    @Id
    @Column(name = "dict_type_id", jdbcType = JDBCType.BIGINT)
    private Long dictTypeId;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @Column(name = "dict_type_class", jdbcType = JDBCType.INTEGER)
    private DictTypeClassEnum dictTypeClass;

    /**
     * 字典类型业务编码
     */
    @Column(name = "dict_type_bus_code", jdbcType = JDBCType.VARCHAR)
    private String dictTypeBusCode;

    /**
     * 字典类型编码
     */
    @Column(name = "dict_type_code", jdbcType = JDBCType.VARCHAR)
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @Column(name = "dict_type_name", jdbcType = JDBCType.VARCHAR)
    private String dictTypeName;

    /**
     * 字典类型名称首字母拼音
     */
    @Column(name = "dict_type_name_pinyin", jdbcType = JDBCType.VARCHAR)
    private String dictTypeNamePinyin;

    /**
     * 字典类型描述
     */
    @Column(name = "dict_type_desc", jdbcType = JDBCType.VARCHAR)
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 排序，带小数点
     */
    @Column(name = "dict_type_sort", jdbcType = JDBCType.DECIMAL)
    private BigDecimal dictTypeSort;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    public SysDictType dictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
        return this;
    }

    public SysDictType dictTypeClass(DictTypeClassEnum dictTypeClass) {
        this.dictTypeClass = dictTypeClass;
        return this;
    }

    public SysDictType dictTypeBusCode(String dictTypeBusCode) {
        this.dictTypeBusCode = dictTypeBusCode;
        return this;
    }

    public SysDictType dictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
        return this;
    }

    public SysDictType dictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
        return this;
    }

    public SysDictType dictTypeNamePinyin(String dictTypeNamePinyin) {
        this.dictTypeNamePinyin = dictTypeNamePinyin;
        return this;
    }

    public SysDictType dictTypeDesc(String dictTypeDesc) {
        this.dictTypeDesc = dictTypeDesc;
        return this;
    }

    public SysDictType statusFlag(StatusEnum statusFlag) {
        this.statusFlag = statusFlag;
        return this;
    }

    public SysDictType dictTypeSort(BigDecimal dictTypeSort) {
        this.dictTypeSort = dictTypeSort;
        return this;
    }

    public SysDictType delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }
}
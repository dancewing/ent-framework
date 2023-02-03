package io.entframework.kernel.dict.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.annotation.Table;
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

/**
 * 字典类型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_dict_type")
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
}
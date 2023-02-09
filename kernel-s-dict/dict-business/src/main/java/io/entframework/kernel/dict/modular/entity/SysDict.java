package io.entframework.kernel.dict.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 字典
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_dict", sqlSupport = SysDictDynamicSqlSupport.class, tableProperty = "sysDict")
public class SysDict extends BaseEntity {
    /**
     * 字典id
     */
    @Id
    @Column(name = "dict_id", jdbcType = JDBCType.BIGINT)
    private Long dictId;

    /**
     * 字典编码
     */
    @Column(name = "dict_code", jdbcType = JDBCType.VARCHAR)
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", jdbcType = JDBCType.VARCHAR)
    private String dictName;

    /**
     * 字典名称首字母
     */
    @Column(name = "dict_name_pinyin", jdbcType = JDBCType.VARCHAR)
    private String dictNamePinyin;

    /**
     * 字典编码
     */
    @Column(name = "dict_encode", jdbcType = JDBCType.VARCHAR)
    private String dictEncode;

    /**
     * 字典类型的编码
     */
    @Column(name = "dict_type_code", jdbcType = JDBCType.VARCHAR)
    private String dictTypeCode;

    /**
     * 字典简称
     */
    @Column(name = "dict_short_name", jdbcType = JDBCType.VARCHAR)
    private String dictShortName;

    /**
     * 字典简称的编码
     */
    @Column(name = "dict_short_code", jdbcType = JDBCType.VARCHAR)
    private String dictShortCode;

    /**
     * 上级字典的id(如果没有上级字典id，则为-1)
     */
    @Column(name = "dict_parent_id", jdbcType = JDBCType.BIGINT)
    private Long dictParentId;

    /**
     * 状态：(1-启用,2-禁用),参考 StatusEnum
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 排序，带小数点
     */
    @Column(name = "dict_sort", jdbcType = JDBCType.DECIMAL)
    private BigDecimal dictSort;

    /**
     * 父id集合
     */
    @Column(name = "dict_pids", jdbcType = JDBCType.VARCHAR)
    private String dictPids;

    /**
     * 是否删除，Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;
}
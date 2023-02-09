package io.entframework.kernel.config.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 参数配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_config", sqlSupport = SysConfigDynamicSqlSupport.class, tableProperty = "sysConfig")
public class SysConfig extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "config_id", jdbcType = JDBCType.BIGINT)
    private Long configId;

    /**
     * 名称
     */
    @Column(name = "config_name", jdbcType = JDBCType.VARCHAR)
    private String configName;

    /**
     * 属性编码
     */
    @Column(name = "config_code", jdbcType = JDBCType.VARCHAR)
    private String configCode;

    /**
     * 属性值
     */
    @Column(name = "config_value", jdbcType = JDBCType.VARCHAR)
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @Column(name = "sys_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum sysFlag;

    /**
     * 备注
     */
    @Column(name = "remark", jdbcType = JDBCType.VARCHAR)
    private String remark;

    /**
     * 状态：1-正常，2-停用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @Column(name = "group_code", jdbcType = JDBCType.VARCHAR)
    private String groupCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;
}
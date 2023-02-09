package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
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
 * 用户数据范围
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "sys_user_data_scope", sqlSupport = SysUserDataScopeDynamicSqlSupport.class, tableProperty = "sysUserDataScope")
public class SysUserDataScope extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "user_data_scope_id", jdbcType = JDBCType.BIGINT)
    private Long userDataScopeId;

    /**
     * 用户id
     */
    @Column(name = "user_id", jdbcType = JDBCType.BIGINT)
    private Long userId;

    /**
     * 机构id
     */
    @Column(name = "org_id", jdbcType = JDBCType.BIGINT)
    private Long orgId;

    private static final long serialVersionUID = 1L;
}
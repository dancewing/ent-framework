package io.entframework.kernel.system.modular.entity;

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
 * 用户数据范围
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_user_data_scope")
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
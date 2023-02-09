/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.api.pojo.entity;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;
import org.mybatis.dynamic.sql.annotation.Column;

import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDateTime;

/**
 * 实体的基础类
 *
 * @date 2020/10/14 18:08
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @ChineseDescription("创建时间")
    @Column(name = "create_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ChineseDescription("创建人")
    @Column(name = "create_user", jdbcType = JDBCType.BIGINT)
    private Long createUser;

    /**
     * 创建人名称
     */
    @ChineseDescription("创建人名称")
    @Column(name = "create_user_name", jdbcType = JDBCType.VARCHAR)
    private String createUserName;

    /**
     * 更新时间
     */
    @ChineseDescription("更新时间")
    @Column(name = "update_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @ChineseDescription("更新人")
    @Column(name = "update_user", jdbcType = JDBCType.BIGINT)
    private Long updateUser;

    /**
     * 更新人名称
     */
    @ChineseDescription("更新人名称")
    @Column(name = "update_user_name", jdbcType = JDBCType.VARCHAR)
    private String updateUserName;
}

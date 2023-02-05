/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.mds.dboperator;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.db.api.DbOperatorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据库操作的实现
 *
 * @date 2020/11/4 14:48
 */
public class DbOperatorImpl implements DbOperatorApi {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long selectCount(String sql, Object... args) {
        Long count = this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        }, args);
        return count == null ? 0L : count;
    }

    @Override
    public Set<Long> findSubListByParentId(String tableName, String parentIdsFieldName, String keyFieldName, Long keyFieldValue) {

        // 组装sql
        String sqlTemplate = "select {} from {} where {} like '%[{}]%'";
        String sql = CharSequenceUtil.format(sqlTemplate, keyFieldName, tableName, parentIdsFieldName, keyFieldValue.toString());
        List<Long>  subIds = this.jdbcTemplate.queryForList(sql, Long.class);
        // 查询所有子级的id集合，结果不包含被查询的keyFieldValue
        // 转为Set<Long>
        return subIds.stream().map(i -> Long.valueOf(i.toString())).collect(Collectors.toSet());
    }

}

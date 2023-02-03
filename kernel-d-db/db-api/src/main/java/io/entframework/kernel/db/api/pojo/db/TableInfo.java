/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.db.api.pojo.db;

import lombok.Data;

/**
 * 表的基本信息
 *
 * @date 2021/5/19 10:47
 */
@Data
public class TableInfo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表的注释
     */
    private String tableComment;

}

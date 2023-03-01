/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.db.api.DbOperatorApi;

/**
 * 获取sql操作器
 *
 * @date 2020/11/4 15:07
 */
public class DbOperatorContext {

    public static DbOperatorApi me() {
        return SpringUtil.getBean(DbOperatorApi.class);
    }

}

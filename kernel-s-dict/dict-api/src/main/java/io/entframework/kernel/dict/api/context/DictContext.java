/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.dict.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.dict.api.DictApi;

/**
 * 字典模块，对外的api
 *
 * @date 2020/10/29 11:39
 */
public class DictContext {

    /**
     * 获取字典相关操作接口
     *
     * @date 2020/10/29 11:55
     */
    public static DictApi me() {
        return SpringUtil.getBean(DictApi.class);
    }

}

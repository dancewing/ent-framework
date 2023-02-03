/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.jwt.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.jwt.api.JwtApi;

/**
 * Jwt工具的context，获取容器中的jwt工具类
 *
 * @date 2020/10/21 14:07
 */
public class JwtContext {

    /**
     * 获取jwt操作接口
     *
     * @date 2020/10/21 14:07
     */
    public static JwtApi me() {
        return SpringUtil.getBean(JwtApi.class);
    }

}

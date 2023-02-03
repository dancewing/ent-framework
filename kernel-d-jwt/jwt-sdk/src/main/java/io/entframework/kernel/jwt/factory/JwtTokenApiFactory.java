/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.jwt.factory;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.jwt.JwtTokenOperator;
import io.entframework.kernel.jwt.api.JwtApi;
import io.entframework.kernel.jwt.api.exception.JwtException;
import io.entframework.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import io.entframework.kernel.jwt.api.config.JwtProperties;


/**
 * jwt token操作工具的生产工厂
 *
 * @date 2021/1/21 18:15
 */
public class JwtTokenApiFactory {

    /**
     * 根据jwt秘钥和过期时间，获取jwt操作的工具
     *
     * @date 2021/1/21 18:16
     */
    public static JwtApi createJwtApi(String jwtSecret, Integer expiredSeconds) {

        if (ObjectUtil.hasEmpty(jwtSecret, expiredSeconds)) {
            throw new JwtException(JwtExceptionEnum.JWT_PARAM_EMPTY);
        }

        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setJwtSecret(jwtSecret);
        jwtProperties.setExpiredSeconds(expiredSeconds.longValue());

        return new JwtTokenOperator(jwtProperties);
    }

}

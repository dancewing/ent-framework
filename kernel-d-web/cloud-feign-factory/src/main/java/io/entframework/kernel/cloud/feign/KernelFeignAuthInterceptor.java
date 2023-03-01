/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cloud.feign;

import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.config.AuthConfigProperties;
import io.entframework.kernel.rule.function.Try;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.Resource;

@Slf4j
public class KernelFeignAuthInterceptor implements RequestInterceptor {

    @Resource
    private AuthConfigProperties authConfigProperties;

    @Resource
    private LoginUserApi loginUserApi;

    @Override
    public void apply(RequestTemplate template) {
        Try<String> tokeTry = Try.call(() -> loginUserApi.getToken());
        tokeTry.ifSuccess((token) -> {
            log.info("get token successfull, and pass to feign request header");
            template.header(authConfigProperties.getTokenHeaderName(), token);
        }).ifFailure((ex) -> {
            log.error("can't get token, {}", ex.getMessage());
        });
    }

}

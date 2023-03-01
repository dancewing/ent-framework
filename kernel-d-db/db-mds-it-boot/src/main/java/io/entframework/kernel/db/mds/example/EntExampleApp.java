/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example;

import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.auth.api.constants.LoginCacheConstants;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.redis.RedisCacheManagerFactory;
import io.entframework.kernel.cache.redis.operator.DefaultRedisCacheOperator;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication(scanBasePackages = "io.entframework.kernel.db.mds.example.ext")
public class EntExampleApp {

	public static void main(String[] args) {
		SpringApplication.run(EntExampleApp.class, args);
	}

	@Bean(name = LoginCacheConstants.LOGIN_USER_CACHE_BEAN_NAME)
	public CacheOperatorApi<LoginUser> loginUserCache(RedisCacheManagerFactory redisCacheManagerFactory) {
		return new DefaultRedisCacheOperator<>(redisCacheManagerFactory
				.createCache(LoginCacheConstants.LOGIN_USER_CACHE_BEAN_NAME, AuthConstants.LOGGED_TOKEN_PREFIX));
	}

	@Bean
	@Scope("prototype")
	public StudentRequest studentRequest() {
		return new StudentRequest();
	}

}

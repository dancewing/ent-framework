/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.gateway.config;

import io.entframework.ent.gateway.handler.SwaggerResourceHandler;
import io.entframework.ent.gateway.props.AuthProperties;
import io.entframework.ent.gateway.props.RouteProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 路由配置信息
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({ RouteProperties.class, AuthProperties.class })
public class RouterFunctionConfiguration {

	private final SwaggerResourceHandler swaggerResourceHandler;

	/**
	 * 这里为支持的请求头，如果有自定义的header字段请自己添加
	 */
	private static final String ALLOWED_HEADERS = "X-Requested-With, Content-Type, Authorization, credential, X-XSRF-TOKEN, token, username, client";

	private static final String ALLOWED_METHODS = "*";

	private static final String ALLOWED_ORIGIN = "*";

	private static final String ALLOWED_EXPOSE = "*";

	private static final String MAX_AGE = "18000L";

	private static final String ALLOWED_HEADERS_KEY = "Access-Control-Allow-Headers";

	private static final String ALLOWED_METHODS_KEY = "Access-Control-Allow-Methods";

	private static final String ALLOWED_ORIGIN_KEY = "Access-Control-Allow-Origin";

	private static final String ALLOWED_EXPOSE_KEY = "Access-Control-Expose-Headers";

	private static final String MAX_AGE_KEY = "Access-Control-Max-Age";

	private static final String ALLOWED_CREDENTIALS_KEY = "Access-Control-Allow-Credentials";

	/**
	 * 跨域配置
	 */
	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
				HttpHeaders headers = response.getHeaders();
				if (!headers.containsKey(ALLOWED_HEADERS_KEY)) {
					headers.add(ALLOWED_HEADERS_KEY, ALLOWED_HEADERS);
				}
				if (!headers.containsKey(ALLOWED_METHODS_KEY)) {
					headers.add(ALLOWED_METHODS_KEY, ALLOWED_METHODS);
				}
				if (!headers.containsKey(ALLOWED_ORIGIN_KEY)) {
					headers.add(ALLOWED_ORIGIN_KEY, ALLOWED_ORIGIN);
				}
				if (!headers.containsKey(ALLOWED_EXPOSE_KEY)) {
					headers.add(ALLOWED_EXPOSE_KEY, ALLOWED_EXPOSE);
				}
				if (!headers.containsKey(MAX_AGE_KEY)) {
					headers.add(MAX_AGE_KEY, MAX_AGE);
				}
				if (!headers.containsKey(ALLOWED_CREDENTIALS_KEY)) {
					headers.add(ALLOWED_CREDENTIALS_KEY, "true");
				}

				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}
			return chain.filter(ctx);
		};
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route(
				RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)),
				swaggerResourceHandler);

	}

	/**
	 * 解决 Only one connection receive subscriber allowed.
	 * 参考：https://github.com/spring-cloud/spring-cloud-gateway/issues/541
	 */
	@Bean
	@ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter() {
			@Override
			@NotNull
			public Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {
				return chain.filter(exchange);
			}
		};
	}

}

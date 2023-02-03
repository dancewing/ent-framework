/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.ent.gateway.props.AuthProperties;
import io.entframework.ent.gateway.provider.AuthProvider;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 鉴权认证
 * @author jeff_qian
 */
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
	@Resource
	private AuthProperties authProperties;
	@Resource
	private SessionManagerApi sessionManagerApi;

	@Resource(name = ResourceConstants.RESOURCE_CACHE_BEAN_NAME)
	private CacheOperatorApi<ResourceDefinition> resourceCache;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		if (path.startsWith("/gw/")) {
			return chain.filter(exchange);
		}
		if (isSkip(path)) {
			return chain.filter(exchange);
		}

		String token = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_HEAD_KEY);
		if (StringUtils.isBlank(token)) {
			token = exchange.getRequest().getQueryParams().getFirst(AuthProvider.AUTH_PARAM_KEY);
		}

		// 如果token不为空，则先判断是否登录过期了，过期了就直接打回，不过期不做处理
		if (CharSequenceUtil.isNotBlank(token)) {
			try {
				sessionManagerApi.validateToken(token);
			} catch (AuthException authException) {
				if (AuthExceptionEnum.AUTH_EXPIRED_ERROR.getErrorCode().equals(authException.getErrorCode())) {
					sessionManagerApi.destroySessionCookie();
					throw authException;
				}
			}
			// 刷新用户的session的过期时间
			sessionManagerApi.refreshSession(token);
		}

		ResourceDefinition resourceDefinition = resourceCache.get(path);
		if (resourceDefinition != null) {
			// 如果需要登录
			if (resourceDefinition.getRequiredLoginFlag() != null && resourceDefinition.getRequiredLoginFlag()) {

				// token为空，返回用户校验失败
				if (CharSequenceUtil.isEmpty(token)) {
					throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
				}

				// 3.校验token和用户会话信息是否正确
				sessionManagerApi.checkAuth(token, path);
			}

			//如果需要检查权限
			if (resourceDefinition.getRequiredPermissionFlag() != null && resourceDefinition.getRequiredPermissionFlag()) {
				// 1. 校验token是否传参
				if (CharSequenceUtil.isEmpty(token)) {
					throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
				}

				// 2. 获取token对应的用户信息
				LoginUser session = sessionManagerApi.getSession(token);
				if (session == null) {
					throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
				}

				// 3. 验证用户有没有当前url的权限
				Set<String> resourceUrls = session.getResourceUrls();
				if (resourceUrls == null || resourceUrls.isEmpty()) {
					throw new AuthException(AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR);
				} else {
					if (!resourceUrls.contains(path)) {
						throw new AuthException(AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR);
					}
				}
			}
		} else {
			throw new AuthException(AuthExceptionEnum.RESOURCE_DEFINITION_ERROR);
		}
		return chain.filter(exchange);
	}

	private boolean isSkip(String path) {
		return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith)
			|| authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith);
	}

	@Override
	public int getOrder() {
		return -100;
	}

}

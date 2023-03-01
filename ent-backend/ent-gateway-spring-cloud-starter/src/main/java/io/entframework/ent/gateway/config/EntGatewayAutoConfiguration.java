package io.entframework.ent.gateway.config;

import io.entframework.ent.gateway.filter.AuthFilter;
import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.auth.api.constants.LoginCacheConstants;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.redis.RedisCacheManagerFactory;
import io.entframework.kernel.cache.redis.operator.DefaultRedisCacheOperator;
import io.entframework.kernel.cache.redis.operator.DefaultRedisHashCacheOperator;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author jeff_qian
 */
@Configuration
@ImportAutoConfiguration({ ErrorHandlerConfiguration.class, RouterFunctionConfiguration.class,
		SwaggerResourceConfiguration.class })
public class EntGatewayAutoConfiguration {

	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}

	@Bean(name = LoginCacheConstants.LOGIN_USER_CACHE_BEAN_NAME)
	public CacheOperatorApi<LoginUser> loginUserCache(RedisCacheManagerFactory redisCacheManagerFactory) {
		return new DefaultRedisCacheOperator<>(redisCacheManagerFactory
				.createCache(LoginCacheConstants.LOGIN_USER_CACHE_BEAN_NAME, AuthConstants.LOGGED_TOKEN_PREFIX));
	}

	@Bean(name = LoginCacheConstants.ALL_USER_LOGIN_TOKEN_CACHE_BEAN_NAME)
	public CacheOperatorApi<Set<String>> allUserLoginTokenCache(RedisCacheManagerFactory redisCacheManagerFactory) {
		return new DefaultRedisCacheOperator<>(redisCacheManagerFactory.createCache(
				LoginCacheConstants.ALL_USER_LOGIN_TOKEN_CACHE_BEAN_NAME, AuthConstants.LOGGED_USERID_PREFIX));
	}

	@Bean(name = ResourceConstants.RESOURCE_CACHE_BEAN_NAME)
	public CacheOperatorApi<ResourceDefinition> resourceCache(RedisCacheManagerFactory redisCacheManagerFactory) {
		return new DefaultRedisHashCacheOperator<>(redisCacheManagerFactory
				.createCache(ResourceConstants.RESOURCE_CACHE_BEAN_NAME, ScannerConstants.RESOURCE_CACHE_KEY));
	}

}

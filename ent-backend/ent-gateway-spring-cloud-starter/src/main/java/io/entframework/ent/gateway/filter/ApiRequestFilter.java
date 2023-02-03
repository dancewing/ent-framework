package io.entframework.ent.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 全局拦截器，作用所有的微服务，验证访问规则，排除internal的接口
 * 非本地环境才生效
 * <p>
 */
@Profile("!local")
@Component
public class ApiRequestFilter implements GlobalFilter, Ordered {

    private static final String INTERNAL_API_PREFIX = "internal";

    private static final Integer NUMBER_2 = 2;

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //如果访问service的internal 接口，gateway直接返回错误
        ServerHttpRequest request = exchange.getRequest().mutate().build();

        String rawPath = request.getURI().getRawPath();
        String[] paths = StringUtils.split(rawPath, "/");
        if (paths != null && paths.length >= NUMBER_2 && INTERNAL_API_PREFIX.equalsIgnoreCase(paths[1])) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            DataBuffer buff = response.bufferFactory().wrap(HttpStatus.BAD_REQUEST.getReasonPhrase().getBytes());
            return response.writeWith(Mono.just(buff));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}

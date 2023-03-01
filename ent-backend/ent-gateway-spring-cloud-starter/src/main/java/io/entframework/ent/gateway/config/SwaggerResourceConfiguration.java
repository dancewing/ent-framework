package io.entframework.ent.gateway.config;

import io.entframework.ent.gateway.filter.FixXForwardedHeadersFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author jeff_qian
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({})
public class SwaggerResourceConfiguration {

    private static final String SWAGGER_API = "/v2/api-docs";

    /**
     * 修复local 模式下，带gw转发时，header设置不正确的问题
     * @return
     */
    @Profile({ "local" })
    @Bean
    @Primary
    public HttpHeadersFilter xForwardedHeadersFilter() {
        log.info("fix header prefix is enable in local!");
        return new FixXForwardedHeadersFilter();
    }

    @Profile({ "local_dev", "test" })
    @Bean
    @Primary
    public XForwardedHeadersFilter fixNginxforwardedHeadersFilter() {
        log.info("fix header prefix is enable in test!");
        return new XForwardedHeadersFilter() {

            @Override
            public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
                HttpHeaders headers = super.filter(input, exchange);

                ServerHttpRequest request = exchange.getRequest();
                String rawPath = request.getPath().value();
                if (StringUtils.startsWith(rawPath, SWAGGER_API)) {
                    headers.remove(XForwardedHeadersFilter.X_FORWARDED_PREFIX_HEADER);
                }
                return headers;
            }
        };
    }

}

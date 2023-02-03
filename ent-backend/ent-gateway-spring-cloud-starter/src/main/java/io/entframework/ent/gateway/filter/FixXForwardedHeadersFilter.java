package io.entframework.ent.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author jeff_qian
 */
public class FixXForwardedHeadersFilter extends XForwardedHeadersFilter {
    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        HttpHeaders headers = super.filter(input, exchange);
        if (headers.containsKey(XForwardedHeadersFilter.X_FORWARDED_PREFIX_HEADER)) {
            String value = headers.getFirst(XForwardedHeadersFilter.X_FORWARDED_PREFIX_HEADER);
            String fixValue = StringUtils.replace(value, ",", "");
            headers.set(XForwardedHeadersFilter.X_FORWARDED_PREFIX_HEADER,  fixValue);
        }
        return headers;
    }
}

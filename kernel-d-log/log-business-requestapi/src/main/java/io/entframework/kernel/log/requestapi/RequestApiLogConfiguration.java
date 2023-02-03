package io.entframework.kernel.log.requestapi;

import io.entframework.kernel.log.api.LogRecordApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestApiLogConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "kernel.sys-log", name = "request-log-enabled", matchIfMissing = true)
    public RequestApiLogRecordAop requestApiLogRecordAop(LogRecordApi logRecordApi) {
        return new RequestApiLogRecordAop(logRecordApi);
    }
}

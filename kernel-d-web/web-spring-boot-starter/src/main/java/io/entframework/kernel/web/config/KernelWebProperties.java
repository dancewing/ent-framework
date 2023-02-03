package io.entframework.kernel.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kernel.web")
@Data
public class KernelWebProperties {
    private String dateTimeFormat = WebConstants.DEFAULT_DATE_TIME_FORMAT;
    private String dateFormat = WebConstants.DEFAULT_DATE_FORMAT;
    private String timeFormat = WebConstants.DEFAULT_TIME_FORMAT;
}

package io.entframework.kernel.security.api.config;

import io.entframework.kernel.security.api.constants.SecurityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SecurityConstants.SECURITY_PREFIX)
@Data
public class KernelSecurityProperties {

	public boolean cosEnabled = true;

}

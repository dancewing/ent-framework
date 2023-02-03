package io.entframework.kernel.auth.api.config;

import io.entframework.kernel.auth.api.constants.AuthConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kernel.auth")
public class AuthConfigProperties {
    /**
     * 获取携带token的param传参的名称
     */
    private String tokenParamName = AuthConstants.DEFAULT_AUTH_PARAM_NAME;
    /**
     * 获取携带token的header头的名称
     */
    private String tokenHeaderName = AuthConstants.DEFAULT_AUTH_HEADER_NAME;
    /**
     * 会话保存在cookie中时，cooke的name
     */
    private String sessionCookieName = AuthConstants.DEFAULT_AUTH_HEADER_NAME;
    /**
     * 获取session过期时间，默认3600秒
     * <p>
     * 在这个时段内不操作，会将用户踢下线，从新登陆
     * <p>
     * 如果开启了记住我功能，在session过期后会从新创建session
     */
    private long sessionExpiredSeconds = 3600L;
}

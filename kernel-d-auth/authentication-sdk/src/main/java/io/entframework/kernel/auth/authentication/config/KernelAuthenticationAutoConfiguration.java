/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.authentication.config;

import io.entframework.kernel.auth.api.AuthServiceApi;
import io.entframework.kernel.auth.api.password.PasswordStoredEncryptApi;
import io.entframework.kernel.auth.api.password.PasswordTransferEncryptApi;
import io.entframework.kernel.auth.api.pojo.SsoProperties;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.authentication.auth.AuthServiceImpl;
import io.entframework.kernel.auth.authentication.password.BcryptPasswordStoredEncrypt;
import io.entframework.kernel.auth.authentication.password.RsaPasswordTransferEncrypt;
import io.entframework.kernel.auth.authentication.timer.ClearInvalidLoginUserCacheTimer;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author jeff_qian
 * @date 2020/11/30 22:16
 */
@Configuration
@EnableConfigurationProperties({SsoProperties.class})
public class KernelAuthenticationAutoConfiguration {

    /**
     * Bcrypt方式的密码加密
     *
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordStoredEncryptApi.class)
    public PasswordStoredEncryptApi passwordStoredEncryptApi() {
        return new BcryptPasswordStoredEncrypt();
    }

    /**
     * RSA方式密码加密传输
     *
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordTransferEncryptApi.class)
    public PasswordTransferEncryptApi passwordTransferEncryptApi() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCytSVn3ff7eBJckAFYwgJjqE9Zq2uAL4g+hkfQqGALdT8NJKALFxNzeSD/xTBLAJrtALWbN1dvyktoVNPAuuzCZO1BxYZNaAU3IKFaj73OSPzca5SGY0ibMw0KvEPkC3sZQeqBqx+VqYAqan90BeG/r9p36Eb0wrshj5XmsFeo6QIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALK1JWfd9/t4ElyQAVjCAmOoT1mra4AviD6GR9CoYAt1Pw0koAsXE3N5IP/FMEsAmu0AtZs3V2/KS2hU08C67MJk7UHFhk1oBTcgoVqPvc5I/NxrlIZjSJszDQq8Q+QLexlB6oGrH5WpgCpqf3QF4b+v2nfoRvTCuyGPleawV6jpAgMBAAECgYBS9fUfetQcUWl0vwVhBu/FA+WSYxnMsEQ3gm7kVsX/i7Zxi4cgnt3QxXKkSg5ZQzaov6OPIuncY7UOAhMrbZtq/Hh8atdTVC/Ng/X9Bomodplq/+KTe/vIfWW5rlQAnMNFVaidxhCVRlRHNusapmj2vYwsiyI9kXUJNHryxtFC4QJBANtQuh3dtd79t3MVaC3TUD/EsGBe9TB3Eykbgv0muannC2Oq8Ci4vIp0NSA+FNCoB8ctgfKJUdBS8RLVnYyu3RcCQQDQmY+AuAXEpO9SgcYeRnQSOU2OSuC1wLt1MRVpPTdvE3bfRnkVxMrK0n3YilQWkQzfkERSG4kRFLIw605xPWn/AkEAiw3vQ9p8Yyu5MiXDjTKrchMyxZfPnHATXQANmJcCJ0DQDtymMxuWp66wtIXIStgPPnGTMAVzM0Qzh/6bS0Tf9wJAWj+1yFjVlghNyoJ+9qZAnYnRNhjLM5dZAxDjVI65pwLi0SKqTHLB0hJThBYE32aODUNba7KiEJPFrEiBvZh2fQJARbboHuHT0PqD1UTJGgodHlaw48kreBU+twext/9/jIqvwmFF88BmQgssHGW/tn4E6Qy3+rCCNWreEReY0gATYw==";
        return new RsaPasswordTransferEncrypt(publicKey, privateKey);
    }

    @Bean
    @ConditionalOnMissingBean(AuthServiceApi.class)
    public AuthServiceApi authServiceApi() {
        return new AuthServiceImpl();
    }

    /**
     * 清空无用登录用户缓存的定时任务
     *
     * @date 2021/3/30 11:32
     */
    @Bean
    @ConditionalOnMissingBean(ClearInvalidLoginUserCacheTimer.class)
    public ClearInvalidLoginUserCacheTimer clearInvalidLoginUserCacheTimer(CacheOperatorApi<LoginUser> loginUserCache, CacheOperatorApi<Set<String>> allUserLoginTokenCache) {
        return new ClearInvalidLoginUserCacheTimer(loginUserCache, allUserLoginTokenCache);
    }
}

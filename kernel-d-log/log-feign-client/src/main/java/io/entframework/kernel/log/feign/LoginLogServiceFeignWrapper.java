package io.entframework.kernel.log.feign;

import io.entframework.kernel.log.api.LoginLogServiceApi;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.rule.function.Try;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginLogServiceFeignWrapper implements LoginLogServiceApi {

    private final LoginLogServiceApi feignClient;

    public LoginLogServiceFeignWrapper(LoginLogServiceApi feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public boolean add(SysLoginLogRequest request) {
        return Try.call(() -> feignClient.add(request)).ifFailure(e -> {
            log.error("feignClient.log error: ", e);
        }).toOptional().orElse(false);
    }

}

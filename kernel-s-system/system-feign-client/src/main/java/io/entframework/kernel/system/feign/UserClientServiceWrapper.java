/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.feign;

import io.entframework.kernel.system.api.UserClientServiceApi;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;

import java.time.LocalDateTime;
import java.util.List;

public class UserClientServiceWrapper implements UserClientServiceApi {
    private final UserClientServiceApi feignClient;

    public UserClientServiceWrapper(UserClientServiceApi feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public Boolean userExist(Long userId) {
        return feignClient.userExist(userId);
    }

    @Override
    public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {
        return feignClient.queryAllUserIdList(sysUserRequest);
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {
        return feignClient.getUserLoginInfo(account);
    }

    @Override
    public void updateUserLoginInfo(Long userId, LocalDateTime date, String ip) {
        feignClient.updateUserLoginInfo(userId, date, ip);
    }

    @Override
    public SysUserResponse getUserInfoByUserId(Long userId) {
        return feignClient.getUserInfoByUserId(userId);
    }
}

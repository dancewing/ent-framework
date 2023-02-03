/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.authentication.timer;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.timer.api.TimerAction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 定时清除无用的登录用户缓存
 *
 * @date 2021/3/30 11:19
 */
public class ClearInvalidLoginUserCacheTimer implements TimerAction {

    /**
     * 登录用户缓存
     */
    private final CacheOperatorApi<LoginUser> loginUserCacheOperatorApi;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     */
    private final CacheOperatorApi<Set<String>> allUserLoginTokenCache;

    public ClearInvalidLoginUserCacheTimer(CacheOperatorApi<LoginUser> loginUserCacheOperatorApi, CacheOperatorApi<Set<String>> allUserLoginTokenCache) {
        this.loginUserCacheOperatorApi = loginUserCacheOperatorApi;
        this.allUserLoginTokenCache = allUserLoginTokenCache;
    }

    @Override
    public void action(String params) {
        Collection<String> allOnlineUsers = allUserLoginTokenCache.getAllKeys();
        if (ObjectUtil.isNotEmpty(allOnlineUsers)) {
            for (String userId : allOnlineUsers) {

                // 获取当前用户的所有token
                Set<String> userTokens = allUserLoginTokenCache.get(userId);

                // 新的userToken
                Set<String> newUserTokens = new HashSet<>();

                // 因为有的token用户没有点退出清空，这里遍历一下，清空无效的userToken
                for (String userToken : userTokens) {
                    LoginUser loginUser = loginUserCacheOperatorApi.get(userToken);
                    if (loginUser != null) {
                        newUserTokens.add(userToken);
                    }
                }

                // 如果userToken都过期了，这个set整体清除掉
                if (ObjectUtil.isEmpty(newUserTokens)) {
                    allUserLoginTokenCache.remove(userId);
                } else {
                    allUserLoginTokenCache.put(userId, newUserTokens);
                }
            }
        }
    }

}

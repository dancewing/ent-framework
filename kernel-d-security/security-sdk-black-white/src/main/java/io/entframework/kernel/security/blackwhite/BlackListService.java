/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.blackwhite;

import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.security.api.BlackListApi;

import java.util.Collection;

/**
 * 黑名单的实现
 * <p>
 * 黑名单的数据会在访问资源时被限制
 *
 * @date 2020/11/20 15:52
 */
public class BlackListService implements BlackListApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public BlackListService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public void addBlackItem(String content) {
        cacheOperatorApi.put(content, content);
    }

    @Override
    public void removeBlackItem(String content) {
        cacheOperatorApi.remove(content);
    }

    @Override
    public Collection<String> getBlackList() {
        return cacheOperatorApi.getAllKeys();
    }

    @Override
    public boolean contains(String content) {
        return cacheOperatorApi.contains(content);
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.api.config;

import org.apache.commons.lang3.StringUtils;

public enum CacheType {

    REDIS("redis"), MEMORY("memory");

    private String code;

    private CacheType(String code) {
        this.code = code;
    }

    public String resolve() {
        return this.code;
    }

    public static CacheType resolve(String code) {
        if (StringUtils.isNotBlank(code)) {
            CacheType[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                CacheType cacheType = var1[var3];
                if (StringUtils.equalsIgnoreCase(code, cacheType.code)) {
                    return cacheType;
                }
            }
        }
        return MEMORY;
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.constants;

/**
 * 缓存前缀相关的常量
 *
 * @date 2021/7/29 22:55
 */
public interface SystemCachesConstants {

    /**
     * 用户缓存的前缀
     */
    String USER_CACHE_PREFIX = "user:";

    /**
     * 用户缓存过期时间(1小时)
     */
    Long USER_CACHE_TIMEOUT_SECONDS = 3600L;

    /**
     * 用户绑定的角色的缓存前缀
     */
    String USER_ROLES_CACHE_PREFIX = "user_roles:";

    /**
     * 角色信息的缓存
     */
    String ROLE_INFO_CACHE_PREFIX = "role:";

    /**
     * 用户组织机构缓存的前缀
     */
    String USER_ORG_CACHE_PREFIX = "user_org:";

    /**
     * 角色绑定资源的缓存
     */
    String ROLE_RESOURCE_CACHE_PREFIX = "role_resource:";

    /**
     * 角色绑定的数据范围的缓存
     */
    String ROLE_DATA_SCOPE_CACHE_PREFIX = "role_data_scope:";

    /**
     * 系统主题的缓存
     */
    String SYSTEM_THEME_CACHE_PREFIX = "system_cache:";
}

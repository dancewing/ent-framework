/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.jwt.api.pojo.payload;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Map;

/**
 * jwt的载体，也就是jwt本身带的一些信息
 *
 * @date 2020/10/21 11:37
 */
@Data
public class DefaultJwtPayload {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 唯一表示id, 用于缓存登录用户的唯一凭证
     */
    private String uuid;

    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    /**
     * 过期时间戳
     */
    private Long expirationDate;

    /**
     * 单点认证中心的用户会话id，一般为一个uuid
     * <p>
     * 用来校验单点中心是否有本用户的会话
     */
    private String caToken;

    /**
     * 其他载体信息
     */
    private Map<String, Object> others;

    public DefaultJwtPayload() {
    }

    public DefaultJwtPayload(Long userId, String account, boolean rememberMe, String caToken) {
        this.userId = userId;
        this.account = account;
        this.uuid = IdUtil.fastUUID();
        this.rememberMe = rememberMe;
        this.caToken = caToken;
    }

}

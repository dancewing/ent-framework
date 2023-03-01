/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.jwt.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt相关的配置封装
 *
 * @author jeff_qian
 * @date 2020/10/21 11:37
 */
@Data
@ConfigurationProperties(prefix = "kernel.auth.jwt")
public class JwtProperties {

	/**
	 * jwt秘钥
	 */
	private String jwtSecret = "1928374650abcdefffasrrwafsfjasdiuer1233assssqqq99811sjkfassskddss";

	/**
	 * 过期时间（秒）
	 */
	private Long expiredSeconds = 259200L;

}

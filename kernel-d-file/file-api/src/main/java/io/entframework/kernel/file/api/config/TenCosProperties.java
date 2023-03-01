/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云cos文件存储配置
 *
 * @date 2020/10/26 11:49
 */
@Data
@ConfigurationProperties(prefix = "kernel.file.ten-cos")
public class TenCosProperties {

    /**
     * Bucket
     */
    private String bucket;

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 地域id（默认北京）
     */
    private String regionId = "ap-beijing";

}

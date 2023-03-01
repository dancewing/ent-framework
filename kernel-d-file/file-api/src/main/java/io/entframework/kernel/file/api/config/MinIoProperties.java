/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinIO文件存储配置
 *
 * @date 2020/10/31 11:19
 */
@Data
@ConfigurationProperties(prefix = "kernel.file.minio")
public class MinIoProperties {

    /**
     * Bucket
     */
    private String bucket;

    /**
     * 服务器端点 MinIO服务器地址 默认：http://127.0.0.1:9000
     */
    private String endpoint = "http://127.0.0.1:9000";

    /**
     * MinIO accessKey
     */
    private String accessKey;

    /**
     * MinIO secretKey
     */
    private String secretKey;

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.pojo.scanner;

import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源扫描器的配置
 *
 * @author jeff_qian
 * @date 2018-01-03 21:39
 */
@Data
@ConfigurationProperties(prefix = ScannerConstants.SCANNER_PREFIX)
public class ScannerProperties {

    /**
     * 资源扫描开关
     */
    private Boolean open;

    /**
     * 扫描到的资源的url是否要带appCode属性，此值默认为true
     * <p>
     * 也就是资源的url上不会带appCode属性，一般在微服务的系统中需要把此值设为true
     */
    private Boolean urlWithAppCode = true;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充，一般不用手动设置此值）
     */
    private String appCode;

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api;

import io.entframework.kernel.scanner.api.pojo.resource.ResourceParam;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 将扫描的资源汇报给采集中心
 *
 * @author jeff_qian
 * @date 2018-02-06 14:30
 */
public interface ResourceReportApi {

    /**
     * 持久化资源集合到某个服务中
     * <p>
     * 如果是单体项目，则吧资源汇报给本服务
     * <p>
     * 如果是微服务项目，则会有个consumer会将本服务的资源发送给资源管理者（一般为system服务）
     *
     * @param reportResourceReq 资源汇报接口
     * @date 2020/10/19 22:02
     */
    @PostMapping("/client/sys-resource/report-resources")
    List<SysResourcePersistencePojo> reportResourcesAndGetResult(@RequestBody ResourceParam reportResourceReq);

}

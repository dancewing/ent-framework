/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.resource.modular.controller;

import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceParam;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

@RestController
public class ResourceReportController {
    @Resource
    private ResourceServiceApi resourceServiceApi;

    @PostMapping("/client/sys-resource/report-resources")
    public List<SysResourcePersistencePojo> reportResourcesAndGetResult(@RequestBody ResourceParam reportResourceReq) {
        return resourceServiceApi.reportResourcesAndGetResult(reportResourceReq);
    }
}

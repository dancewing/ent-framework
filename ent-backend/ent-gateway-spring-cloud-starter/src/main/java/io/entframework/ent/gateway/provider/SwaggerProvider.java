/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.gateway.provider;

import io.entframework.ent.gateway.props.RouteProperties;
import io.entframework.ent.gateway.props.RouteResource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合接口文档注册
 */
@Primary
@Component
@AllArgsConstructor
public class SwaggerProvider {

    private static final String API_URI = "/v2/api-docs";

    private RouteProperties routeProperties;

    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<RouteResource> routeResources = routeProperties.getResources();
        routeResources.forEach(routeResource -> resources.add(swaggerResource(routeResource)));
        return resources;
    }

    private SwaggerResource swaggerResource(RouteResource routeResource) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(routeResource.getName());
        if (StringUtils.isNotEmpty(routeResource.getGroup())) {
            swaggerResource.setUrl(
                    routeResource.getLocation().concat(API_URI).concat("?group=").concat(routeResource.getGroup()));
        }
        else {
            swaggerResource.setUrl(routeResource.getLocation().concat(API_URI));
        }
        swaggerResource.setSwaggerVersion(routeResource.getVersion());
        return swaggerResource;
    }

}

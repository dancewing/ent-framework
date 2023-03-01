/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.scanner.api.ResourceCollectorApi;
import io.entframework.kernel.scanner.api.exception.ScannerException;
import io.entframework.kernel.scanner.api.exception.enums.ScannerExceptionEnum;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的资源收集器，默认搜集到内存Map中
 *
 * @author jeff_qian
 * @date 2020/10/19 20:33
 */
@Slf4j
public class DefaultResourceCollector implements ResourceCollectorApi {

    /**
     * 以资源编码为key，存放资源集合
     */
    private final Map<String, ResourceDefinition> resourceDefinitions = new ConcurrentHashMap<>();

    /**
     * 第一个key以模块编码（控制器名称下划线分割），第二个key是资源编码，存放资源集合
     */
    private final Map<String, Map<String, ResourceDefinition>> modularResourceDefinitions = new ConcurrentHashMap<>();

    /**
     * key以模块编码（控制器名称下划线分割），value是控制器的中文名称
     */
    private final Map<String, String> controllerCodeNameDict = new HashMap<>();

    /**
     * key是请求的url，value是资源信息
     */
    private final Map<String, ResourceDefinition> urlDefineResources = new ConcurrentHashMap<>();

    @Override
    public void collectResources(List<ResourceDefinition> apiResource) {
        if (apiResource != null && !apiResource.isEmpty()) {
            log.debug("资源收集, {} 条记录", apiResource.size());
            for (ResourceDefinition resourceDefinition : apiResource) {
                ResourceDefinition alreadyFlag = resourceDefinitions.get(resourceDefinition.getResourceCode());
                if (alreadyFlag != null) {
                    throw new ScannerException(ScannerExceptionEnum.SYSTEM_RESOURCE_EXIST, alreadyFlag.getUrl(),
                            resourceDefinition.getUrl());
                }
                resourceDefinitions.put(resourceDefinition.getResourceCode(), resourceDefinition);
                urlDefineResources.put(resourceDefinition.getUrl(), resourceDefinition);

                // 存储模块资源
                Map<String, ResourceDefinition> modularResources = modularResourceDefinitions
                        .get(CharSequenceUtil.toUnderlineCase(resourceDefinition.getModularCode()));
                if (modularResources == null) {
                    modularResources = new HashMap<>();
                    modularResources.put(resourceDefinition.getResourceCode(), resourceDefinition);
                    modularResourceDefinitions.put(
                            CharSequenceUtil.toUnderlineCase(resourceDefinition.getModularCode()), modularResources);
                }
                else {
                    modularResources.put(resourceDefinition.getResourceCode(), resourceDefinition);
                }

                // 添加资源code-中文名称字典
                this.bindResourceName(resourceDefinition.getResourceCode(), resourceDefinition.getResourceName());
            }
        }
    }

    @Override
    public ResourceDefinition getResource(String resourceCode) {
        return resourceDefinitions.get(resourceCode);
    }

    @Override
    public List<ResourceDefinition> getAllResources() {
        Set<Map.Entry<String, ResourceDefinition>> entries = resourceDefinitions.entrySet();
        List<ResourceDefinition> definitions = new ArrayList<>();
        for (Map.Entry<String, ResourceDefinition> entry : entries) {
            definitions.add(entry.getValue());
        }
        return definitions;
    }

    @Override
    public List<ResourceDefinition> getResourcesByModularCode(String code) {
        Map<String, ResourceDefinition> stringResourceDefinitionMap = modularResourceDefinitions.get(code);
        return new ArrayList<>(stringResourceDefinitionMap.values());
    }

    @Override
    public String getResourceName(String code) {
        return controllerCodeNameDict.get(code);
    }

    @Override
    public void bindResourceName(String code, String name) {
        controllerCodeNameDict.putIfAbsent(code, name);
    }

    @Override
    public Map<String, Map<String, ResourceDefinition>> getModularResources() {
        return this.modularResourceDefinitions;
    }

    @Override
    public String getResourceUrl(String code) {
        ResourceDefinition resourceDefinition = this.resourceDefinitions.get(code);
        if (resourceDefinition == null) {
            return null;
        }
        else {
            return resourceDefinition.getUrl();
        }
    }

    @Override
    public ResourceDefinition getResourceByUrl(String url) {
        return this.urlDefineResources.get(url);
    }

}

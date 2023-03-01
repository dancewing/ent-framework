/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.modular.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.entframework.kernel.resource.modular.entity.SysResource;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.scanner.api.pojo.resource.FieldMetadata;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ResourceDefinition和SysResource实体互转
 *
 * @date 2019-05-29-14:37
 */
public class ResourceFactory {

    /**
     * ResourceDefinition转化为SysResource实体
     *
     * @date 2020/12/9 14:14
     */
    public static SysResource createResource(ResourceDefinition resourceDefinition) {
        SysResource resource = new SysResource();
        BeanUtil.copyProperties(resourceDefinition, resource, CopyOptions.create().ignoreError());
        resource.setResourceCode(resourceDefinition.getResourceCode());

        if (resourceDefinition.getRequiredLoginFlag() != null && resourceDefinition.getRequiredLoginFlag()) {
            resource.setRequiredLoginFlag(YesOrNotEnum.Y);
        }
        else {
            resource.setRequiredLoginFlag(YesOrNotEnum.N);
        }

        if (resourceDefinition.getRequiredPermissionFlag() != null && resourceDefinition.getRequiredPermissionFlag()) {
            resource.setRequiredPermissionFlag(YesOrNotEnum.Y);
        }
        else {
            resource.setRequiredPermissionFlag(YesOrNotEnum.N);
        }

        if (resourceDefinition.getViewFlag() != null && resourceDefinition.getViewFlag()) {
            resource.setViewFlag(YesOrNotEnum.Y);
        }
        else {
            resource.setViewFlag(YesOrNotEnum.N);
        }

        resource.setAutoReport(YesOrNotEnum.Y);

        // 转化校验组
        if (ObjectUtil.isNotEmpty(resourceDefinition.getValidateGroups())) {
            resource.setValidateGroups(
                    JSON.toJSONString(resourceDefinition.getValidateGroups(), SerializerFeature.WriteClassName));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getParamFieldDescriptions())) {
            resource.setParamFieldDescriptions(JSON.toJSONString(resourceDefinition.getParamFieldDescriptions(),
                    SerializerFeature.WriteClassName));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getResponseFieldDescriptions())) {
            resource.setResponseFieldDescriptions(JSON.toJSONString(resourceDefinition.getResponseFieldDescriptions(),
                    SerializerFeature.WriteClassName));
        }

        return resource;
    }

    /**
     * SysResource实体转化为ResourceDefinition对象
     *
     * @date 2020/12/9 14:15
     */
    public static ResourceDefinition createResourceDefinition(SysResource sysResource) {

        ResourceDefinition resourceDefinition = new ResourceDefinition();

        // 拷贝公共属性
        BeanUtil.copyProperties(sysResource, resourceDefinition, CopyOptions.create().ignoreError());

        // 设置是否需要登录标识，Y为需要登录
        resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y.equals(sysResource.getRequiredLoginFlag()));

        // 设置是否需要权限认证标识，Y为需要权限认证
        resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y.equals(sysResource.getRequiredPermissionFlag()));

        // 设置是否是视图类型
        resourceDefinition.setViewFlag(YesOrNotEnum.Y.equals(sysResource.getViewFlag()));

        // 转化校验组
        if (ObjectUtil.isNotEmpty(sysResource.getValidateGroups())) {
            resourceDefinition.setValidateGroups(
                    JSON.parseObject(sysResource.getValidateGroups(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getParamFieldDescriptions())) {
            resourceDefinition.setParamFieldDescriptions(
                    JSON.parseObject(sysResource.getParamFieldDescriptions(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getResponseFieldDescriptions())) {
            resourceDefinition.setResponseFieldDescriptions(JSON.parseObject(sysResource.getResponseFieldDescriptions(),
                    FieldMetadata.class, Feature.SupportAutoType));
        }

        return resourceDefinition;
    }

    /**
     * ResourceDefinition转化为api界面的详情信息
     *
     * @date 2021/1/16 16:09
     */
    public static ResourceDefinition fillResourceDetail(ResourceDefinition resourceDefinition) {

        // 这个接口的校验组信息
        Set<String> validateGroups = resourceDefinition.getValidateGroups();

        // 接口的请求参数信息
        Set<FieldMetadata> paramFieldDescriptions = resourceDefinition.getParamFieldDescriptions();
        if (paramFieldDescriptions != null && !paramFieldDescriptions.isEmpty()) {
            for (FieldMetadata fieldMetadata : paramFieldDescriptions) {
                fillDetailMessage(validateGroups, fieldMetadata);
            }
        }

        // 接口的响应参数信息
        FieldMetadata responseFieldDescriptions = resourceDefinition.getResponseFieldDescriptions();
        fillDetailMessage(validateGroups, responseFieldDescriptions);

        return resourceDefinition;
    }

    /**
     * 填充字段里详细的提示信息
     *
     * @date 2021/1/16 18:00
     */
    public static void fillDetailMessage(Set<String> validateGroups, FieldMetadata fieldMetadata) {
        if (validateGroups == null || validateGroups.isEmpty()) {
            return;
        }

        if (fieldMetadata == null) {
            return;
        }
        StringBuilder finalValidateMessages = new StringBuilder();
        Map<String, Set<String>> groupAnnotations = fieldMetadata.getGroupValidationMessage();
        if (groupAnnotations != null) {
            for (String validateGroup : validateGroups) {
                Set<String> validateMessage = groupAnnotations.get(validateGroup);
                if (validateMessage != null && !validateMessage.isEmpty()) {
                    finalValidateMessages.append(CharSequenceUtil.join("，", validateMessage));
                }
            }
        }
        fieldMetadata.setValidationMessages(finalValidateMessages.toString());

        // 递归填充子类型的详细提示信息
        if (fieldMetadata.getGenericFieldMetadata() != null && !fieldMetadata.getGenericFieldMetadata().isEmpty()) {
            for (FieldMetadata metadata : fieldMetadata.getGenericFieldMetadata()) {
                fillDetailMessage(validateGroups, metadata);
            }
        }
    }

    /**
     * 将资源的集合整理成一个map，key是url，value是ResourceDefinition
     *
     * @date 2021/5/17 16:21
     */
    public static Map<String, ResourceDefinition> orderedResourceDefinition(List<ResourceDefinition> sysResourceList) {

        HashMap<String, ResourceDefinition> result = new HashMap<>();

        if (ObjectUtil.isEmpty(sysResourceList)) {
            return result;
        }

        for (ResourceDefinition sysResource : sysResourceList) {
            String url = sysResource.getUrl();
            result.put(url, sysResource);
        }

        return result;
    }

    public static boolean equals(SysResource source, SysResource that) {
        if (source == that) {
            return true;
        }
        return new EqualsBuilder().append(source.getAppCode(), that.getAppCode())
                .append(source.getResourceCode(), that.getResourceCode())
                .append(source.getResourceName(), that.getResourceName())
                .append(source.getProjectCode(), that.getProjectCode())
                .append(source.getClassName(), that.getClassName()).append(source.getMethodName(), that.getMethodName())
                .append(source.getModularCode(), that.getModularCode())
                .append(source.getModularName(), that.getModularName()).append(source.getViewFlag(), that.getViewFlag())
                .append(source.getUrl(), that.getUrl()).append(source.getHttpMethod(), that.getHttpMethod())
                .append(source.getRequiredLoginFlag(), that.getRequiredLoginFlag())
                .append(source.getAutoReport(), that.getAutoReport())
                .append(source.getRequiredPermissionFlag(), that.getRequiredPermissionFlag())
                .append(source.getValidateGroups(), that.getValidateGroups())
                .append(source.getParamFieldDescriptions(), that.getParamFieldDescriptions())
                .append(source.getResponseFieldDescriptions(), that.getResponseFieldDescriptions()).isEquals();
    }

}

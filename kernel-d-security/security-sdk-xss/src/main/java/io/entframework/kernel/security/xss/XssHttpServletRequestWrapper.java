/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.security.xss;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HtmlUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 对原有HttpServletRequest包装，在执行获取参数等操作时候，进行xss过滤
 *
 * @date 2021/1/13 22:50
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    /**
     * 获取所有param方式传参的属性的值
     *
     * @date 2021/1/13 22:52
     */
    @Override
    public String[] getParameterValues(String parameter) {

        // 获取所有参数
        String[] values = super.getParameterValues(parameter);
        if (ObjectUtil.isEmpty(values)) {
            return values;
        }

        // 针对每一个string参数进行过滤
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            encodedValues[i] = HtmlUtil.filter(values[i]);
        }

        return encodedValues;
    }

    /**
     * 获取单个param方式传参的属性的值
     *
     * @date 2021/1/13 22:52
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (ObjectUtil.isEmpty(value)) {
            return value;
        }
        return HtmlUtil.filter(value);
    }

    /**
     * 获取header的值
     *
     * @date 2021/1/13 22:53
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (ObjectUtil.isEmpty(value)) {
            return value;
        }
        return HtmlUtil.filter(value);
    }

}

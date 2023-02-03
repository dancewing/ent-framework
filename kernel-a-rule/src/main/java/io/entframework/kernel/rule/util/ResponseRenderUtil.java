/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.rule.util;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import io.entframework.kernel.rule.pojo.response.ErrorResponseData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * http响应信息的直接渲染工具
 *
 * @date 2020/12/15 21:39
 */
@Slf4j
public class ResponseRenderUtil {

    /**
     * 渲染接口json信息
     *
     * @date 2020/12/15 21:40
     */
    public static void renderJsonResponse(int status, HttpServletResponse response, Object responseData) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        String errorResponseJsonData = JSON.toJSONString(responseData);
        try {
            response.setStatus(status);
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

    /**
     * 渲染接口json信息
     *
     * @date 2020/12/15 21:40
     */
    public static void renderErrorResponse(HttpServletResponse response,
                                           String code, String message, String exceptionClazz) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
        errorResponseData.setExceptionClazz(exceptionClazz);
        String errorResponseJsonData = JSON.toJSONString(errorResponseData);
        try {
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

    /**
     * 设置渲染文件的头
     *
     * @date 2021/7/1 15:01
     */
    public static void setRenderFileHeader(HttpServletResponse response, String fileName) {
        final String charset = ObjectUtil.defaultIfNull(response.getCharacterEncoding(), CharsetUtil.UTF_8);
        response.setHeader("Content-Disposition", CharSequenceUtil.format("attachment;filename={}", URLEncodeUtil.encode(fileName, Charset.forName(charset))));
        response.setContentType("application/octet-stream; charset=utf-8");
    }

}

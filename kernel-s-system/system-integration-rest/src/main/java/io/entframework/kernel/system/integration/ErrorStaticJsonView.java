/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.integration;

import io.entframework.kernel.rule.pojo.response.ErrorResponseData;
import io.entframework.kernel.rule.util.ResponseRenderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.View;

import java.util.Map;

/**
 * 当请求404的时候返回的错误界面
 *
 * @date 2021/5/17 10:45
 */
public class ErrorStaticJsonView implements View {

    @Override
    public void render(Map<String, ?> model, @NotNull HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (response.isCommitted()) {
            // response已经提交不能响应
            return;
        }

        ErrorResponseData errorResponseData = new ErrorResponseData("404", "请求资源不存在");
        ResponseRenderUtil.renderJsonResponse(404, response, errorResponseData);
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

}

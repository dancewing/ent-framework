/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.pojo.response;

import io.entframework.kernel.rule.constants.RuleConstants;
import lombok.Data;

/**
 * http响应结果封装
 *
 * @date 2020/10/17 17:33
 */
@Data
public class ResponseData<T> {

    public static final ResponseData<Void> OK_VOID = ok((Void) null);

    public static final ResponseData<Void> OK_VOID_NO_MSG = ok((Void) null, null);

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private T data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseData<T> ok(T data) {
        return ok(data, RuleConstants.SUCCESS_CODE, RuleConstants.SUCCESS_MESSAGE);
    }

    public static <T> ResponseData<T> ok(String message) {
        return ok(null, RuleConstants.SUCCESS_CODE, message);
    }

    public static <T> ResponseData<T> ok(T data, String message) {
        return ok(data, RuleConstants.SUCCESS_CODE, message);
    }

    public static <T> ResponseData<T> ok(T data, String code, String message) {
        ResponseData<T> resp = new ResponseData<>();
        resp.success = true;
        resp.data = data;
        resp.code = code;
        resp.message = message;
        return resp;
    }

    public static <T> ResponseData<T> fail(String code, String message) {
        ResponseData<T> resp = new ResponseData<>();
        resp.success = false;
        resp.data = null;
        resp.code = code;
        resp.message = message;
        return resp;
    }

}

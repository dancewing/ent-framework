package io.entframework.ent.gateway.provider;

import io.entframework.kernel.rule.exception.base.ServiceException;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 */
public class ResponseProvider {

    /**
     * 成功
     * @param message 信息
     * @return
     */
    public static Map<String, Object> success(String message) {
        return response(200, message);
    }

    /**
     * 失败
     * @param message 信息
     * @return
     */
    public static Map<String, Object> fail(String message) {
        return response(400, message);
    }

    /**
     * 未授权
     * @param message 信息
     * @return
     */
    public static Map<String, Object> unAuth(String message) {
        return response(401, message);
    }

    /**
     * 服务器异常
     * @param message 信息
     * @return
     */
    public static Map<String, Object> error(String message) {
        return response(500, message);
    }

    /**
     * 构建返回的JSON数据格式
     * @param status 状态码
     * @param message 信息
     * @return
     */
    public static Map<String, Object> response(int status, String message) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", status);
        map.put("message", message);
        map.put("data", null);
        return map;
    }

    public static Map<String, Object> response(int status, ServiceException se) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("status", status);
        map.put("code", se.getErrorCode());
        map.put("message", se.getMessage());
        map.put("data", null);
        return map;
    }

}

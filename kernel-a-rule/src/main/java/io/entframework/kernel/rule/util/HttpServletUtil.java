/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.rule.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONPath;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.http.ServletExceptionEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.List;

/**
 * 保存Http请求的上下文，在任何地方快速获取HttpServletRequest和HttpServletResponse
 *
 * @date 2020/10/15 17:38
 */
@Slf4j
public class HttpServletUtil {

    /**
     * 本机ip地址
     */
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * Nginx代理自定义的IP名称
     */
    private static final String AGENT_SOURCE_IP = "Agent-Source-Ip";

    /**
     * 本机ip地址的ipv6地址
     */
    private static final String LOCAL_REMOTE_HOST = "0:0:0:0:0:0:0:1";

    /**
     * 获取用户浏览器信息的http请求header
     */
    private static final String USER_AGENT_HTTP_HEADER = "User-Agent";
    private static final String ACCEPT_HTTP_HEADER = "Accept";

    /**
     * 获取当前请求的request对象
     *
     * @date 2020/10/15 17:48
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
        } else {
            return requestAttributes.getRequest();
        }
    }

    /**
     * 获取当前请求的response对象
     *
     * @date 2020/10/15 17:48
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
        } else {
            return requestAttributes.getResponse();
        }
    }

    /**
     * 获取客户端ip
     * <p>
     * 如果获取不到或者获取到的是ipv6地址，都返回127.0.0.1
     *
     * @date 2020/10/26 14:09
     */
    public static String getRequestClientIp(HttpServletRequest request) {
        if (ObjectUtil.isEmpty(request)) {
            return LOCAL_IP;
        } else {
            String remoteHost = getClientIP(request, AGENT_SOURCE_IP);
            return LOCAL_REMOTE_HOST.equals(remoteHost) ? LOCAL_IP : remoteHost;
        }
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * headerNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request     请求对象{@link HttpServletRequest}
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     * @since 4.4.1
     */
    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    /**
     * 根据http请求的client ip定位城市等信息
     *
     * @param request      http请求封装
     * @param ipGeoApi     阿里云ip定位api接口
     * @param ipGeoAppCode 阿里云ip定位appCode
     * @date 2020/10/26 14:10
     */
    @SuppressWarnings("unchecked")
    public static String calcClientIpAddress(HttpServletRequest request, String ipGeoApi, String ipGeoAppCode) {

        // 如果获取不到，返回 "-"
        String resultJson = "-";

        // 请求阿里云定位接口需要传的header的名称
        String requestApiHeader = "Authorization";

        // 获取客户端的ip地址
        String ip = getRequestClientIp(request);

        // 如果是本地ip或局域网ip，则直接不查询
        if (ObjectUtil.isEmpty(ip) || NetUtil.isInnerIP(ip)) {
            return resultJson;
        }

        // 判断定位api和appCode是否为空
        if (ObjectUtil.hasEmpty(ipGeoApi, ipGeoAppCode)) {
            return resultJson;
        }

        try {
            if (ObjectUtil.isAllNotEmpty(ipGeoApi, ipGeoAppCode)) {
                String jsonPath = "$['data']['country','region','city','isp']";
                String appCodeSymbol = "APPCODE";
                HttpRequest http = HttpUtil.createGet(String.format(ipGeoApi, ip));
                http.header(requestApiHeader, appCodeSymbol + " " + ipGeoAppCode);
                try (HttpResponse httpResponse = http.timeout(3000).execute()) {
                    resultJson = httpResponse.body();
                }
                resultJson = String.join("", (List<String>) JSONPath.read(resultJson, jsonPath));
            }
        } catch (Exception e) {
            log.error("根据ip定位异常，具体信息为：{}", e.getMessage());
        }
        return resultJson;
    }

    /**
     * 根据http请求获取UserAgent信息
     * <p>
     * UserAgent信息包含浏览器的版本，客户端操作系统等信息
     * <p>
     * 没有相关header被解析，则返回null
     *
     * @date 2020/10/28 9:14
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {

        // 获取http header的内容
        String userAgentStr = getHeaderIgnoreCase(request, USER_AGENT_HTTP_HEADER);

        // 如果http header内容不为空，则解析这个字符串获取UserAgent对象
        if (ObjectUtil.isNotEmpty(userAgentStr)) {
            return UserAgentUtil.parse(userAgentStr);
        } else {
            return null;
        }
    }

    /**
     * 忽略大小写获得请求header中的信息
     *
     * @param request        请求对象{@link HttpServletRequest}
     * @param nameIgnoreCase 忽略大小写头信息的KEY
     * @return header值
     */
    public static String getHeaderIgnoreCase(HttpServletRequest request, String nameIgnoreCase) {
        final Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (name != null && name.equalsIgnoreCase(nameIgnoreCase)) {
                return request.getHeader(name);
            }
        }

        return null;
    }

    /**
     * 判断当前请求是否是普通请求
     * <p>
     * 定义：普通请求为网页请求，Accept中包含类似text/html的标识
     *
     * @return ture-是普通请求
     * @date 2021/2/22 22:37
     */
    public static boolean getNormalRequestFlag(HttpServletRequest request) {
        return request.getHeader(ACCEPT_HTTP_HEADER) == null
                || request.getHeader(ACCEPT_HTTP_HEADER).toLowerCase().contains("text/html");
    }

    /**
     * 判断当前请求是否是json请求
     * <p>
     * 定义：json请求为网页请求，Accept中包含类似 application/json 的标识
     *
     * @return ture-是json请求
     * @date 2021/2/22 22:37
     */
    public static boolean getJsonRequestFlag(HttpServletRequest request) {
        return request.getHeader(ACCEPT_HTTP_HEADER) == null
                || request.getHeader(ACCEPT_HTTP_HEADER).toLowerCase().contains("application/json");
    }

}

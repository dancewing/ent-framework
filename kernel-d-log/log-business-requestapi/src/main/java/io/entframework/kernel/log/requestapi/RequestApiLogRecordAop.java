/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.requestapi;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.log.api.LogRecordApi;
import io.entframework.kernel.log.api.constants.LogConstants;
import io.entframework.kernel.log.api.constants.LogFileConstants;
import io.entframework.kernel.log.api.factory.LogRecordFactory;
import io.entframework.kernel.log.api.factory.appender.AuthedLogAppender;
import io.entframework.kernel.log.api.factory.appender.HttpLogAppender;
import io.entframework.kernel.log.api.factory.appender.ParamsLogAppender;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import io.entframework.kernel.rule.annotation.BusinessLog;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 每个请求接口记录日志的AOP
 * <p>
 * 将控制器controller包下的所有控制器类，执行的时候对url，参数，结果等进行记录
 *
 * @date 2020/10/28 17:06
 */
@Aspect
@Slf4j
public class RequestApiLogRecordAop implements Ordered {

    /**
     * 日志记录的api
     */
    private final LogRecordApi logRecordApi;

    public RequestApiLogRecordAop(LogRecordApi logRecordApi) {
        this.logRecordApi = logRecordApi;
    }

    /**
     * 切所有controller包
     */
    @Pointcut("execution(* *..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object aroundPost(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();

        try {
            // 判断是否需要记录日志
            boolean ensureMakeLog = this.ensureMakeLog(point);
            if (!ensureMakeLog) {
                return result;
            }

            if (ObjectUtil.isNotEmpty(result)) {
                // 获取接口上@PostResource或者@GetResource的name属性和requiredLogin属性
                Map<String, Object> annotationProp = getAnnotationProp(point);

                // 获取字段的名
                Map<String, Object> args = getFieldsName(point);

                // 删除Request和Response
                args.entrySet().removeIf(objectEntry -> objectEntry.getValue() instanceof HttpServletRequest
                        || objectEntry.getValue() instanceof HttpServletResponse);

                // 记录日志
                recordLog(args, result, annotationProp);
            }
        }
        catch (Exception e) {
            log.error("日志记录没有记录成功！", e);
        }

        return result;
    }

    /**
     * AOP获取 @PostResource 和 @GetResource 属性信息
     * @param joinPoint joinPoint对象
     * @return 返回K, V格式的参数，key是参数名称，v是参数值
     * @date 2020/12/22 21:18
     */
    private Map<String, Object> getAnnotationProp(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 通过map封装参数和参数值，key参数名，value是参数值
        Map<String, Object> propMap = new HashMap<>(2);

        // 获取接口上的@PostResource或者@GetResource的name属性和requiredLogin属性，填充到map
        ApiResource apiResource = method.getAnnotation(ApiResource.class);
        GetResource getResource = method.getAnnotation(GetResource.class);
        PostResource postResource = method.getAnnotation(PostResource.class);

        if (apiResource != null) {
            propMap.put(ScannerConstants.ANNOTATION_NAME, apiResource.name());
            propMap.put(ScannerConstants.ANNOTATION_REQUIRED_LOGIN, apiResource.requiredLogin());
        }

        if (getResource != null) {
            propMap.put(ScannerConstants.ANNOTATION_NAME, getResource.name());
            propMap.put(ScannerConstants.ANNOTATION_REQUIRED_LOGIN, getResource.requiredLogin());
        }

        if (postResource != null) {
            propMap.put(ScannerConstants.ANNOTATION_NAME, postResource.name());
            propMap.put(ScannerConstants.ANNOTATION_REQUIRED_LOGIN, postResource.requiredLogin());
        }

        return propMap;
    }

    /**
     * 将请求方法记录日志的过程
     * @param params AOP拦截方法的参数封装，key是参数名称，v是参数值
     * @param result AOP拦截方法的返回值
     * @param annotationProp AOP拦截注解属性
     * @date 2020/10/28 17:38
     */
    private void recordLog(Map<String, Object> params, Object result, Map<String, Object> annotationProp) {

        Object actionName = annotationProp.get("name");

        // 创建日志对象
        LogRecordDTO logRecordDTO = LogRecordFactory.createLogRecord(LogConstants.LOG_DEFAULT_NAME, actionName);

        // 填充用户登录信息
        AuthedLogAppender.appendAuthedHttpLog(logRecordDTO);

        // 填充http接口请求信息
        HttpLogAppender.appendHttpLog(logRecordDTO);

        // 追加参数信息
        ParamsLogAppender.appendAuthedHttpLog(logRecordDTO, params, result);

        // 异步记录日志
        logRecordApi.addAsync(logRecordDTO);
    }

    @Override
    public int getOrder() {
        return LogFileConstants.DEFAULT_API_LOG_AOP_SORT;
    }

    /**
     * AOP获取参数名称和参数值
     * @param joinPoint joinPoint对象
     * @return 返回K, V格式的参数，key是参数名称，v是参数值
     * @date 2020/11/2 10:40
     */
    private Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {

        // 获取被拦截方法的所有参数
        Object[] args = joinPoint.getArgs();

        // 通过map封装参数和参数值，key参数名，value是参数值
        Map<String, Object> paramMap = new HashMap<>(args.length);
        try {
            String classType = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            // 处理基本类型
            Class<?>[] classes = new Class[args.length];
            for (int k = 0; k < args.length; k++) {
                if (args[k] instanceof Model) {
                    classes[k] = Model.class;
                }
                else if (args[k] instanceof MultipartFile) {
                    classes[k] = MultipartFile.class;
                }
                else {
                    classes[k] = args[k].getClass();
                }

            }

            ParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();

            // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
            Method method = Class.forName(classType).getMethod(methodName, classes);

            // 参数名
            String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);

            // 为空直接返回
            if (null == parameterNames) {
                return new HashMap<>(1);
            }

            // 装载参数名称和参数值
            for (int i = 0; i < parameterNames.length; i++) {
                // 解决上传文件接口aop记录日志报错
                if (args[i] instanceof MultipartFile multipartFile) {
                    // 根据参数名只记录文件名
                    paramMap.put(parameterNames[i], multipartFile.getOriginalFilename());
                }
                else {
                    paramMap.put(parameterNames[i], args[i]);
                }

            }

        }
        catch (Exception e) {

            // 打印日志
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }

            // 有异常则不显示参数名称直接返回参数
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof MultipartFile multipartFile) {
                    // 根据参数名只记录文件名
                    paramMap.put("args" + i, multipartFile.getOriginalFilename());
                }
                else {
                    paramMap.put("args" + i, args[i]);
                }
            }

        }

        return paramMap;
    }

    /**
     * 确定当前接口是否需要记录日志
     *
     * @date 2022/1/12 20:44
     */
    private boolean ensureMakeLog(ProceedingJoinPoint point) {
        // 获取类上的业务日志开关注解
        Class<?> controllerClass = point.getTarget().getClass();
        BusinessLog businessLog = controllerClass.getAnnotation(BusinessLog.class);

        // 获取方法上的业务日志开关注解
        BusinessLog methodBusinessLog = null;
        MethodSignature methodSignature = null;
        if (!(point.getSignature() instanceof MethodSignature)) {
            return false;
        }
        methodSignature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        try {
            Method currentMethod = target.getClass().getMethod(methodSignature.getName(),
                    methodSignature.getParameterTypes());
            methodBusinessLog = currentMethod.getAnnotation(BusinessLog.class);
        }
        catch (NoSuchMethodException e) {
            return false;
        }

        // 如果控制器类上特意标明不做日志，则不记录日志
        if (businessLog != null && !businessLog.openLog()) {
            return false;
        }
        // 如果方法上标明不记录日志，则不记录日志
        return methodBusinessLog == null || methodBusinessLog.openLog();
    }

}

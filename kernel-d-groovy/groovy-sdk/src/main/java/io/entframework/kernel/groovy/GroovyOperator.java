/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.groovy;

import io.entframework.kernel.groovy.api.GroovyApi;
import io.entframework.kernel.groovy.api.exception.GroovyException;
import io.entframework.kernel.groovy.api.exception.enums.GroovyExceptionEnum;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * groovy动态脚本操作实现
 *
 * @date 2021/1/22 16:28
 */
@Slf4j
public class GroovyOperator implements GroovyApi {

    @Override
    public Class<?> textToClass(String javaClassCode) {
        try (GroovyClassLoader groovyClassLoader = new GroovyClassLoader()) {
            return (Class<?>) groovyClassLoader.parseClass(javaClassCode);
        } catch (IOException ex) {
            log.error("Can't close GroovyClassLoader :" + ex.getMessage());
        }
        return null;
    }

    @Override
    public Object executeShell(String javaCode) {
        GroovyShell shell = new GroovyShell();
        return shell.evaluate(javaCode);
    }

    @Override
    public Object executeClassMethod(String javaClassCode, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Class<?> clazz = this.textToClass(javaClassCode);
            Method clazzMethod = clazz.getMethod(method, parameterTypes);
            Object obj = clazz.newInstance();
            return clazzMethod.invoke(obj, args);
        } catch (Exception e) {
            log.error("执行groovy类中方法出错！", e);
            throw new GroovyException(GroovyExceptionEnum.GROOVY_EXE_ERROR);
        }
    }

}

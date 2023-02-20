/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.dao.mybatis.handler.FastjsonTypeHandler;
import io.entframework.kernel.db.dao.mybatis.handler.OptionEnumTypeHandler;
import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.atteo.classindex.ClassIndex;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import java.util.ArrayList;
import java.util.List;

/***
 * Mybatis dynamic sql 插件
 */
@Slf4j
public class KernelMyBatisConfigurationCustomizer implements ConfigurationCustomizer {

    private static final Class<EnumHandler> HANDLER_ENUM_CLAZZ = EnumHandler.class;
    private static final Class<JsonHandler> HANDLER_JSON_CLAZZ = JsonHandler.class;

    private static final Class<?> ENUM_HANDLER_CLAZZ = OptionEnumTypeHandler.class;
    private static final Class<?> JSON_HANDLER_CLAZZ = FastjsonTypeHandler.class;

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        setup(configuration);
    }

    private void setup(org.apache.ibatis.session.Configuration configuration) {
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        log.info("EnumTypeHandler - start......");
        List<Class<?>> enumJavaTypes = getEnumJavaTypes();
        for (Class<?> javaTypeClass : enumJavaTypes) {
            typeHandlerRegistry.register(javaTypeClass, ENUM_HANDLER_CLAZZ);
            log.info("EnumTypeHandler - javaTypeClass:" + javaTypeClass + ", TypeHandler:" + ENUM_HANDLER_CLAZZ.getName());
        }
        log.info("EnumTypeHandler - end......");
        log.info("JsonHandler - start......");
        List<Class<?>> jsonJavaTypes = getJsonJavaTypes();
        for (Class<?> javaTypeClass : jsonJavaTypes) {
            typeHandlerRegistry.register(javaTypeClass, JSON_HANDLER_CLAZZ);
            log.info("JsonHandler - javaTypeClass:" + javaTypeClass + ", TypeHandler:" + JSON_HANDLER_CLAZZ.getName());
        }
        log.info("JsonHandler - end......");
    }

    private static List<Class<?>> getEnumJavaTypes() {
        List<Class<?>> list = new ArrayList<>();
        final Iterable<Class<?>> classes = ClassIndex.getAnnotated(HANDLER_ENUM_CLAZZ);
        for (Class<?> clazz : classes) {
            if (clazz.isEnum()) {
                list.add(clazz);
            } else {
                log.warn("EnumTypeHandler - Not Enum:" + clazz.getName());
            }
        }
        return list;
    }

    private static List<Class<?>> getJsonJavaTypes() {
        List<Class<?>> list = new ArrayList<>();
        final Iterable<Class<?>> classes = ClassIndex.getAnnotated(HANDLER_JSON_CLAZZ);
        for (Class<?> clazz : classes) {
            list.add(clazz);
        }
        return list;
    }
}

/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.mapper;

import io.entframework.kernel.db.mds.meta.Entities;
import io.entframework.kernel.db.mds.meta.EntityMeta;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapperManager implements InitializingBean, ApplicationContextAware {
    private static final Map<Class<?>, Class<?>> knownMappers = new HashMap<>();
    private static final Map<Class<?>, BaseMapper<?>> classMappers = new HashMap<>();
    private ApplicationContext context;
    @Resource
    private SqlSessionFactory sqlSessionFactory;


    @SuppressWarnings("unchecked")
    public <T> BaseMapper<T> getMapper(Class<T> entity) {
        if (!knownMappers.containsKey(entity)) {

        }
        Class<?> mapperClass = knownMappers.get(entity);
        if (classMappers.containsKey(mapperClass)) {
            return (BaseMapper<T>) classMappers.get(mapperClass);
        }

        BaseMapper<T> baseMapper = (BaseMapper<T>) this.context.getBean(mapperClass);
        classMappers.put(mapperClass, baseMapper);
        return baseMapper;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.context.getBeansWithAnnotation(Mapper.class);
        Collection<Class<?>> mappers = this.sqlSessionFactory.getConfiguration().getMapperRegistry().getMappers();
        mappers.forEach(mapperClass -> {
            if (log.isDebugEnabled()) {
                log.debug("find mapper: {}", mapperClass.getName());
            }
            EntityMeta entities = Entities.fromMapper(mapperClass);
            knownMappers.put(entities.getEntityClass(), mapperClass);
        });
        if (mappers.isEmpty() && log.isWarnEnabled()) {
            log.warn("No mapper found!");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

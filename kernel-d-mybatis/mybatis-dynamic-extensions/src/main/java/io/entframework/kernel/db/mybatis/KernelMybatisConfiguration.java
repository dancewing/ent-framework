/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mybatis;

import io.entframework.kernel.db.mybatis.binding.MapperRegistryExt;
import io.entframework.kernel.db.mybatis.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.JoinColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.annotation.OneToMany;
import org.mybatis.dynamic.sql.util.ReflectUtils;
import org.mybatis.dynamic.sql.util.StringUtilities;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KernelMybatisConfiguration extends Configuration {

    public KernelMybatisConfiguration() {
    }

    public KernelMybatisConfiguration(Environment environment) {
        super(environment);
    }

    protected final Map<String, ResultMap> additionalResultMaps = new StrictMap<>("Result Maps collection");

    private final MapperRegistryExt mapperRegistryExt = new MapperRegistryExt(this);

    @Override
    public MapperRegistry getMapperRegistry() {
        return mapperRegistryExt;
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        mapperRegistryExt.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        mapperRegistryExt.addMappers(packageName);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        mapperRegistryExt.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistryExt.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return mapperRegistryExt.hasMapper(type);
    }

    @Override
    public ResultMap getResultMap(String id) {
        ResultMap resultMap = super.getResultMap(id);
        // This is empty, try to generate result map
        if (resultMap.getMappedColumns().isEmpty()) {
            if (additionalResultMaps.containsKey(id)) {
                return additionalResultMaps.get(id);
            }

            String methodFullName = StringUtils.substringBeforeLast(id, "-");
            String mapperName = StringUtils.substringBeforeLast(methodFullName, ".");
            String method = StringUtils.substringAfterLast(methodFullName, ".");
            Class<?> mapperClass = ClassUtils.toClassConfident(mapperName);
            Class<?> entityClass = getEntityClass(mapperClass);
            if (entityClass != null) {
                Method mapperMethod = getMethod(mapperClass, method);
                Type resolvedReturnType = TypeParameterResolver.resolveReturnType(mapperMethod, mapperClass);

                Class<?> returnType;
                if (resolvedReturnType instanceof Class<?>) {
                    returnType = (Class<?>) resolvedReturnType;
                }
                else if (resolvedReturnType instanceof ParameterizedType) {
                    returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getActualTypeArguments()[0];
                }
                else {
                    returnType = mapperMethod.getReturnType();
                }
                if (entityClass.isAssignableFrom(returnType)) {
                    EntityMeta entities = Entities.getInstance(entityClass);
                    List<ResultMapping> resultMappings = new ArrayList<>();
                    List<FieldAndColumn> columns = entities.getColumns();
                    for (FieldAndColumn wrapper : columns) {
                        ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, wrapper.field().getName(),
                                wrapper.column().name(), wrapper.field().getType());
                        createResultMapping(entities, resultMappings, wrapper, rmBuilder);
                    }

                    for (Field field : entities.getRelations()) {
                        ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, field.getName(), null,
                                field.getType());
                        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                        StringBuilder resultId = new StringBuilder(methodFullName + "-");
                        if (field.isAnnotationPresent(ManyToOne.class)) {
                            resultId.append("association");
                        }
                        if (field.isAnnotationPresent(OneToMany.class)) {
                            resultId.append("collection");
                        }
                        resultId.append("-");
                        resultId.append(field.getName());
                        if (!resultMaps.containsKey(resultId.toString())) {
                            createResultMapping(resultId.toString(), joinColumn.target(), null);
                        }
                        rmBuilder.nestedResultMapId(resultId.toString());
                        resultMappings.add(rmBuilder.build());
                    }

                    ResultMap.Builder builder = new ResultMap.Builder(this, id, entities.getEntityClass(),
                            resultMappings);
                    ResultMap answer = builder.build();
                    additionalResultMaps.put(id, answer);
                    return answer;
                }
            }

        }
        return resultMap;
    }

    private Class<?> getEntityClass(Class<?> mapperClass) {
        Type[] genericInterfaces = mapperClass.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType parameterizedType) {
                return (Class<?>) parameterizedType.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String methodName) throws SecurityException {
        if (null == clazz || !StringUtilities.hasText(methodName)) {
            return null;
        }

        Method res = null;
        final Method[] methods = ReflectUtils.getMethods(clazz);
        for (Method method : methods) {

            if (method.getName().equals(methodName)) {
                res = method;
            }
        }
        return res;
    }

    private void createResultMapping(String resultId, Class<?> target, String alias) {
        EntityMeta entities = Entities.getInstance(target);
        List<ResultMapping> resultMappings = new ArrayList<>();
        List<FieldAndColumn> columns = entities.getColumns();
        if (StringUtils.isEmpty(alias)) {
            alias = entities.getTable().tableNameAtRuntime() + "_";
        }
        for (FieldAndColumn wrapper : columns) {
            ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, wrapper.field().getName(),
                    alias + wrapper.column().name(), wrapper.field().getType());
            createResultMapping(entities, resultMappings, wrapper, rmBuilder);
        }
        ResultMap.Builder builder = new ResultMap.Builder(this, resultId, entities.getEntityClass(), resultMappings);
        addResultMap(builder.build());
    }

    private void createResultMapping(EntityMeta entities, List<ResultMapping> resultMappings, FieldAndColumn wrapper,
            ResultMapping.Builder rmBuilder) {
        Column column = wrapper.field().getAnnotation(Column.class);
        if (column.typeHandler() != TypeHandler.class) {
            rmBuilder.typeHandler(ReflectUtils.newInstance(column.typeHandler()));
        }
        rmBuilder.jdbcType(JdbcType.forCode(column.jdbcType().getVendorTypeNumber()));
        if (StringUtils.equals(entities.getPrimaryKey().field().getName(), wrapper.field().getName())) {
            rmBuilder.flags(Collections.singletonList(ResultFlag.ID));
        }
        resultMappings.add(rmBuilder.build());
    }

}

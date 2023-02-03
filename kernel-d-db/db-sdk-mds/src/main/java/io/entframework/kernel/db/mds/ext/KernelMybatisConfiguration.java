/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.ext;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.JoinColumn;
import io.entframework.kernel.db.api.annotation.ManyToOne;
import io.entframework.kernel.db.api.annotation.OneToMany;
import io.entframework.kernel.db.api.util.ClassUtils;
import io.entframework.kernel.db.mds.meta.Entities;
import io.entframework.kernel.db.mds.meta.EntityMeta;
import io.entframework.kernel.db.mds.meta.FieldMeta;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KernelMybatisConfiguration extends Configuration {

    protected final Map<String, ResultMap> additionalResultMaps = new StrictMap<>("Result Maps collection");

    @Override
    public ResultMap getResultMap(String id) {
        ResultMap resultMap = super.getResultMap(id);
        //这是个空的ResultMap
        if (resultMap.getMappedColumns().size() == 0) {
            if (additionalResultMaps.containsKey(id)) {
                return additionalResultMaps.get(id);
            }
            String methodFullName = StringUtils.substringBeforeLast(id, "-");
            String mapperName = StringUtils.substringBeforeLast(methodFullName, ".");
            String method = StringUtils.substringAfterLast(methodFullName, ".");
            if (StringUtils.equalsAny(method, "selectOne", "selectMany")) {
                EntityMeta entities = Entities.fromMapper(ClassUtils.toClassConfident(mapperName));
                List<ResultMapping> resultMappings = new ArrayList<>();
                List<FieldMeta> columns = entities.getColumns();
                for (FieldMeta wrapper : columns) {
                    ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, wrapper.getField().getName(), wrapper.getColumn().name(), wrapper.getField().getType());
                    createResultMapping(entities, resultMappings, wrapper, rmBuilder);
                }

                for (Field field : entities.getRelations()) {
                    ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, field.getName(), null, field.getType());
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

                ResultMap.Builder builder = new ResultMap.Builder(this, id, entities.getEntityClass(), resultMappings);
                ResultMap answer = builder.build();
                additionalResultMaps.put(id, answer);
                return answer;
            }
        }
        return resultMap;
    }

    private void createResultMapping(String resultId, Class<?> target, String alias) {
        EntityMeta entities = Entities.getInstance(target);
        List<ResultMapping> resultMappings = new ArrayList<>();
        List<FieldMeta> columns = entities.getColumns();
        if (StringUtils.isEmpty(alias)) {
            alias = entities.getTable().tableNameAtRuntime() + "_";
        }
        for (FieldMeta wrapper : columns) {
            ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this, wrapper.getField().getName(), alias + wrapper.getColumn().name(), wrapper.getField().getType());
            createResultMapping(entities, resultMappings, wrapper, rmBuilder);
        }
        ResultMap.Builder builder = new ResultMap.Builder(this, resultId, entities.getEntityClass(), resultMappings);
        addResultMap(builder.build());
    }

    private void createResultMapping(EntityMeta entities, List<ResultMapping> resultMappings, FieldMeta wrapper, ResultMapping.Builder rmBuilder) {
        Column column = wrapper.getField().getAnnotation(Column.class);
        if (column.typeHandler() != TypeHandler.class) {
            rmBuilder.typeHandler(ClassUtils.newInstance(column.typeHandler()));
        }
        rmBuilder.jdbcType(JdbcType.forCode(column.jdbcType().getVendorTypeNumber()));
        if (StringUtils.equals(entities.getPrimaryKey().getField().getName(), wrapper.getField().getName())) {
            rmBuilder.flags(Collections.singletonList(ResultFlag.ID));
        }
        resultMappings.add(rmBuilder.build());
    }
}

package io.entframework.kernel.db.mds.ext.mapping;

import io.entframework.kernel.db.api.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.JoinColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.annotation.OneToMany;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MappedStatementCreator {

    private final Configuration configuration;

    public MappedStatementCreator(Configuration configuration) {
        this.configuration = configuration;
    }

    public MappedStatement create(MappedStatement source, Class<?> entityClass) {
        if (entityClass != null) {
            if (source.getSqlCommandType() == SqlCommandType.SELECT) {
                String key = source.getId() + "-" + entityClass.getName();
                if (configuration.hasStatement(key)) {
                    return configuration.getMappedStatement(key);
                } else {
                    MappedStatement mappedStatement = create(source, key, entityClass);
                    this.configuration.addMappedStatement(mappedStatement);
                    return mappedStatement;
                }
            }
        }
        log.warn("entityClass is null");
        return source;
    }

    public MappedStatement create(MappedStatement source, String key, Class<?> entityClass) {

        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, key, source.getSqlSource(), source.getSqlCommandType());
        builder.parameterMap(source.getParameterMap());
        builder.fetchSize(source.getFetchSize());
        builder.timeout(source.getTimeout());
        builder.statementType(source.getStatementType());
        builder.resultSetType(source.getResultSetType());
        builder.cache(source.getCache());
        builder.flushCacheRequired(source.isFlushCacheRequired());
        builder.useCache(source.isUseCache());
        builder.resultOrdered(source.isResultOrdered());
        builder.keyGenerator(source.getKeyGenerator());
        if (source.getKeyProperties() != null) {
            builder.keyProperty(String.join(",", List.of(source.getKeyProperties())));
        }
        if (source.getKeyColumns() != null) {
            builder.keyColumn(String.join(",", List.of(source.getKeyColumns())));
        }
        builder.databaseId(source.getDatabaseId());
        builder.lang(source.getLang());
        if (source.getResultSets() != null) {
            builder.resultSets(String.join(",", List.of(source.getResultSets())));
        }
        builder.resultMaps(createResultMaps(entityClass, key));

        return builder.build();
    }

    private List<ResultMap> createResultMaps(Class<?> entityClass, String key) {
        if (this.configuration.hasResultMap(key)) {
            return List.of(this.configuration.getResultMap(key));
        }
        EntityMeta entities = Entities.getInstance(entityClass);
        List<ResultMapping> resultMappings = new ArrayList<>();
        List<FieldAndColumn> columns = entities.getColumns();
        for (FieldAndColumn wrapper : columns) {
            ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this.configuration, wrapper.field().getName(), wrapper.column().name(), wrapper.field().getType());
            createEntityResultMapping(entities, resultMappings, wrapper, rmBuilder);
        }

        for (Field field : entities.getRelations()) {
            ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this.configuration, field.getName(), null, field.getType());
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            StringBuilder resultId = new StringBuilder(key + "-");
            if (field.isAnnotationPresent(ManyToOne.class)) {
                resultId.append("association");
            }
            if (field.isAnnotationPresent(OneToMany.class)) {
                resultId.append("collection");
            }
            resultId.append("-");
            resultId.append(field.getName());
            if (!this.configuration.hasResultMap(resultId.toString())) {
                createResultMapping(resultId.toString(), joinColumn.target(), null);
            }
            rmBuilder.nestedResultMapId(resultId.toString());
            resultMappings.add(rmBuilder.build());
        }

        ResultMap.Builder builder = new ResultMap.Builder(this.configuration, key, entities.getEntityClass(), resultMappings);
        ResultMap answer = builder.build();
        return List.of(answer);
    }

    private void createResultMapping(String resultId, Class<?> target, String alias) {
        EntityMeta entities = Entities.getInstance(target);
        List<ResultMapping> resultMappings = new ArrayList<>();
        List<FieldAndColumn> columns = entities.getColumns();
        if (StringUtils.isEmpty(alias)) {
            alias = entities.getTable().tableNameAtRuntime() + "_";
        }
        for (FieldAndColumn wrapper : columns) {
            ResultMapping.Builder rmBuilder = new ResultMapping.Builder(this.configuration, wrapper.field().getName(), alias + wrapper.column().name(), wrapper.field().getType());
            createEntityResultMapping(entities, resultMappings, wrapper, rmBuilder);
        }
        ResultMap.Builder builder = new ResultMap.Builder(this.configuration, resultId, entities.getEntityClass(), resultMappings);
        this.configuration.addResultMap(builder.build());
    }


    private void createEntityResultMapping(EntityMeta entities, List<ResultMapping> resultMappings, FieldAndColumn wrapper, ResultMapping.Builder rmBuilder) {
        Column column = wrapper.field().getAnnotation(Column.class);
        if (column.typeHandler() != TypeHandler.class) {
            rmBuilder.typeHandler(ClassUtils.newInstance(column.typeHandler()));
        }
        rmBuilder.jdbcType(JdbcType.forCode(column.jdbcType().getVendorTypeNumber()));
        if (StringUtils.equals(entities.getPrimaryKey().field().getName(), wrapper.field().getName())) {
            rmBuilder.flags(Collections.singletonList(ResultFlag.ID));
        }
        resultMappings.add(rmBuilder.build());
    }
}

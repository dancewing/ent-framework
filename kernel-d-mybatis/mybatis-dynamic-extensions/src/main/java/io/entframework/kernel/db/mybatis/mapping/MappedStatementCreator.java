package io.entframework.kernel.db.mybatis.mapping;

import io.entframework.kernel.db.mybatis.binding.ActualEntityClassDetector;
import io.entframework.kernel.db.mybatis.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.dynamic.sql.StatementProvider;
import org.mybatis.dynamic.sql.annotation.*;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MappedStatementCreator {

    private final Configuration configuration;

    public MappedStatementCreator(Configuration configuration) {
        this.configuration = configuration;
    }

    public MappedStatement create(MappedStatement source, StatementProvider statementProvider) {
        Class<?> entityClass = ActualEntityClassDetector.determine(statementProvider);
        if (entityClass != null) {
            String key = source.getId() + "-" + entityClass.getName();
            if (configuration.hasStatement(key)) {
                return configuration.getMappedStatement(key);
            } else {
                MappedStatement mappedStatement = create(source, key, statementProvider, entityClass);
                this.configuration.addMappedStatement(mappedStatement);
                return mappedStatement;
            }
        }
        return source;
    }

    public MappedStatement create(MappedStatement source, String key, StatementProvider statementProvider, Class<?> entityClass) {


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

        if (source.getSqlCommandType() == SqlCommandType.SELECT) {
            builder.resultMaps(createResultMaps(entityClass, key));
        }
        if (source.getSqlCommandType() == SqlCommandType.INSERT) {
            if (statementProvider instanceof InsertStatementProvider<?>) {
                EntityMeta entityMeta = Entities.getInstance(entityClass);
                Optional<FieldAndColumn> generatedValueColumn = entityMeta.findColumn(GeneratedValue.class);
                generatedValueColumn.ifPresent(fieldAndColumn -> {
                    KeyGenerator keyGenerator = createKeyGenerator(fieldAndColumn, source.getId(), source.getLang());
                    builder.keyGenerator(keyGenerator);
                    builder.keyProperty("row." + fieldAndColumn.fieldName());
                });
            }
        }

        return builder.build();
    }

    private KeyGenerator createKeyGenerator(FieldAndColumn fieldAndColumn, String baseStatementId, LanguageDriver lang) {
        String id = baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        Class<?> resultTypeClass = fieldAndColumn.fieldType();
        StatementType statementType = StatementType.PREPARED;
        String keyProperty = "row." + fieldAndColumn.fieldName();
        String keyColumn = "null";
        boolean executeBefore = false;

        // defaults
        boolean useCache = false;
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        Integer fetchSize = null;
        Integer timeout = null;
        boolean flushCache = false;
        String parameterMap = null;
        String resultMap = null;
        ResultSetType resultSetTypeEnum = null;
        String databaseId = null;

        String[] statements = getSelectKeyStatement();

        SqlSource sqlSource = lang.createSqlSource(configuration, String.join(" ", statements).trim(), null);
        SqlCommandType sqlCommandType = SqlCommandType.SELECT;
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType)
                .fetchSize(fetchSize)
                .timeout(timeout)
                .statementType(statementType)
                .keyGenerator(keyGenerator)
                .keyProperty(keyProperty)
                .keyColumn(keyColumn)
                .databaseId(databaseId)
                .lang(lang)
                .resultOrdered(false)
                .resultSets(null)
                .resultMaps(getStatementResultMaps(null, resultTypeClass, id))
                .resultSetType(null)
                .flushCacheRequired(false)
                .useCache(false);
        MappedStatement keyStatement = statementBuilder.build();
        SelectKeyGenerator answer = new SelectKeyGenerator(keyStatement, executeBefore);
        configuration.addKeyGenerator(id, answer);
        return answer;
    }

    private String[] getSelectKeyStatement() {
        List<String> results = new ArrayList<>();
        if (StringUtils.isNotEmpty(this.configuration.getDatabaseId())) {
            String databaseId = this.configuration.getDatabaseId();
            if ("mysql".equalsIgnoreCase(databaseId) || "mariadb".equalsIgnoreCase(databaseId) || "h2".equalsIgnoreCase(databaseId)) {
                results.add("SELECT LAST_INSERT_ID()");
            }
        }
        return results.toArray(new String[0]);
    }

    private List<ResultMap> getStatementResultMaps(
            String resultMap,
            Class<?> resultType,
            String statementId) {

        List<ResultMap> resultMaps = new ArrayList<>();
        if (resultMap != null) {
            String[] resultMapNames = resultMap.split(",");
            for (String resultMapName : resultMapNames) {
                try {
                    resultMaps.add(configuration.getResultMap(resultMapName.trim()));
                } catch (IllegalArgumentException e) {
                    throw new IncompleteElementException("Could not find result map '" + resultMapName + "' referenced from '" + statementId + "'", e);
                }
            }
        } else if (resultType != null) {
            ResultMap inlineResultMap = new ResultMap.Builder(
                    configuration,
                    statementId + "-Inline",
                    resultType,
                    new ArrayList<>(),
                    null).build();
            resultMaps.add(inlineResultMap);
        }
        return resultMaps;
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

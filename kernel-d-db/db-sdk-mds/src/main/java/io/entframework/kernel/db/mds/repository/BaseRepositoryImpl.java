/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.repository;

import cn.hutool.core.lang.Assert;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.entity.JoinDetail;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.db.mds.mapper.MapperManager;
import io.entframework.kernel.db.mds.meta.Entities;
import io.entframework.kernel.db.mds.meta.EntityMeta;
import io.entframework.kernel.db.mds.util.MyBatis3CustomUtils;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<ENTITY> implements BaseRepository<ENTITY> {

    private final Class<ENTITY> entityClass;

    @Autowired
    private MapperManager mapperManager;

    @SuppressWarnings("unchecked")
    protected BaseRepositoryImpl(Class<? extends ENTITY> entityClass) {
        this.entityClass = (Class<ENTITY>) entityClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectMany(SelectStatementProvider selectStatement) {
        return this.getMapper().selectMany(selectStatement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectOne(SelectStatementProvider selectStatement) {
        return this.getMapper().selectOne(selectStatement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(CountDSLCompleter completer) {
        return this.getMapper().count(completer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countBy(ENTITY entity) {
        return this.getMapper().count(this.defaultCountDSL(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(DeleteDSLCompleter completer) {
        return this.getMapper().delete(completer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByPrimaryKey(Object id) {
        return this.getMapper().deleteByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ENTITY insert(ENTITY row) {
        int count = getMapper().insert(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> insertMultiple(List<ENTITY> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        this.getMapper().insertMultiple(records);
        return records;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ENTITY insertSelective(ENTITY row) {
        int count = this.getMapper().insertSelective(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectOne(SelectDSLCompleter completer) {
        return this.getMapper().selectOne(completer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> select(SelectDSLCompleter completer) {
        return this.getMapper().select(completer);
    }

    @Override
    public List<ENTITY> select(QueryExpressionDSL<SelectModel> queryExpression) {
        return this.select(queryExpression, true);
    }
    
    @Override
    public List<ENTITY> select(QueryExpressionDSL<SelectModel> queryExpression, boolean cascade) {
        EntityMeta entities = Entities.getInstance(entityClass);
        if (entities.hasManyToOne() && cascade) {
            List<JoinDetail> joinDetails = entities.getManyToOneJoinDetails();
            return MyBatis3CustomUtils.leftJoinSelectList(this.getMapper()::selectMany, entities.getManyToOneSelectColumns(),
                    entities.getTable(), joinDetails, queryExpression);
        }
        SelectStatementProvider selectStatement = queryExpression.build().render(RenderingStrategies.MYBATIS3);
        return this.getMapper().selectMany(selectStatement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectDistinct(SelectDSLCompleter completer) {
        return this.getMapper().selectDistinct(completer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectByPrimaryKey(Object id) {
        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(UpdateDSLCompleter completer) {
        return this.getMapper().update(completer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateByPrimaryKey(ENTITY row) {
        return this.getMapper().updateByPrimaryKey(row, false);
    }

    @Override
    public int updateByPrimaryKeySelective(ENTITY row) {
        return this.getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ENTITY update(ENTITY row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        Assert.notNull(entities);
        int count = getMapper().updateByPrimaryKey(row, false);
        if (count == 0) {
            Object pk = ReflectionKit.getFieldValue(row, entities.getPrimaryKey().getField().getName());
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, pk);
        }
        return row;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectBy(ENTITY row) {
        return this.selectBy(row, null, null, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectBy(ENTITY row, boolean cascade) {
        return this.selectBy(row, null, null, cascade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectBy(ENTITY row, Integer pageNo, Integer pageSize, boolean cascade) {
        return this.selectBy(row, pageNo, pageSize, cascade, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> selectBy(ENTITY row, Integer pageNo, Integer pageSize, boolean cascade, SortSpecification orderBy) {
        Assert.notNull(row);
        EntityMeta entities = Entities.getInstance(row.getClass());
        if (entities.hasManyToOne() && cascade) {
            List<JoinDetail> joinDetails = entities.getManyToOneJoinDetails();
            return MyBatis3CustomUtils.leftJoinSelectList(this.getMapper()::selectMany, entities.getManyToOneSelectColumns(),
                    entities.getTable(), joinDetails, defaultQuerySelectDSL(row, pageNo, pageSize, orderBy));
        }
        return getMapper().select(defaultSelectDSL(row, orderBy));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectOne(ENTITY row) {
        return this.selectOne(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectOne(ENTITY row, boolean cascade) {
        return this.selectOne(row, cascade, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> selectOne(ENTITY row, boolean cascade, SortSpecification orderBy) {
        Assert.notNull(row);
        EntityMeta entities = Entities.getInstance(row.getClass());
        if (entities.hasRelation() && cascade) {
            List<JoinDetail> joinDetails = entities.getAllJoinDetails();
            return MyBatis3CustomUtils.leftJoinSelectOne(this.getMapper()::selectOne, entities.getAllJoinSelectColumns(),
                    entities.getTable(), joinDetails, defaultQuerySelectDSL(row, 1, 2, orderBy));
        }
        return getMapper().selectOne(defaultSelectDSL(row, orderBy));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectDSLCompleter defaultSelectDSL(ENTITY row, SortSpecification orderBy) {
        return this.defaultSelectDSL(row, null, null, orderBy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteBy(ENTITY row) {
        return getMapper().delete(defaultDeleteDSL(row));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(ENTITY row) {
        Assert.notNull(row);
        EntityMeta entities = Entities.getInstance(row.getClass());
        Assert.notNull(entities);
        Object pk = ReflectionKit.getFieldValue(row, entities.getPrimaryKey().getField().getName());
        if (pk == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(pk);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ENTITY get(Object id) {
        Optional<ENTITY> row = getMapper().selectByPrimaryKey(id);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(InsertStatementProvider<ENTITY> statement) {
        return getMapper().insert(statement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(UpdateStatementProvider statement) {
        return getMapper().update(statement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(DeleteStatementProvider statement) {
        return getMapper().delete(statement);
    }

    public BaseMapper<ENTITY> getMapper() {
        return mapperManager.getMapper(entityClass);
    }

    public <T> BaseMapper<T> getMapper(Class<T> entityClass) {
        return mapperManager.getMapper(entityClass);
    }

    public Class<ENTITY> getEntityClass() {
        return this.entityClass;
    }
}

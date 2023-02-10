/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.service;

import io.entframework.kernel.converter.support.ConverterService;
import io.entframework.kernel.db.api.factory.PageResultFactory;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.api.util.ClassUtils;
import io.entframework.kernel.db.mds.repository.GeneralRepository;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.util.Inflection;
import io.entframework.kernel.rule.util.ReflectionKit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.select.ColumnSortSpecification;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseServiceImpl<REQ extends BaseRequest, RES, ENTITY> implements BaseService<REQ, RES, ENTITY> {
    private final Class<ENTITY> entityClass;
    private final Class<REQ> requestClass;
    private final Class<RES> responseClass;

    @Autowired
    @Lazy
    protected ConverterService converterService;

    @Resource
    private GeneralRepository generalRepository;


    @SuppressWarnings("unchecked")
    protected BaseServiceImpl(Class<? extends REQ> requestClass, Class<? extends RES> responseClass, Class<? extends ENTITY> entityClass) {
        this.requestClass = (Class<REQ>) requestClass;
        this.responseClass = (Class<RES>) responseClass;
        this.entityClass = (Class<ENTITY>) entityClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RES> select(REQ request) {
        ENTITY query = this.converterService.convert(request, entityClass);
        List<ENTITY> results = generalRepository.selectBy(query, null, null, false, getOrderBy(request));
        return results.stream().map(entity -> this.converterService.convert(entity, responseClass)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RES> list(REQ request) {
        ENTITY query = this.converterService.convert(request, entityClass);
        List<ENTITY> results = generalRepository.selectBy(query, request.getPageNo(), request.getPageSize(), true, getOrderBy(request));
        return results.stream().map(entity -> this.converterService.convert(entity, responseClass)).toList();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PageResult<RES> page(REQ request) {
        long count = this.countBy(request);
        ENTITY query = this.converterService.convert(request, entityClass);
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        List<ENTITY> rows = generalRepository.selectBy(query, request.getPageNo(), request.getPageSize(), true, getOrderBy(request));
        List<RES> results = rows.stream().map(entity -> this.converterService.convert(entity, responseClass)).toList();
        return PageResultFactory.createPageResult(results, count, pageSize, pageNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RES selectOne(REQ request) {
        ENTITY query = this.converterService.convert(request, entityClass);
        Optional<ENTITY> result = generalRepository.selectOne(query, true);
        return this.converterService.convert(result.orElse(null), responseClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countBy(REQ request) {
        ENTITY entity = this.converterService.convert(request, entityClass);
        return generalRepository.countBy(entity);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBy(REQ request) {
        ENTITY row = this.converterService.convert(request, entityClass);
        return generalRepository.deleteBy(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(REQ request) {
        ENTITY row = this.converterService.convert(request, entityClass);
        return generalRepository.delete(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(REQ request) {
        EntityMeta entities = Entities.getInstance(this.entityClass);
        String pluralizeIds = Inflection.pluralize(entities.getPrimaryKey().field().getName());
        Object pks = ReflectionKit.getFieldValue(request, pluralizeIds);
        if (pks == null) {
            return -1;
        }
        if (pks instanceof Collection<?> colIds) {
            colIds.forEach(id -> {
                ENTITY row = this.generalRepository.get(this.entityClass, (Serializable) id);
                this.generalRepository.deleteBy(row);
            });
            return colIds.size();
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RES insert(REQ request) {
        ENTITY row = this.converterService.convert(request, entityClass);
        row = generalRepository.insert(row);
        return this.converterService.convert(row, responseClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RES> insertMultiple(List<REQ> requests) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }
        List<ENTITY> records = requests.stream().map(request -> this.converterService.convert(request, entityClass)).toList();
        List<ENTITY> results = this.generalRepository.insertMultiple(records);
        return results.stream().map(entity -> this.converterService.convert(entity, responseClass)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RES load(Object id) {
        EntityMeta entityMeta = Entities.getInstance(getEntityClass());
        ENTITY query = ClassUtils.newInstance(getEntityClass());
        ReflectionKit.setFieldValue(query, entityMeta.getPrimaryKey().field().getName(), id);
        Optional<ENTITY> record = generalRepository.selectOne(query, true);
        return record.map(entity -> this.converterService.convert(entity, responseClass)).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RES get(Serializable id) {
        ENTITY record = generalRepository.get(this.entityClass, id);
        return this.converterService.convert(record, responseClass);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<ENTITY> selectByPrimaryKey(Serializable id) {
        return generalRepository.selectByPrimaryKey(this.entityClass, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RES update(REQ request) {
        ENTITY record = this.converterService.convert(request, entityClass);
        record = generalRepository.update(record);
        return this.converterService.convert(record, responseClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortSpecification getOrderBy(REQ request) {
        EntityMeta entities = Entities.getInstance(this.entityClass);
        String pkField = entities.getPrimaryKey().column().name();
        SortSpecification orderBy = SimpleSortSpecification.of(pkField).descending();
        if (StringUtils.isNotEmpty(request.getOrderBy())) {
            Optional<SqlColumn<Object>> column = entities.findField(request.getOrderBy());
            if (column.isPresent()) {
                SqlColumn<Object> objectSqlColumn = column.get();
                String tableAlias = entities.getTable().tableNameAtRuntime();
                orderBy = new ColumnSortSpecification(tableAlias, objectSqlColumn);
                if (StringUtils.equalsIgnoreCase("desc", request.getSortBy())) {
                    orderBy = orderBy.descending();
                }
            }
        }
        SortSpecification defaultOrderBy = defaultOrderBy();
        return defaultOrderBy == null ? orderBy : defaultOrderBy;
    }

    public GeneralRepository getRepository() {
        return generalRepository;
    }

    public Class<ENTITY> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public Class<REQ> getRequestClass() {
        return this.requestClass;
    }

    @Override
    public Class<RES> getResponseClass() {
        return this.responseClass;
    }
}

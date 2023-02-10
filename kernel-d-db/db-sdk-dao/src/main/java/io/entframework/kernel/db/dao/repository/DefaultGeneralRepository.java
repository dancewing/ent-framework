package io.entframework.kernel.db.dao.repository;

import cn.hutool.core.lang.Assert;
import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.api.util.ClassUtils;
import io.entframework.kernel.db.dao.listener.EntityListeners;
import io.entframework.kernel.db.dao.util.JoinQueryUtils;
import io.entframework.kernel.db.mybatis.mapper.GeneralMapperSupport;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.annotation.JoinColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.relation.JoinDetail;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
public class DefaultGeneralRepository implements GeneralRepository {

    @Resource
    private GeneralMapperSupport generalMapperSupport;

    @Resource
    private EntityListeners entityListeners;

    @Override
    public <T> T insert(T row) {
        entityListeners.beforeInsert(row);
        int count = generalMapperSupport.insert(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        return row;
    }

    @Override
    public <E> List<E> insertMultiple(List<E> records) {
        if (records != null && !records.isEmpty()) {
            Class<E> entityClass = (Class<E>) records.get(0).getClass();
            EntityMeta entities = Entities.getInstance(entityClass);
            List<Field> manyToOneColumns = entities.findColumns(ManyToOne.class);
            manyToOneColumns.forEach(field -> {
                List<Object> tmpManyToOnes = new ArrayList<>();
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                records.forEach(row -> {
                    Object leftFieldValue = ReflectionKit.getFieldValue(row, joinColumn.left());
                    Optional.ofNullable(leftFieldValue).ifPresent(tmpManyToOnes::add);
                });
            });

            entityListeners.beforeInsertMultiple(records);
            int count = generalMapperSupport.insertMultiple(records);
            if (count != records.size()) {
                throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
            }
            return records;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public <E> E insertSelective(E row) {
        entityListeners.beforeInsert(row);
        int count = generalMapperSupport.insertSelective(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        return row;
    }

    @Override
    public int delete(Class<?> entityClass, DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(generalMapperSupport::delete, entityClass, completer);
    }

    @Override
    public int deleteByPrimaryKey(Class<?> entityClass, Serializable id) {
        EntityMeta entities = Entities.getInstance(entityClass);
        SqlColumn<Object> pk = entities.getPrimaryKey().column();
        return this.delete(entityClass, c -> c.where(pk, SqlBuilder.isEqualTo(id)));
    }

    @Override
    public <E> int deleteBy(E row) {
        return this.delete(row.getClass(), this.defaultDeleteDSL(row));
    }

    @Override
    public <E> int delete(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        FieldAndColumn pk = entities.getPrimaryKey();
        Object pkValue = ReflectionKit.getFieldValue(row, entities.getPrimaryKey().fieldName());
        if (pkValue == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        } else {
            Optional<FieldAndColumn> logicDeleteOp = entities.findColumn(LogicDelete.class);
            if (logicDeleteOp.isPresent()) {
                if (log.isDebugEnabled()) {
                    log.debug("Found logicDelete in entity: " + row.getClass().getName());
                }
                FieldAndColumn logicDeleteColumn = logicDeleteOp.get();
                ReflectionKit.setFieldValue(row, logicDeleteColumn.fieldName(), YesOrNotEnum.Y);
                return this.updateByPrimaryKey(row, true);
            } else {
                return this.delete(row.getClass(), c -> c.where(pk.column(), SqlBuilder.isEqualTo(pkValue)));
            }
        }
    }

    @Override
    public int update(Class<?> entityClass, UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(generalMapperSupport::update, entityClass, completer);
    }

    @Override
    public <E> int updateByPrimaryKey(E row, boolean selective) {
        return generalMapperSupport.updateByPrimaryKey(row, selective);
    }

    @Override
    public <E> E update(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        Assert.notNull(entities);
        int count = this.updateByPrimaryKey(row, false);
        if (count == 0) {
            Object pk = ReflectionKit.getFieldValue(row, entities.getPrimaryKey().field().getName());
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, pk);
        }
        return row;
    }

    @Override
    public long count(Class<?> entityClass, CountDSLCompleter completer) {
        EntityMeta entities = Entities.getInstance(entityClass);
        return MyBatis3Utils.countFrom(generalMapperSupport::count, entities.getTable(), completer);
    }

    @Override
    public <E> long countBy(E row) {
        return this.count(row.getClass(), this.defaultCountDSL(row));
    }

    @Override
    public <E> Optional<E> selectOne(Class<E> entityClass, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(generalMapperSupport::selectOne, entityClass, completer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> Optional<E> selectOne(E row, boolean cascade, SortSpecification orderBy) {
        Assert.notNull(row);
        Class<E> clazz = (Class<E>) row.getClass();
        EntityMeta entities = Entities.getInstance(row.getClass());
        if (entities.hasRelation() && cascade) {
            List<JoinDetail> joinDetails = entities.getAllJoinDetails();
            return JoinQueryUtils.leftJoinSelectOne(generalMapperSupport::selectOne, entities.getAllJoinSelectColumns(),
                    row.getClass(), joinDetails, defaultQuerySelectDSL(row, 1, 2, orderBy));
        }
        return this.selectOne(clazz, defaultSelectDSL(row, 1, 2, orderBy));
    }

    @Override
    public <E> List<E> select(Class<E> entityClass, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(generalMapperSupport::selectMany, entityClass, completer);
    }

    @Override
    public <E> List<E> select(Class<E> entityClass, QueryExpressionDSL<SelectModel> queryExpression, boolean cascade) {
        EntityMeta entities = Entities.getInstance(entityClass);
        if (entities.hasManyToOne() && cascade) {
            List<JoinDetail> joinDetails = entities.getManyToOneJoinDetails();
            return JoinQueryUtils.leftJoinSelectList(generalMapperSupport::selectMany, entities.getManyToOneSelectColumns(),
                    entityClass, joinDetails, queryExpression);
        }
        SelectStatementProvider selectStatement = queryExpression.build().render(RenderingStrategies.MYBATIS3);
        return generalMapperSupport.selectMany(selectStatement);
    }

    @Override
    public <E> List<E> selectDistinct(Class<E> entityClass, SelectDSLCompleter completer) {
        EntityMeta entities = Entities.getInstance(entityClass);
        return MyBatis3Utils.selectDistinct(generalMapperSupport::selectMany, entities.getSelectColumns(), entities.getTable(), completer);
    }

    @Override
    public <E> Optional<E> selectByPrimaryKey(Class<E> entityClass, Serializable id) {
        EntityMeta entities = Entities.getInstance(entityClass);
        SqlColumn<Object> pk = entities.getPrimaryKey().column();
        return this.selectOne(entityClass, c ->
                c.where(pk, SqlBuilder.isEqualTo(id))
        );
    }

    @Override
    public <E> List<E> selectBy(E row, Integer pageNo, Integer pageSize, boolean cascade, SortSpecification sortBy) {
        Assert.notNull(row);
        EntityMeta entities = Entities.getInstance(row.getClass());
        if (entities.hasManyToOne() && cascade) {
            List<JoinDetail> joinDetails = entities.getManyToOneJoinDetails();
            return JoinQueryUtils.leftJoinSelectList(generalMapperSupport::selectMany, entities.getManyToOneSelectColumns(),
                    row.getClass(), joinDetails, defaultQuerySelectDSL(row, pageNo, pageSize, sortBy));
        }
        return generalMapperSupport.select((Class<E>) row.getClass(), defaultSelectDSL(row, pageNo, pageSize, sortBy));
    }

    @Override
    public <E> E get(Class<E> entityClass, Serializable id) {
        EntityMeta entities = Entities.getInstance(entityClass);
        Optional<E> result;
        if (entities.hasManyToOne()) {
            E query = ClassUtils.newInstance(entityClass);
            ReflectionKit.setFieldValue(query, entities.getPrimaryKey().fieldName(), id);
            result = this.selectOne(query);
        } else {
            result = this.selectByPrimaryKey(entityClass, id);
        }
        if (result.isPresent()) {
            E entity = result.get();
            log.info("return value type: " + entity.getClass().getName());
            Optional<FieldAndColumn> logicDeleteOp = entities.findColumn(LogicDelete.class);
            if (logicDeleteOp.isPresent()) {
                FieldAndColumn logicDeleteColumn = logicDeleteOp.get();
                YesOrNotEnum logicDeleteColumnValue = (YesOrNotEnum) ReflectionKit.getFieldValue(entity, logicDeleteColumn.fieldName());
                if (logicDeleteColumnValue != null && logicDeleteColumnValue != YesOrNotEnum.N) {
                    throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
                }
            }
            return entity;
        } else {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
        }
    }
}

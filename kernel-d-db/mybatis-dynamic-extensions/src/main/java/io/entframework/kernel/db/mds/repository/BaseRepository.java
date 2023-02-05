/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.repository;

import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.db.mds.meta.Entities;
import io.entframework.kernel.db.mds.meta.EntityMeta;
import io.entframework.kernel.db.mds.meta.FieldMeta;
import io.entframework.kernel.db.mds.util.MyBatis3CustomUtils;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.DeleteModel;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.*;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.where.AbstractWhereDSL;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

public interface BaseRepository<E> {
    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 新增数据库记录，单表操作，如果Entity数据有关联数据，不会插入<br/>
     * 支持逻辑删除和乐观锁字段值的初始化<br/>
     * <br/>
     * <b color="#99FF99">>>可以使用<<</b>
     *
     * @see BaseMapper#insert(Object)
     */
    E insert(E row);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 批量插入数据，参考insert方法<br/>
     * <br/>
     * <b color="#E63F00">>>可以使用<<</b>
     *
     * @see BaseMapper#insertMultiple(Collection)
     */
    List<E> insertMultiple(List<E> records);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 选择性插入，如果Entity中字段缺值，则改字段不会出现在生成的Insert语句中<br/>
     * 支持逻辑删除和乐观锁字段值的初始化<br/>
     * 但是考虑表字段新增字段可能非空，<b>不建议使用</b><br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#insertSelective(Object)
     */
    E insertSelective(E row);

    /**
     * <b>MyBatis Dynamic SQL 底层方法</b><br/><br/>
     * InsertStatementProvider通常由Entity生成，最终生成Insert语句进行数据库操作<br/>
     * DeleteDSLCompleter相当于Where查询条件，执行时会直接操作数据库<br/>
     * 逻辑删除或者乐观锁不会影响（这两个字段的初始化由框架的Interceptor）<br/>
     * <a href="https://mybatis.org/mybatis-dynamic-sql/docs/insert.html">官方文档</a><br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    int insert(InsertStatementProvider<E> statement);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据DeleteDSLCompleter删除(物理)，并返回生效的记录数，谨慎使用<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#delete(DeleteDSLCompleter)
     */
    int delete(DeleteDSLCompleter completer);

    /**
     * <b>MyBatis Dynamic SQL 底层方法</b><br/><br/>
     * DeleteStatementProvider通常由Entity生成，最终生成Delete语句进行数据库操作<br/>
     * 逻辑删除或者乐观锁会受到影响，因为这项操作是物理删除<br/>
     * <a href="https://mybatis.org/mybatis-dynamic-sql/docs/delete.html">官方文档</a><br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    int delete(DeleteStatementProvider statement);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据主键ID删除（物理）记录，谨慎使用<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#deleteByPrimaryKey(Object)
     */
    int deleteByPrimaryKey(Object id);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据Entity的值构造删除条件<br/>
     * 注意如果支持逻辑删除，只是更新逻辑删除标记位<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    int deleteBy(E row);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据主键ID删除记录<br/>
     * 注意如果支持逻辑删除，只是更新逻辑删除标记位，会在EntityRepositoryImpl类中自动生成此方法<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    int delete(E row);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据UpdateDSLCompleter生成Update语句进行数据库操作<br/>
     * 逻辑删除或者乐观锁会受到影响<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#update(UpdateDSLCompleter)
     */
    int update(UpdateDSLCompleter completer);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据ID根据数据库记录，如果Entity字段没有值，数据库库字段会被置为Null<br/>
     * 乐观锁会受到影响<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#updateByPrimaryKey(Object)
     */
    int updateByPrimaryKey(E row);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据ID根据数据库记录，如果Entity字段没有值，则不会更新<br/>
     * 乐观锁会受到影响<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#updateByPrimaryKey(Object, boolean)
     */
    int updateByPrimaryKeySelective(E row);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据Entity生成Update语句，更新数据库<br/>
     * 支持乐观锁<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    E update(E row);

    /**
     * <b>MyBatis Dynamic SQL 底层方法</b><br/><br/>
     * UpdateStatementProvider通常由Entity生成，最终生成Update语句进行数据库操作<br/>
     * 逻辑删除或者乐观锁会受到影响，除非手动指定这两项值<br/>
     * <a href="https://mybatis.org/mybatis-dynamic-sql/docs/update.html">官方文档</a><br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    int update(UpdateStatementProvider statement);


    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据CountDSLCompleter统计符合查询条件的记录总数<br/>
     * 如果支持逻辑删除，需要确认逻辑删除标记位对查询结果的影响，合理设值<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     *
     * @see BaseMapper#count(CountDSLCompleter)
     */
    long count(CountDSLCompleter completer);

    long countBy(E row);


    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 单表查询的高阶用法，返回多条记录<br/>
     * 手动组装查询条件，筛选列等<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    List<E> selectMany(SelectStatementProvider selectStatement);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 单表查询的高阶用法，返回单条记录<br/>
     * 手动组装查询条件，筛选列等<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    Optional<E> selectOne(SelectStatementProvider selectStatement);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据SelectDSLCompleter查询符合条件的单条记录，如果多条记录符合条件，抛出异常<br/>
     * 如果支持逻辑删除，需要确认逻辑删除标记位对查询结果的影响，合理设值<br/>
     * <br/>
     * <b color="#E63F00">>>可以使用<<</b>
     *
     * @see BaseMapper#selectOne(SelectDSLCompleter)
     */
    Optional<E> selectOne(SelectDSLCompleter completer);

    /**
     * @see BaseRepository#selectOne(Object, boolean)
     */
    Optional<E> selectOne(E row);


    Optional<E> selectOne(E row, boolean cascade);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据Entity转成的SelectDSLCompleter，查询符合条件的一条记录<br/>
     * cascade为true，并且实体类有Many-TO-One属性，会自动关联查询<br/>
     * 支持逻辑删除，默认查询非删除状态的记录<br/>
     * <br/>
     *
     * @see BaseRepository#selectBy(Object)
     */
    Optional<E> selectOne(E row, boolean cascade, SortSpecification orderBy);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据SelectDSLCompleter查询符合条件的多条记录<br/>
     * 如果支持逻辑删除，需要确认逻辑删除标记位对查询结果的影响，合理设值<br/>
     * <br/>
     * <b color="#E63F00">>>可以使用<<</b>
     *
     * @see BaseMapper#select(SelectDSLCompleter)
     */
    List<E> select(SelectDSLCompleter completer);

    List<E> select(QueryExpressionDSL<SelectModel> queryExpression);

    List<E> select(QueryExpressionDSL<SelectModel> queryExpression, boolean cascade);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据SelectDSLCompleter查询符合条件的不重复的多条记录<br/>
     * 如果支持逻辑删除，需要确认逻辑删除标记位对查询结果的影响，合理设值<br/>
     * <br/>
     * <b color="#E63F00">>>可以使用<<</b>
     *
     * @see BaseMapper#selectDistinct(SelectDSLCompleter)
     */
    List<E> selectDistinct(SelectDSLCompleter completer);

    /**
     * <b>MyBatis Mapper代理方法</b><br/><br/>
     * 根据ID获取数据库单条记录<br/>
     * 如果支持逻辑删除，确认是否需要对数据状态对业务处理<br/>
     * <br/>
     * <b color="#E63F00">>>可以使用<<</b>
     *
     * @see BaseMapper#selectByPrimaryKey(Object)
     */
    Optional<E> selectByPrimaryKey(Object id);

    /**
     * @see BaseRepository#selectBy(Object, Integer, Integer, boolean)
     */
    List<E> selectBy(E row);

    /**
     * @see BaseRepository#selectBy(Object, Integer, Integer, boolean)
     */
    List<E> selectBy(E row, boolean cascade);

    List<E> selectBy(E row, Integer pageNo, Integer pageSize, boolean cascade);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据Entity转成的SelectDSLCompleter，查询符合条件的记录<br/>
     * 注意这是单表全字段查询，支持支持分页<br/>
     * 支持逻辑删除，默认查询非删除状态的记录<br/>
     * <br/>
     *
     * @param row      传入的查询条件
     * @param pageNo   第x页，从1开始
     * @param pageSize 每页数量
     * @param cascade  是否支持关联查询
     * @param sortBy   排序字段
     */
    List<E> selectBy(E row, Integer pageNo, Integer pageSize, boolean cascade, SortSpecification sortBy);


    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 将Entity转成SelectDSLCompleter，最终生成Select语句进行数据库操作<br/>
     * <br/>
     *
     * @see BaseRepository#defaultSelectDSL(E, Integer, Integer, SortSpecification)
     */
    SelectDSLCompleter defaultSelectDSL(E row, SortSpecification orderBy);

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 根据ID获取数据库单条记录
     * 支持逻辑删除，如果查询到的记录已标记为删除则抛出异常<br/>
     * 如果找不到记录也会抛出异常<br/>
     * 只需要装载数据，可用selectByPrimaryKey方法<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     *
     * @see BaseMapper#selectByPrimaryKey(Object)
     */
    E get(Object id);

    /**
     * <b>MyBatis Dynamic SQL 底层方法</b><br/><br/>
     * 将Entity转换成DeleteDSLCompleter<br/>
     * DeleteDSLCompleter相当于Where查询条件，执行时会直接操作数据库<br/>
     * 逻辑删除或者乐观锁会被直接跳过<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    default DeleteDSLCompleter defaultDeleteDSL(E row) {
        return c -> {
            DeleteDSL<DeleteModel>.DeleteWhereBuilder deleteDSL = c.where();
            deleteDSL.configureStatement(config -> config.setNonRenderingWhereClauseAllowed(true));
            applyUpdateWhereBuilder(deleteDSL, row);
            return deleteDSL;
        };
    }

    /**
     * <b>MyBatis 包装方法</b><br/><br/>
     * 将Entity转成SelectDSLCompleter，最终生成Select语句进行数据库操作<br/>
     * 注意这是单表全字段查询，支持分页<br/>
     * 支持逻辑删除，默认查询非删除状态的记录<br/>
     * <br/>
     */
    default SelectDSLCompleter defaultSelectDSL(E row, Integer pageNo, Integer pageSize, SortSpecification orderBy) {
        return c -> {
            QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder selectDSL = c.where();
            applyQueryWhereBuilder(selectDSL, row);
            if (orderBy != null) {
                selectDSL.orderBy(orderBy);
            }
            return MyBatis3CustomUtils.buildPagination(c, pageNo, pageSize);
        };
    }

    default CountDSLCompleter defaultCountDSL(E entity) {
        return c -> {
            CountDSL<SelectModel>.CountWhereBuilder countDSL = c.where();
            applyQueryWhereBuilder(countDSL, entity);
            return countDSL;
        };
    }

    default QueryExpressionDSL<SelectModel> defaultQuerySelectDSL(E row, Integer pageNo, Integer pageSize, SortSpecification orderBy) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        QueryExpressionDSL<SelectModel> start = SqlBuilder.select(entities.getSelectColumns()).from(entities.getTable());
        QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder queryDSL = start.where();
        applyQueryWhereBuilder(queryDSL, row);
        if (orderBy != null) {
            queryDSL.orderBy(orderBy);
        }
        if (pageSize != null && pageSize >= 0) {
            start.limit(pageSize);
            if (pageNo != null && pageNo - 1 >= 0) {
                int offset = (pageNo - 1) * pageSize;
                start.offset(offset);
            }
        }
        return start;
    }

    /**
     * <b>MyBatis Mapper封装方法</b><br/><br/>
     * 根据Entity构造Where 查询条件<br/>
     * 如果启用逻辑删除，自动生成非删除状态的Where条件
     * <br/>
     * <p color="#E63F00">>>推荐使用<<</p>
     */
    default void applyQueryWhereBuilder(AbstractWhereDSL<?> whereDSL, E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        for (FieldMeta wrapper : entities.getColumns()) {
            Object value = ReflectionKit.hasField(row, wrapper.getField().getName()) ?
                    ReflectionKit.getFieldValue(row, wrapper.getField().getName()) : null;
            if (wrapper.isLogicDelete() && value == null) {
                whereDSL.and(wrapper.getColumn(), isEqualTo(YesOrNotEnum.N));
            } else {
                whereDSL.and(wrapper.getColumn(), isEqualTo(value).filter(Objects::nonNull));
            }
        }
    }

    /**
     * <b>MyBatis Mapper封装方法</b><br/><br/>
     * 根据Entity构造Where 更新/删除条件<br/>
     * 如果启用逻辑删除，自动生成非删除状态的Where条件
     * <br/>
     * <p color="#E63F00">>>推荐使用<<</p>
     */
    default void applyUpdateWhereBuilder(AbstractWhereDSL<?> whereDSL, E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        for (FieldMeta wrapper : entities.getColumns()) {
            Object value = ReflectionKit.hasField(row, wrapper.getField().getName()) ?
                    ReflectionKit.getFieldValue(row, wrapper.getField().getName()) : null;
            if (wrapper.isLogicDelete() && value == null) {
                whereDSL.and(wrapper.getColumn(), isEqualTo(YesOrNotEnum.N));
            } else {
                whereDSL.and(wrapper.getColumn(), isEqualTo(value).filter(Objects::nonNull));
            }
        }
    }

    Class<E> getEntityClass();
}

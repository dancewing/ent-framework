/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.dao.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;

import java.io.Serializable;
import java.util.List;

public interface BaseService<REQ extends BaseRequest, RES, ENTITY> {

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request构造Where查询符合条件的多条记录<br/>
     * 单表查询，不支持分页<br/>
     * 如果启用逻辑删除，只查询非删除状态的记录<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     *
     * @see BaseRepository#select(SelectDSLCompleter)
     */
    List<RES> select(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request构造Where查询符合条件的多条记录<br/>
     * 如果包含关联表，则多表查询，支持分页<br/>
     * 如果启用逻辑删除，只查询非删除状态的记录<br/>
     * 返回结果为List，与page方法区别在是否包装返回结果<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    List<RES> list(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request构造Where查询符合条件的多条记录<br/>
     * 如果包含关联表，则多表查询，支持分页<br/>
     * 如果启用逻辑删除，只查询非删除状态的记录<br/>
     * 返回结果使用Page包装，方便前端显示<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    PageResult<RES> page(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request传入的值构造Where查询符合条件的单条记录<br/>
     * 如果包含关联表，则多表查询<br/>
     * 注意与get方法的区别<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    RES selectOne(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request构造Where查询符合条件的记录总数<br/>
     * 如果启用逻辑删除，只统计非删除状态的记录<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    long countBy(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request构造Where查询条件并删除符合条件的记录<br/>
     * 如果启用逻辑删除，需要谨慎使用<br/>
     * <br/>
     * <b color="#E63F00">>>谨慎使用<<</b>
     */
    int deleteBy(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request传入的主键ID删除符合条件的单条记录<br/>
     * 如果启用逻辑删除，只是更新记录的删除状态<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    int delete(REQ request);

    /**
     * <b>Repository代理方法</b><br/>
     * <br/>
     * 根据Request传入的主键ID集合批量删除对应记录<br/>
     * 如果启用逻辑删除，只是更新记录的删除状态<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    int batchDelete(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request传入的主键ID集合批量删除对应记录<br/>
     * 如果启用逻辑删除，只是更新记录的删除状态<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    RES insert(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 批量创建记录<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    List<RES> insertMultiple(List<REQ> request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据Request传入的主键ID获取对应记录<br/>
     * 如果包含关联表，则多表查询<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    RES load(Object id);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 根据传入ID获取对应记录<br/>
     * 不查询关联表<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    RES get(Serializable id);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 将Request传入值根据主键ID更新对应记录<br/>
     * 支持乐观锁<br/>
     * <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    RES update(REQ request);

    /**
     * <b>Service封装方法</b><br/>
     * <br/>
     * 获取排序字段的工具方法 <br/>
     * <b color="#99FF99">>>建议使用<<</b>
     */
    SortSpecification getOrderBy(REQ request);

    /**
     * 默认的排序
     * @return SortSpecification
     */
    default SortSpecification defaultOrderBy() {
        return null;
    }

    Class<REQ> getRequestClass();

    Class<RES> getResponseClass();

}

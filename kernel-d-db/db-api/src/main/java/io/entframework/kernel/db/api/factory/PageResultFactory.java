/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * 分页的返回结果创建工厂
 * <p>
 * 一般由mybatis-plus的Page对象转为PageResult
 *
 * @date 2020/10/15 16:57
 */
public class PageResultFactory {

	public static <T, R> PageResult<R> convertPage(PageResult<T> page, Function<T, R> func) {
		PageResult<R> pageResult = new PageResult<>();
		pageResult.setItems(page.getItems().stream().map(func).toList());
		pageResult.setTotalRows(page.getTotalRows());
		pageResult.setPageNo(page.getPageNo());
		pageResult.setPageSize(page.getPageSize());
		pageResult.setTotalPage(page.getTotalPage());
		return pageResult;
	}

	/**
	 * 将mybatis-plus的page转成自定义的PageResult，扩展了totalPage总页数
	 *
	 * @date 2020/10/15 15:53
	 */
	public static <T> PageResult<T> createPageResult(List<T> rows, Long count, Integer pageSize, Integer pageNo) {
		PageResult<T> pageResult = new PageResult<>();
		pageResult.setItems(rows);
		pageResult.setTotalRows(Convert.toInt(count));
		pageResult.setPageNo(pageNo);
		pageResult.setPageSize(pageSize);
		pageResult.setTotalPage(PageUtil.totalPage(pageResult.getTotalRows(), pageSize));
		return pageResult;
	}

}

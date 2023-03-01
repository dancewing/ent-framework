/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.db.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.log.api.LogManagerApi;
import io.entframework.kernel.log.api.pojo.manage.SysLogRequest;
import io.entframework.kernel.log.api.pojo.manage.SysLogResponse;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志管理，数据库实现
 *
 * @date 2020/11/2 17:40
 */
@Slf4j
public class DbLogManagerServiceImpl implements LogManagerApi {

	@Resource
	private SysLogService sysLogService;

	@Override
	public List<LogRecordDTO> findList(SysLogRequest sysLogRequest) {
		List<SysLogResponse> sysLogList = this.sysLogService.findList(sysLogRequest);
		List<LogRecordDTO> logRecordDTOList = CollUtil.newArrayList();
		BeanUtil.copyProperties(sysLogList, logRecordDTOList);
		return logRecordDTOList;
	}

	@Override
	public PageResult<LogRecordDTO> findPage(SysLogRequest sysLogRequest) {
		PageResult<SysLogResponse> sysLogPageResult = this.sysLogService.findPage(sysLogRequest);

		// 分页类型转换
		PageResult<LogRecordDTO> logRecordDTOPageResult = new PageResult<>();
		BeanUtil.copyProperties(sysLogPageResult, logRecordDTOPageResult);

		// 转化数组
		List<SysLogResponse> rows = sysLogPageResult.getItems();
		ArrayList<LogRecordDTO> newRows = new ArrayList<>();
		for (SysLogResponse row : rows) {
			LogRecordDTO logRecordDTO = new LogRecordDTO();
			BeanUtil.copyProperties(row, logRecordDTO);

			// 设置请求和响应为空，减少带宽开销
			logRecordDTO.setRequestResult(null);
			logRecordDTO.setRequestResult(null);
			newRows.add(logRecordDTO);
		}
		logRecordDTOPageResult.setItems(newRows);

		return logRecordDTOPageResult;
	}

	@Override
	public void del(SysLogRequest sysLogRequest) {
		this.sysLogService.del(sysLogRequest);
	}

	@Override
	public LogRecordDTO detail(SysLogRequest sysLogRequest) {
		SysLogResponse detail = this.sysLogService.detail(sysLogRequest);
		LogRecordDTO logRecordDTO = new LogRecordDTO();
		BeanUtil.copyProperties(detail, logRecordDTO);
		return logRecordDTO;
	}

}

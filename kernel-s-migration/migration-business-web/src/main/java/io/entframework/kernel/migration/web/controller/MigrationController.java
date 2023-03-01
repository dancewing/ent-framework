/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.migration.web.controller;

import io.entframework.kernel.migration.api.pojo.MigrationAggregationPOJO;
import io.entframework.kernel.migration.web.pojo.MigrationRequest;
import io.entframework.kernel.migration.web.service.MigrationService;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据迁移控制器
 *
 * @date 2021/7/6 17:35
 */
@RestController
@ApiResource(name = "数据迁移控制器")
public class MigrationController {

	@Autowired
	private MigrationService migrationService;

	/**
	 * 获取所有可备份数据列表
	 * @return {@link ResponseData}
	 * @date 2021/7/6 17:37
	 **/
	@GetResource(name = "获取所有可备份数据列表", path = "/data-migration/get-all-migration-list")
	public ResponseData<List<MigrationRequest>> getAllMigrationList() {
		List<MigrationRequest> migrationRequestList = migrationService.getAllMigrationList();
		return ResponseData.ok(migrationRequestList);
	}

	/**
	 * 备份指定数据列表
	 * @return {@link ResponseData}
	 * @date 2021/7/7 11:11
	 **/
	@GetResource(name = "备份指定数据列表", path = "/data-migration/migration-select-data")
	public ResponseData<String> migrationSelectData(
			@Validated(MigrationAggregationPOJO.export.class) MigrationAggregationPOJO migrationAggregationPOJO) {
		List<String> res = new ArrayList<>();
		for (String s : migrationAggregationPOJO.getAppAndModuleNameList()) {
			try {
				String decode = URLDecoder.decode(s, "UTF-8");
				res.add(decode);
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		migrationAggregationPOJO.setAppAndModuleNameList(res);
		String migrationSelectDataStr = migrationService.migrationSelectData(migrationAggregationPOJO);
		return ResponseData.ok(migrationSelectDataStr);
	}

	/**
	 * 恢复备份数据
	 * @return {@link ResponseData}
	 * @date 2021/7/7 11:11
	 **/
	@PostResource(name = "恢复备份数据", path = "/data-migration/restore-data")
	public ResponseData<Void> restoreData(@RequestPart("file") MultipartFile file, String type) {
		migrationService.restoreData(file, type);
		return ResponseData.OK_VOID;
	}

}

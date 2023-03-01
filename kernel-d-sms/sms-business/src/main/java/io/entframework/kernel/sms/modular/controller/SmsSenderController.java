/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.sms.modular.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.sms.modular.pojo.SysSmsSendParam;
import io.entframework.kernel.sms.modular.pojo.SysSmsVerifyParam;
import io.entframework.kernel.sms.modular.pojo.request.SysSmsRequest;
import io.entframework.kernel.sms.modular.pojo.response.SysSmsResponse;
import io.entframework.kernel.sms.modular.service.SysSmsService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 短信发送控制器
 *
 * @date 2020/10/26 18:34
 */
@RestController
@ApiResource(name = "短信发送控制器")
public class SmsSenderController {

	@Resource
	private SysSmsService sysSmsService;

	/**
	 * 发送记录查询
	 *
	 * @date 2020/10/26 18:34
	 */
	@GetResource(name = "发送记录查询", path = "/sms/page")
	public ResponseData<PageResult<SysSmsResponse>> page(SysSmsRequest sysSmsRequest) {
		return ResponseData.ok(sysSmsService.page(sysSmsRequest));
	}

	/**
	 * 发送验证码短信
	 *
	 * @date 2020/10/26 18:34
	 */
	@PostResource(name = "发送验证码短信", path = "/sms/send-login-message", requiredLogin = false, requiredPermission = false)
	public ResponseData<Boolean> sendMessage(@RequestBody @Validated SysSmsSendParam sysSmsSendParam) {

		// 清空params参数
		sysSmsSendParam.setParams(null);

		// 设置模板中的参数
		HashMap<String, Object> paramMap = MapUtil.newHashMap();
		paramMap.put("code", RandomUtil.randomNumbers(6));
		sysSmsSendParam.setParams(paramMap);

		return ResponseData.ok(sysSmsService.sendShortMessage(sysSmsSendParam));
	}

	/**
	 * 验证短信验证码
	 *
	 * @date 2020/10/26 18:35
	 */
	@PostResource(name = "验证短信验证码", path = "/sms/validateMessage", requiredLogin = false, requiredPermission = false)
	public ResponseData<String> validateMessage(@RequestBody @Validated SysSmsVerifyParam sysSmsVerifyParam) {
		sysSmsService.validateSmsInfo(sysSmsVerifyParam);
		return ResponseData.ok("短信验证成功");
	}

}

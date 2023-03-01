/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.provider;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.loginuser.api.LoginUserRemoteApi;
import io.entframework.kernel.auth.api.loginuser.pojo.LoginUserRequest;
import io.entframework.kernel.auth.api.loginuser.pojo.SessionValidateResponse;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.system.api.UserServiceApi;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 图形验证码
 *
 * @date 2021/1/15 15:11
 */
@RestController
public class LoginUserProvider implements LoginUserRemoteApi {

	@Resource
	private SessionManagerApi sessionManagerApi;

	@Resource
	private UserServiceApi userServiceApi;

	@Override
	public LoginUser getLoginUserByToken(@RequestBody LoginUserRequest loginUserRequest) {
		if (CharSequenceUtil.isBlank(loginUserRequest.getToken())) {
			throw new SystemModularException(SysUserExceptionEnum.TOKEN_EMPTY);
		}
		return sessionManagerApi.getSession(loginUserRequest.getToken());
	}

	@Override
	public SessionValidateResponse haveSession(@RequestParam("token") String token) {
		boolean validateFlag = sessionManagerApi.haveSession(token);
		return new SessionValidateResponse(validateFlag);
	}

	@Override
	public LoginUser getEffectiveLoginUser(@RequestBody LoginUser loginUser) {
		return userServiceApi.getEffectiveLoginUser(loginUser);
	}

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.context;

import io.entframework.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除当前登录用户相关的ThreadLocalHolder
 *
 * @date 2021/10/29 11:41
 */
@Component
public class LoginUserRemoveThreadLocalHolder implements RemoveThreadLocalApi {

	@Override
	public void removeThreadLocalAction() {
		LoginUserHolder.remove();
	}

}

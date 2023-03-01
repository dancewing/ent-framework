/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.scanner.api.holder;

import io.entframework.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除ip地址相关的ThreadLocalHolder
 *
 * @date 2021/10/29 11:42
 */
@Component
public class IpAddrRemoveThreadLocalHolder implements RemoveThreadLocalApi {

	@Override
	public void removeThreadLocalAction() {
		IpAddrHolder.clear();
	}

}

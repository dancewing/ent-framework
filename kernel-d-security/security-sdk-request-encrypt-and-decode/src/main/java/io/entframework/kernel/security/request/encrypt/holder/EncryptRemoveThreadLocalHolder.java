/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.request.encrypt.holder;

import io.entframework.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除加解密的相关ThreadLocal
 *
 * @date 2021/10/29 11:37
 */
@Component
public class EncryptRemoveThreadLocalHolder implements RemoveThreadLocalApi {

	@Override
	public void removeThreadLocalAction() {
		EncryptionHolder.clearAesKey();
	}

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.request.encrypt.holder;

import cn.hutool.crypto.asymmetric.RSA;
import io.entframework.kernel.security.request.encrypt.constants.EncryptionConstants;

/**
 * 用于存储RSA实例
 *
 * @date 2021/6/4 08:58
 */
public class EncryptionRsaHolder {

    public static RSA STATIC_RSA = new RSA(EncryptionConstants.PRIVATE_KEY, EncryptionConstants.PUBLIC_KEY);

}

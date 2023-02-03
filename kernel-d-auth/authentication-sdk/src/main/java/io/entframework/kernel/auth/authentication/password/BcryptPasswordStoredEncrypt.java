/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.authentication.password;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.BCrypt;
import io.entframework.kernel.auth.api.password.PasswordStoredEncryptApi;

/**
 * 基于BCrypt算法实现的密码加密解密器
 *
 * @date 2020/12/21 17:02
 */
public class BcryptPasswordStoredEncrypt implements PasswordStoredEncryptApi {

    @Override
    public String encrypt(String originPassword) {
        if (CharSequenceUtil.isBlank(originPassword)) {
            return null;
        }

        return BCrypt.hashpw(originPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String encryptBefore, String encryptAfter) {
        return BCrypt.checkpw(encryptBefore, encryptAfter);
    }

}

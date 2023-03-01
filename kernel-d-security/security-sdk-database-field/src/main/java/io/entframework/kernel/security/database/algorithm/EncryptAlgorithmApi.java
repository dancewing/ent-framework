/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.database.algorithm;

/**
 * 加密算法接口
 * <p>
 * 可根据自身需要自定义实现，默认实现为AES
 *
 * @date 2021/7/3 11:02
 */
public interface EncryptAlgorithmApi {

	/**
	 * 加密算法
	 * @param encryptedData 加密数据
	 * @return {@link java.lang.String}
	 * @date 2021/7/3 11:07
	 **/
	String encrypt(String encryptedData);

	/**
	 * 解密算法
	 * @param cipher 待解密密文
	 * @return {@link java.lang.String}
	 * @date 2021/7/3 11:33
	 **/
	String decrypt(String cipher);

}

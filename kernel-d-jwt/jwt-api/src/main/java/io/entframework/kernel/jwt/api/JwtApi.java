/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.jwt.api;

import io.entframework.kernel.jwt.api.exception.JwtException;
import io.entframework.kernel.jwt.api.pojo.payload.DefaultJwtPayload;

import java.util.Map;

/**
 * jwt相关的操作api
 *
 * @date 2020/10/21 11:31
 */
public interface JwtApi {

	/**
	 * 生成token
	 * @param payload jwt的载体信息
	 * @return jwt token
	 * @date 2020/10/21 11:38
	 */
	String generateToken(Map<String, Object> payload);

	/**
	 * 生成token，用默认的payload格式
	 * @param defaultJwtPayload jwt的载体信息
	 * @return jwt token
	 * @date 2020/10/21 11:38
	 */
	String generateTokenDefaultPayload(DefaultJwtPayload defaultJwtPayload);

	/**
	 * 获取jwt的payload（通用的）
	 * @param token jwt的token
	 * @return jwt的payload
	 * @date 2020/10/21 11:52
	 */
	Map<String, Object> getJwtPayloadClaims(String token);

	/**
	 * 获取jwt的payload（限定默认格式）
	 * @param token jwt的token
	 * @return 返回默认格式的payload
	 * @date 2020/10/21 11:51
	 */
	DefaultJwtPayload getDefaultPayload(String token);

	/**
	 * 校验jwt token是否正确
	 * <p>
	 * 不正确的token有两种：
	 * <p>
	 * 1. 第一种是jwt token过期了 2. 第二种是jwt本身是错误的
	 * <p>
	 * 本方法只会响应正确还是错误
	 * @param token jwt的token
	 * @return true-token正确，false-token错误或失效
	 * @date 2020/10/21 11:43
	 */
	boolean validateToken(String token);

	/**
	 * 校验jwt token是否正确，如果参数token是错误的会抛出对应异常
	 * <p>
	 * 不正确的token有两种：
	 * <p>
	 * 1. 第一种是jwt token过期了 2. 第二种是jwt本身是错误的
	 * @param token jwt的token
	 * @throws JwtException Jwt相关的业务异常
	 * @date 2020/10/21 11:43
	 */
	void validateTokenWithException(String token) throws JwtException;

	/**
	 * 校验jwt token是否失效了
	 * @param token jwt token
	 * @return true-token失效，false-token没失效
	 * @date 2020/10/21 11:56
	 */
	boolean validateTokenIsExpired(String token);

}

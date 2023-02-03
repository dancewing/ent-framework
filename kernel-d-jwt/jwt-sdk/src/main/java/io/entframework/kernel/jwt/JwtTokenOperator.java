/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.entframework.kernel.jwt.api.JwtApi;
import io.entframework.kernel.jwt.api.config.JwtProperties;
import io.entframework.kernel.jwt.api.exception.JwtException;
import io.entframework.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import io.entframework.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static io.entframework.kernel.jwt.api.exception.enums.JwtExceptionEnum.JWT_PARSE_ERROR;

/**
 * jwt token的操作对象
 *
 * @date 2020/10/31 15:33
 */
public class JwtTokenOperator implements JwtApi {

    private final JwtProperties jwtProperties;

    public JwtTokenOperator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateToken(Map<String, Object> payload) {

        // 计算过期时间
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(jwtProperties.getExpiredSeconds()));

        // 构造jwt token
        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getJwtSecret())
                .compact();
    }

    @Override
    public String generateTokenDefaultPayload(DefaultJwtPayload defaultJwtPayload) {

        // 计算过期时间
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(jwtProperties.getExpiredSeconds()));

        // 设置过期时间
        defaultJwtPayload.setExpirationDate(expirationDate.getTime());

        Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getJwtSecret().getBytes());
        // 构造jwt token
        return Jwts.builder()
                .setClaims(BeanUtil.beanToMap(defaultJwtPayload))
                .setSubject(defaultJwtPayload.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims getJwtPayloadClaims(String token) {
        Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getJwtSecret().getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public DefaultJwtPayload getDefaultPayload(String token) {
        Map<String, Object> jwtPayload = getJwtPayloadClaims(token);
        return BeanUtil.toBeanIgnoreError(jwtPayload, DefaultJwtPayload.class);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getJwtPayloadClaims(token);
            return true;
        } catch (io.jsonwebtoken.JwtException jwtException) {
            return false;
        }
    }

    @Override
    public void validateTokenWithException(String token) throws JwtException {

        // 1.先判断是否是token过期了
        boolean tokenIsExpired = this.validateTokenIsExpired(token);
        if (tokenIsExpired) {
            throw new JwtException(JwtExceptionEnum.JWT_EXPIRED_ERROR, token);
        }

        // 2.判断是否是jwt本身的错误
        try {
            getJwtPayloadClaims(token);
        } catch (io.jsonwebtoken.JwtException jwtException) {
            throw new JwtException(JWT_PARSE_ERROR, token);
        }
    }

    @Override
    public boolean validateTokenIsExpired(String token) {
        try {
            Claims claims = getJwtPayloadClaims(token);
            final Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

}

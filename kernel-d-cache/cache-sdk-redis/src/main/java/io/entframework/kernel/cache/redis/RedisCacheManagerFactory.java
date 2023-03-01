/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.collect.Maps;
import io.entframework.kernel.cache.api.exception.CacheException;
import io.entframework.kernel.cache.api.exception.CacheExceptionEnum;
import io.entframework.kernel.cache.redis.config.KernelRedisCacheProperties;
import io.entframework.kernel.cache.redis.config.RedisCacheNode;
import io.entframework.kernel.cache.redis.config.RedisCacheProperties;
import io.entframework.kernel.cache.redis.dao.impl.*;
import io.entframework.kernel.cache.redis.impl.RedisCacheManager;
import io.entframework.kernel.cache.redis.operator.DefaultRedisCacheOperator;
import io.entframework.kernel.cache.redis.operator.DefaultRedisHashCacheOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * redis utils
 */
@Slf4j
public class RedisCacheManagerFactory implements InitializingBean, DisposableBean {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final KernelRedisCacheProperties cacheProperties;

    private static final Map<String, RedissonClient> cachedMap = MapUtil.newConcurrentHashMap();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisCacheManagerFactory(KernelRedisCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;

        // 初始化ObjectMapper

        objectMapper.registerModule(new Jdk8Module());
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
    }

    /**
     * 根据CacheProperties配置创建RedisCacheManager
     */
    public <V> RedisCacheManager<V> createCache(@NonNull String cacheName, String defaultPrefix) {

        if (StringUtils.isEmpty(this.cacheProperties.getHost())) {
            throw new CacheException(CacheExceptionEnum.REDIS_CACHE_NOT_SET);
        }
        RedisCacheNode cacheNode = new RedisCacheNode();
        BeanUtils.copyProperties(this.cacheProperties, cacheNode);
        if (!CollectionUtils.isEmpty(this.cacheProperties.getNodes())) {
            Optional<RedisCacheNode> redisCacheNode = this.cacheProperties.getNodes().stream()
                    .filter(node -> cacheName.equals(node.getAlias())).findFirst();
            if (redisCacheNode.isPresent()) {
                RedisCacheNode cacheNodeProperties = redisCacheNode.get();
                mergeProperties(cacheNodeProperties, cacheNode);
            }
        }
        if (StringUtils.isEmpty(cacheNode.getPrefix())) {
            cacheNode.setPrefix(defaultPrefix);
        }

        RedissonClient redissonClient;
        String cacheKey = generateCacheKey(cacheNode);
        if (cachedMap.containsKey(cacheKey)) {
            redissonClient = cachedMap.get(cacheKey);
        }
        else {
            redissonClient = createRedissonClient(cacheNode);
            cachedMap.put(cacheKey, redissonClient);
        }

        RedisCacheManager<V> cacheManager = new RedisCacheManager<>(cacheNode.getPrefix());
        cacheManager.setRedisAtomicLongDao(new RedissonAtomicLongDao(redissonClient))
                .setRedisSimpleDao(new RedissonSimpleDao(redissonClient))
                .setRedisMapDao(new RedissonMapDao(redissonClient)).setRedisListDao(new RedissonListDao(redissonClient))
                .setRedisSetDao(new RedissonSetDao(redissonClient))
                .setRedisOrderSetDao(new RedissonOrderSetDao(redissonClient))
                .setRedisZScoreSetDao(new RedissonZScoreSetDao(redissonClient))
                .setRedisLockDao(new RedissonLockDao(redissonClient));
        return cacheManager;
    }

    public Map<String, BeanDefinition> createBeanDefinition() {
        Map<String, BeanDefinition> definitionMap = Maps.newLinkedHashMap();
        List<RedisCacheNode> cacheNodes = this.cacheProperties.getNodes();
        if (!CollectionUtils.isEmpty(cacheNodes)) {
            for (RedisCacheNode node : cacheNodes) {
                RedisCacheNode cacheNode = new RedisCacheNode();
                BeanUtils.copyProperties(this.cacheProperties, cacheNode);
                mergeProperties(node, cacheNode);

                RedissonClient redissonClient;
                String cacheKey = generateCacheKey(cacheNode);
                if (cachedMap.containsKey(cacheKey)) {
                    redissonClient = cachedMap.get(cacheKey);
                }
                else {
                    redissonClient = createRedissonClient(cacheNode);
                    cachedMap.put(cacheKey, redissonClient);
                }

                RedisCacheManager<?> cacheManager = new RedisCacheManager<>(cacheNode.getPrefix());
                cacheManager.setRedisAtomicLongDao(new RedissonAtomicLongDao(redissonClient))
                        .setRedisSimpleDao(new RedissonSimpleDao(redissonClient))
                        .setRedisMapDao(new RedissonMapDao(redissonClient))
                        .setRedisListDao(new RedissonListDao(redissonClient))
                        .setRedisSetDao(new RedissonSetDao(redissonClient))
                        .setRedisOrderSetDao(new RedissonOrderSetDao(redissonClient))
                        .setRedisZScoreSetDao(new RedissonZScoreSetDao(redissonClient))
                        .setRedisLockDao(new RedissonLockDao(redissonClient));
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                if (cacheNode.getIsHash() != null && cacheNode.getIsHash()) {
                    beanDefinition.setBeanClass(DefaultRedisHashCacheOperator.class);
                }
                else {
                    beanDefinition.setBeanClass(DefaultRedisCacheOperator.class);
                }
                ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
                constructorArgumentValues.addIndexedArgumentValue(0, cacheManager);
                beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
                definitionMap.put(cacheNode.getAlias(), beanDefinition);
            }
        }
        return definitionMap;
    }

    private String generateCacheKey(RedisCacheProperties p) {
        String sb = p.getHost() + p.getPort() + p.getAuth() + p.getDatabase();
        return DigestUtil.md5Hex(sb);
    }

    private void mergeProperties(RedisCacheNode source, RedisCacheNode target) {
        if (source == null) {
            return;
        }
        if (StringUtils.isNotEmpty(source.getAlias())) {
            target.setAlias(source.getAlias());
        }
        if (StringUtils.isNotEmpty(source.getPrefix())) {
            target.setPrefix(source.getPrefix());
        }
        if (StringUtils.isNotEmpty(source.getAuth())) {
            target.setAuth(source.getAuth());
        }
        if (StringUtils.isNotEmpty(source.getHost())) {
            target.setHost(source.getHost());
        }
        if (source.getPort() != null) {
            target.setPort(source.getPort());
        }

        if (StringUtils.isNotEmpty(source.getAuth())) {
            target.setAuth(source.getAuth());
        }

        if (source.getDatabase() != null) {
            target.setDatabase(source.getDatabase());
        }

        if (source.getSsl() != null) {
            target.setSsl(source.getSsl());
        }

        if (source.getConnectionPoolSize() != null) {
            target.setConnectionPoolSize(source.getConnectionPoolSize());
        }

        if (source.getConnectionMinimumIdleSize() != null) {
            target.setConnectionMinimumIdleSize(source.getConnectionMinimumIdleSize());
        }

        if (source.getIdleConnectionTimeout() != null) {
            target.setIdleConnectionTimeout(source.getIdleConnectionTimeout());
        }

        if (source.getIsHash() != null) {
            target.setIsHash(source.getIsHash());
        }
    }

    /**
     * 根据CacheProperties配置创建RedissonClient
     * @param properties 属性
     * @return Redisson Client
     */
    public RedissonClient createRedissonClient(RedisCacheProperties properties) {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec(this.objectMapper));
        config.useSingleServer().setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setDatabase(properties.getDatabase() != null ? properties.getDatabase() : 0)
                .setPassword(StringUtils.isNotBlank(properties.getAuth()) ? properties.getAuth() : null)
                .setConnectionPoolSize(
                        properties.getConnectionPoolSize() != null ? properties.getConnectionPoolSize() : 5)
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize() != null
                        ? properties.getConnectionMinimumIdleSize() : 2)
                .setIdleConnectionTimeout(10000);
        return Redisson.create(config);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Iterator<RedisCacheNode> iterator = this.cacheProperties.getNodes().iterator();
        List<String> cacheAliases = new ArrayList<>();

        while (iterator.hasNext()) {
            RedisCacheNode p = iterator.next();
            if (StringUtils.isBlank(p.getAlias())) {
                throw new CacheException(CacheExceptionEnum.REDIS_CACHE_ALIAS_MISSING);
            }

            if (cacheAliases.contains(p.getAlias())) {
                throw new CacheException(CacheExceptionEnum.REDIS_CACHE_ALIAS_DUPLICATED, p.getAlias());
            }
            cacheAliases.add(p.getAlias());
        }
    }

    @Override
    public void destroy() throws Exception {
        cachedMap.values().forEach(client -> {
            try {
                log.info("Destroy redisson connection");
                client.shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

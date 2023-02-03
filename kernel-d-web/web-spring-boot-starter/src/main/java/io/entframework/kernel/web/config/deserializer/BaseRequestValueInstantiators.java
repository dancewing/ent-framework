/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.web.config.deserializer;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleValueInstantiators;
import org.springframework.context.ApplicationContext;

/**
 * {@link BaseRequest}的 value 生成器
 * 默认jackson在获取前台request传回来的数据时，使用{@link StdValueInstantiator}
 * 做参数的实例化，但是BaseRequest 为了保留扩展性
 * 在自定义Request后，并将其注册成Bean使前台Controller能顺利完成
 * <p>
 * <pre>
 * @Bean
 * @Scope("prototype")
 * public StudentRequest studentRequest() {
 * return new StudentRequest();
 * }
 * </pre>
 * </p>
 */
public class BaseRequestValueInstantiators extends SimpleValueInstantiators {


    private final ApplicationContext context;

    public BaseRequestValueInstantiators(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator) {
        if (BaseRequest.class.isAssignableFrom(beanDesc.getBeanClass())) {
            return new BaseRequestInstantiator(this.context, defaultInstantiator);
        }
        return defaultInstantiator;
    }
}

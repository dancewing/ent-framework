/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.web.config;

import io.entframework.kernel.web.config.deserializer.BaseRequestValueInstantiators;
import io.entframework.kernel.web.config.error.CustomErrorAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableConfigurationProperties(KernelWebProperties.class)
@Import({cn.hutool.extra.spring.SpringUtil.class})
public class KernelSpringMvcAutoConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    private final KernelWebProperties properties;
    private ApplicationContext context;

    public KernelSpringMvcAutoConfiguration(KernelWebProperties properties) {
        this.properties = properties;
    }

    /**
     * 重写系统的默认错误提示
     *
     * @author fengshuonan
     * @date 2020/12/16 15:36
     */
    @Bean
    public CustomErrorAttributes gunsErrorAttributes() {
        return new CustomErrorAttributes();
    }

    /**
     * json自定义序列化工具,long转string
     *
     * @author fengshuonan
     * @date 2020/12/13 17:16
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder
                    .serializerByType(Long.class, ToStringSerializer.instance)
                    .serializerByType(Long.TYPE, ToStringSerializer.instance)
                    .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(this.properties.getDateTimeFormat())))
                    .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(this.properties.getDateTimeFormat())))
                    .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(this.properties.getDateFormat())))
                    .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(this.properties.getDateFormat())))
                    .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(this.properties.getTimeFormat())))
                    .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(this.properties.getTimeFormat())));
        };
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonMessageConverter) {

                ObjectMapper objectMapper = jacksonMessageConverter.getObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                BaseRequestValueInstantiators ivi = new BaseRequestValueInstantiators(this.context);
                simpleModule.setValueInstantiators(ivi);
                objectMapper.registerModule(simpleModule);
                break;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer baseRequestJackson2ObjectMapperBuilderCustomizer() {
//        return jacksonObjectMapperBuilder ->
//                jacksonObjectMapperBuilder.deserializerByType(BaseRequest.class, new BaseRequestDeserializer());
//    }
}

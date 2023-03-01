/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 资源标识，此注解代替Spring Mvc的@RequestMapping注解
 * <p>
 * 目的是为了在使用Spring Mvc的基础之上，增加对接口权限的控制功能
 *
 * @author jeff_qian
 * @date 2018-01-03-下午2:56
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface ApiResource {

	/**
	 * <p>
	 * 此属性用在一个程序承载两个业务系统的情况下,标识这个资源所属的模块,用在控制器上
	 * </p>
	 */
	String appCode() default "";

	/**
	 * 资源名称(必填项)
	 */
	String name();

	/**
	 * 需要登录(true-需要登录,false-不需要登录)
	 */
	boolean requiredLogin() default true;

	/**
	 * 需要鉴权(true-需要鉴权,false-不需要鉴权)
	 */
	boolean requiredPermission() default true;

	/**
	 * 是否是视图类型：true-是，false-否 如果是视图类型，url需要以 '/view' 开头， 视图类型的接口会渲染出html界面，而不是json数据，
	 * 视图层一般会在前后端不分离项目出现
	 */
	boolean viewFlag() default false;

	/**
	 * 请求路径(同RequestMapping)
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	/**
	 * 请求的http方法(同RequestMapping)
	 */
	@AliasFor(annotation = RequestMapping.class)
	RequestMethod[] method() default {};

	/**
	 * 同RequestMapping
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.util;

import cn.hutool.core.collection.CollUtil;
import io.entframework.kernel.scanner.api.enums.ParamTypeEnum;
import io.entframework.kernel.scanner.api.pojo.resource.ParameterMetadata;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 反射工具类，获取方法的一些元数据
 *
 * @date 2020/12/8 17:48
 */
@Slf4j
public class MethodReflectUtil {

	/**
	 * 获取方法上的注解
	 * <p>
	 * 注意，此方法只获取方法第一个参数的所有注解
	 * @param method 方法反射信息
	 * @return 方法参数上的注解集合
	 * @date 2020/12/8 17:49
	 */
	public static List<Annotation> getMethodFirstParamAnnotations(Method method) {
		if (method == null) {
			return Collections.emptyList();
		}

		if (method.getParameterCount() <= 0) {
			return Collections.emptyList();
		}

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (parameterAnnotations.length == 0) {
			return Collections.emptyList();
		}

		// 只获取第一个参数的所有注解，所以下标为0
		Annotation[] resultAnnotations = parameterAnnotations[0];
		if (resultAnnotations == null || resultAnnotations.length == 0) {
			return Collections.emptyList();
		}
		else {
			return CollUtil.toList(resultAnnotations);
		}
	}

	/**
	 * 获取方法上校验分组
	 * <p>
	 * 例如：获取如下方法的校验分组信息SysAppRequest.edit.class <pre>
	 * public ResponseData edit(@RequestBody @Validated(SysAppRequest.edit.class) SysAppRequest sysAppParam) {
	 *     ...
	 * }
	 * </pre>
	 * @param method 方法反射信息
	 * @return 方法的参数校验分组信息
	 * @date 2020/12/8 17:59
	 */
	public static Set<String> getMethodValidateGroup(Method method) {
		List<Annotation> methodFirstParamAnnotations = getMethodFirstParamAnnotations(method);
		if (methodFirstParamAnnotations == null) {
			return Collections.emptySet();
		}

		// 判断annotation有没有是@Validated注解类型的
		try {
			for (Annotation annotation : methodFirstParamAnnotations) {
				if (Validated.class.equals(annotation.annotationType())) {
					Method validateGroupMethod = annotation.annotationType().getMethod("value");
					Object invoke = validateGroupMethod.invoke(annotation);
					if (invoke != null) {
						Class<?>[] result = (Class<?>[]) invoke;
						HashSet<String> groupClassNames = new HashSet<>();
						if (result.length > 0) {
							for (Class<?> groupClass : result) {
								groupClassNames.add(groupClass.getSimpleName());
							}
						}
						else {
							groupClassNames.add(ScannerConstants.DEFAULT_VALIDATED);
						}
						return groupClassNames;
					}
				}
			}
		}
		catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			log.error("获取方法上的校验分组出错！", e);
		}
		return Collections.emptySet();
	}

	/**
	 * 返回方法的所有类型参数信息
	 * @param method 方法反射信息
	 * @return 方法第一个参数的class类型
	 * @date 2020/12/8 18:16
	 */
	public static Type[] getMethodGenericTypes(Method method) {
		if (method == null) {
			return new Type[0];
		}
		return method.getGenericParameterTypes();
	}

	/**
	 * 获取方法的返回值type类型，type可能是class也可能是带泛型的类型
	 * @param method 方法反射信息
	 * @return 方法返回值的class类型
	 * @date 2020/12/8 18:20
	 */
	public static Type getMethodReturnType(Method method) {
		if (method == null) {
			return null;
		}
		return method.getGenericReturnType();
	}

	/**
	 * 获取方法的所有参数元数据信息
	 *
	 * @date 2022/1/20 11:51
	 */
	public static List<ParameterMetadata> getMethodParameterInfos(Method method) {
		List<ParameterMetadata> result = new LinkedList<>();

		if (method == null) {
			return result;
		}

		Parameter[] parameters = method.getParameters();
		if (parameters.length == 0) {
			return result;
		}

		for (Parameter parameter : parameters) {
			ParameterMetadata parameterMetadata = new ParameterMetadata();

			// 设置type类型
			Type parameterizedType = parameter.getParameterizedType();
			parameterMetadata.setParameterizedType(parameterizedType);

			// 设置注解
			Annotation[] annotations = parameter.getAnnotations();
			parameterMetadata.setAnnotations(annotations);

			// 设置参数是param参数还是request body参数
			parameterMetadata.setParamTypeEnum(getParamTypeEnum(annotations));

			// 设置参数名
			parameterMetadata.setParameterName(parameter.getName());

			result.add(parameterMetadata);
		}

		return result;
	}

	/**
	 * 根据参数上的注解判断出是param参数还是request body参数
	 *
	 * @date 2022/1/20 13:43
	 */
	public static ParamTypeEnum getParamTypeEnum(Annotation[] annotations) {

		// 注解为空，直接判断为param参数
		if (annotations == null || annotations.length == 0) {
			return ParamTypeEnum.QUERY_PARAM;
		}

		// 如果注解中包含@RequestBody注解，则是json请求
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(RequestBody.class)) {
				return ParamTypeEnum.REQUEST_BODY;
			}
		}

		// 其他情况，判定为时param参数
		return ParamTypeEnum.QUERY_PARAM;
	}

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.database.annotation;

import java.lang.annotation.*;

/**
 * 被保护数据标识注解(标识那个DTO需要加解密)
 * <p>
 * 该注解作用范围在类上面
 *
 * @date 2021/7/3 10:54
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedData {

}

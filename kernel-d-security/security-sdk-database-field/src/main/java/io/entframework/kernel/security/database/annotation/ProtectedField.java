/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.database.annotation;

import java.lang.annotation.*;

/**
 * 需要加解密字段注解（该字段自动加解密，数据库是密文，查看时是明文）
 * <p>
 * 该注解作用范围在字段上面(该类需要加 {@link ProtectedData} 注解)
 *
 * @date 2021/7/5 9:18
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedField {

}

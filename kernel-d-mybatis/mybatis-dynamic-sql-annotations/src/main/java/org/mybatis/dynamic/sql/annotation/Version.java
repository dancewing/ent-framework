package org.mybatis.dynamic.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * The following types are supported for version properties: <code>Integer</code>,
 * <code>Long</code>, <code>Date</code>, <code>Timestamp</code>,
 * <code>LocalDateTime</code>,
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Version {

}

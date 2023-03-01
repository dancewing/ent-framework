package org.mybatis.dynamic.sql.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
// @Repeatable(GeneratedValue.List.class)
public @interface GeneratedValue {

	// @Documented
	// @Retention(RetentionPolicy.RUNTIME)
	// @Target(ElementType.FIELD)
	// @interface List {
	// GeneratedValue[] value();
	// }

}

package io.entframework.kernel.db.mybatis.util;


import org.mybatis.dynamic.sql.exception.DynamicSqlException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public final class VersionFieldUtils {

    public static Object getInitVersionVal(Class<?> clazz) {
        if (long.class.equals(clazz) || Long.class.equals(clazz)) {
            return ((long) 1);
        } else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
            return 1;
        } else if (Date.class.equals(clazz)) {
            return new Date();
        } else if (Timestamp.class.equals(clazz)) {
            return new Timestamp(System.currentTimeMillis());
        } else if (LocalDateTime.class.equals(clazz)) {
            return LocalDateTime.now();
        }
        throw new DynamicSqlException("Can't get get init Version value");
    }

    public static Object increaseVersionVal(Class<?> clazz, Object value) {
        if (long.class.equals(clazz) || Long.class.equals(clazz)) {
            return ((long) value) + 1;
        } else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
            return ((int) value) + 1;
        } else if (Date.class.equals(clazz)) {
            return new Date();
        } else if (Timestamp.class.equals(clazz)) {
            return new Timestamp(System.currentTimeMillis());
        } else if (LocalDateTime.class.equals(clazz)) {
            return LocalDateTime.now();
        }
        throw new DynamicSqlException("Can't get increase Version value");
    }

}

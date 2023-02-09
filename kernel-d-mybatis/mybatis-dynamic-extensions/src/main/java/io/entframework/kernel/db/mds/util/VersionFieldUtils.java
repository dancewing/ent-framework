package io.entframework.kernel.db.mds.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
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
        log.error("unsupported version type : {}", clazz.getName());
        //not supported type, return original val.
        return null;
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
        log.error("unsupported version type : {}", clazz.getName());
        //not supported type, return original val.
        return null;
    }

}

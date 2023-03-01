package org.mybatis.dynamic.sql.util;

public abstract class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, String message) {
        if (!StringUtilities.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

}
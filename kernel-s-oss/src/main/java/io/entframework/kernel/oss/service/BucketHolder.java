package io.entframework.kernel.oss.service;

public class BucketHolder {

    private static final ThreadLocal<String> BUCKET_HOLDER = new ThreadLocal<>();

    public static void set(String bucket) {
        BUCKET_HOLDER.set(bucket);
    }

    public static String get() {
        return BUCKET_HOLDER.get();
    }

    public static void remove() {
        BUCKET_HOLDER.remove();
    }

}

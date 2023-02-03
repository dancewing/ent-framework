package io.entframework.kernel.db.generator.plugin;

public enum JsonDateFormat {
    DATE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    TIME("HH:mm");
    private String format;

    JsonDateFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}

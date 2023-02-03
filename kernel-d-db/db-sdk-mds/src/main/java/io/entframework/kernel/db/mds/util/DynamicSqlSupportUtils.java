package io.entframework.kernel.db.mds.util;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoSupportExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public final class DynamicSqlSupportUtils {

    public static SqlColumn<?> findColumn(SqlTable table, String fieldName) {
        Optional<SqlColumn<?>> columnOptional = safeFindColumn(table, fieldName);
        if (columnOptional.isPresent()) {
            return columnOptional.get();
        }
        throw new DaoException(DaoSupportExceptionEnum.FIELD_NOT_FOUND, table.tableAlias().orElse(""), fieldName);
    }

    public static Optional<SqlColumn<?>> safeFindColumn(SqlTable table, String fieldName) {
        try {
            Field[] fields = table.getClass().getDeclaredFields();
            Optional<Field> foundField = Arrays.stream(fields).filter(field -> StringUtils.equalsIgnoreCase(fieldName, field.getName())).findFirst();
            if (foundField.isPresent()) {
                Object value = FieldUtils.readField(foundField.get(), table, true);
                return Optional.of((SqlColumn<?>) value);
            }
        } catch (Exception ex) {
            log.error("Can't find field {} from {}", fieldName, table.tableNameAtRuntime());
        }
        return Optional.empty();
    }

    public static <T> Optional<SqlColumn<T>> safeFindColumn(SqlTable table, String fieldName, Class<T> fieldType) {
        try {
            Field[] fields = table.getClass().getDeclaredFields();
            Optional<Field> foundField = Arrays.stream(fields).filter(field -> StringUtils.equalsIgnoreCase(fieldName, field.getName())).findFirst();
            if (foundField.isPresent()) {
                Object value = FieldUtils.readField(foundField.get(), table, true);
                return Optional.of((SqlColumn<T>) value);
            }
        } catch (Exception ex) {
            log.error("Can't find field {} from {}", fieldName, table.tableNameAtRuntime());
        }
        return Optional.empty();
    }
}

package org.mybatis.dynamic.sql.update.render;

import org.jetbrains.annotations.NotNull;
import org.mybatis.dynamic.sql.StatementProvider;

import java.util.Map;

public interface UpdateRowStatementProvider<T> extends StatementProvider {
    /**
     * Return the row associated with this update statement.
     */
    @NotNull
    T getRow();

    /**
     * Return the formatted update statement.
     */
    String getUpdateStatement();

    Map<String, Object> getParameters();
}

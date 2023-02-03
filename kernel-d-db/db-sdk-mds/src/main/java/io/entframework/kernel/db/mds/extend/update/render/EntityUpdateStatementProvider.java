package io.entframework.kernel.db.mds.extend.update.render;

import org.jetbrains.annotations.NotNull;

public interface EntityUpdateStatementProvider<T> {
    /**
     * Return the row associated with this update statement.
     */
    @NotNull
    T getRow();

    /**
     * Return the formatted update statement.
     */
    String getUpdateStatement();
}

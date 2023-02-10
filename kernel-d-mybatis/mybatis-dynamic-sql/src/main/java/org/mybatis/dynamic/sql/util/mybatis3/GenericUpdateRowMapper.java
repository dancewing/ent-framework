package org.mybatis.dynamic.sql.util.mybatis3;

import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.update.render.UpdateRowStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public interface GenericUpdateRowMapper<T> {
    @UpdateProvider(type = SqlProviderAdapter.class, method = "updateRow")
    int updateRow(UpdateRowStatementProvider<T> updateStatement);

    @UpdateProvider(type = SqlProviderAdapter.class, method = "updateRowSelect")
    int updateRowSelect(UpdateRowStatementProvider<T> updateStatement);
}

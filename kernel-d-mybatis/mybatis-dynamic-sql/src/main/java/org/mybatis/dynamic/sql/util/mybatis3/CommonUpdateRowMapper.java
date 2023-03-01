package org.mybatis.dynamic.sql.util.mybatis3;

import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.update.render.UpdateRowStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public interface CommonUpdateRowMapper {

    @UpdateProvider(type = SqlProviderAdapter.class, method = "updateRow")
    <T> int updateRow(UpdateRowStatementProvider<T> updateStatement);

    @UpdateProvider(type = SqlProviderAdapter.class, method = "updateRowSelect")
    <T> int updateRowSelect(UpdateRowStatementProvider<T> updateStatement);

}

package io.entframework.kernel.db.mds.extend.update;

import io.entframework.kernel.db.mds.extend.SqlUpdateProviderAdapter;
import io.entframework.kernel.db.mds.extend.update.render.EntityUpdateStatementProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface EntityUpdateMapper<T> {
    @UpdateProvider(type = SqlUpdateProviderAdapter.class, method = "update")
    int updateRow(EntityUpdateStatementProvider<T> updateStatement);
}

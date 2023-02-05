package io.entframework.kernel.db.mds.extend;

import io.entframework.kernel.db.mds.extend.update.render.EntityUpdateStatementProvider;

public class SqlUpdateProviderAdapter {

    public String update(EntityUpdateStatementProvider<?> updateStatement) {
        return updateStatement.getUpdateStatement();
    }
}

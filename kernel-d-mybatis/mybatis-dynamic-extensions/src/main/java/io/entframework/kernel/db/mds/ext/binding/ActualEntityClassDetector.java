package io.entframework.kernel.db.mds.ext.binding;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.select.QueryExpressionModel;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectRenderer;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.List;

@Slf4j
public class ActualEntityClassDetector {
    public static Class<?> determine(Object[] args) {
        if (args.length == 1) {
            if (args[0] instanceof SelectStatementProvider selectStatementProvider) {
                SelectRenderer selectRenderer = selectStatementProvider.getSource();
                SelectModel selectModel = selectRenderer.getSelectModel();
                List<QueryExpressionModel> queryExpressions = selectModel.queryExpressions();
                if (!queryExpressions.isEmpty()) {
                    QueryExpressionModel queryExpressionModel = queryExpressions.get(0);
                    return queryExpressionModel.getEntityClass();
                }

            }
        }
        return null;
    }
}

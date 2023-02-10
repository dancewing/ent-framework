package io.entframework.kernel.db.mds.ext.binding;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.StatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.QueryExpressionModel;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectRenderer;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.List;

@Slf4j
public class ActualEntityClassDetector {
    public static Class<?> determine(StatementProvider statementProvider) {
        if (statementProvider instanceof SelectStatementProvider selectStatementProvider) {
            SelectRenderer selectRenderer = selectStatementProvider.getSource();
            SelectModel selectModel = selectRenderer.getSelectModel();
            List<QueryExpressionModel> queryExpressions = selectModel.queryExpressions();
            if (!queryExpressions.isEmpty()) {
                QueryExpressionModel queryExpressionModel = queryExpressions.get(0);
                return queryExpressionModel.getEntityClass();
            }
        }
        if (statementProvider instanceof InsertStatementProvider<?> insertStatementProvider) {
            log.info(insertStatementProvider.getInsertStatement());
            return insertStatementProvider.getRow().getClass();
        }
        return null;
    }

    public static StatementProvider determineStatementProvider(Object[] args) {
        if (args != null && args.length == 1) {
            if (args[0] instanceof StatementProvider statementProvider) {
                return statementProvider;
            }
        }
        return null;
    }
}

package io.entframework.kernel.db.mds.test.animal;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.QueryExpressionModel;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectRenderer;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereDSL;

import java.util.List;

@Intercepts({
		@Signature(type = Executor.class, method = "query",
				args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }), })
public class BrainWeightInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		Object[] args = invocation.getArgs();
		if (args.length > 1) {
			if (args[1] instanceof SelectStatementProvider selectStatementProvider) {
				SelectRenderer selectRenderer = selectStatementProvider.getSource();
				SelectModel selectModel = selectRenderer.getSelectModel();
				List<QueryExpressionModel> queryExpressions = selectModel.queryExpressions();
				if (queryExpressions.size() > 0) {
					QueryExpressionModel queryExpressionModel = queryExpressions.get(0);
					Class<?> entityClass = queryExpressionModel.getEntityClass();
					if (AnimalData.class.isAssignableFrom(entityClass)) {
						if (queryExpressionModel.whereModel().isEmpty()) {
							QueryExpressionModel.Builder build = QueryExpressionModel.newBuilder(queryExpressionModel);
							WhereDSL whereDSL = WhereDSL.where();
							whereDSL.and(AnimalDataDynamicSqlSupport.brainWeight, SqlBuilder.isGreaterThan(2D));
							build.withWhereModel(whereDSL.build());
							// SqlBuilder.select(queryExpressionModel.mapColumns())
							queryExpressions.clear();
							queryExpressions.add(build.build());
						}
					}
				}
				// 改变了条件，重新生成语句
				args[1] = selectRenderer.render();
			}
		}
		return invocation.proceed();
	}

}

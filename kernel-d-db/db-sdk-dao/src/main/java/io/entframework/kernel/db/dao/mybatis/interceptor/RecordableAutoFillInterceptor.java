/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.dao.mybatis.interceptor;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.db.mybatis.util.VersionFieldUtils;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Version;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 拦截器，配合BaseEntity一起使用
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Slf4j
public class RecordableAutoFillInterceptor implements Interceptor {

	private static final Cache<String, Field> CACHE = CacheBuilder.newBuilder()
			// 设置并发级别为cpu核心数，默认为4
			.concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object object = invocation.getArgs()[1];
		// sql类型
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object entity = object;
		if (object instanceof InsertStatementProvider<?> isp) {
			// Dynamic SQL
			entity = isp.getRow();
		}
		if (entity != null) {
			setValue(entity, !SqlCommandType.INSERT.equals(sqlCommandType));
		}
		if (object instanceof MultiRowInsertStatementProvider<?> statementProvider) {
			List<?> entities = statementProvider.getRecords();
			if (entities != null) {
				for (Object obj : entities) {
					setValue(obj, false);
				}
			}
		}

		if (object instanceof UpdateStatementProvider usp) {
			BoundSql boundSql = mappedStatement.getBoundSql(object);
			String mapperName = StringUtils.substringBeforeLast(mappedStatement.getId(), ".");
			if (log.isDebugEnabled()) {
				log.debug("try to update using :" + mapperName);
			}
			// EntityMeta entityMeta =
			// Entities.fromMapper(ClassUtils.toClassConfident(mapperName));
			// setMapValue(entityMeta, usp.getParameters(), boundSql.getSql());
		}
		return invocation.proceed();
	}

	private void setMapValue(EntityMeta entityInfo, Map<String, Object> parameters, String sql) {
		try {
			Statement stmt = CCJSqlParserUtil.parse(sql);
			Update update = (Update) stmt;
			ArrayList<UpdateSet> updateSets = update.getUpdateSets();

			if (BaseEntity.class.isAssignableFrom(entityInfo.getEntityClass())) {

				int index = getIndex(updateSets, "create_user");
				if (index > 0) {

				}
			}

			if (entityInfo.hasVersion()) {
				Optional<FieldAndColumn> versionColumn = entityInfo.findVersionColumn();
				if (versionColumn.isPresent()) {
					String versionColumnName = versionColumn.get().column().name();
					int index = getIndex(updateSets, versionColumnName);
					if (index > 0) {
						String key = "p" + index;
						if (parameters.containsKey(key)) {
							Object version = parameters.get(key);
							if (version != null) {
								parameters.put(key, VersionFieldUtils.increaseVersionVal(version.getClass(), version));
							}
						}
						else {

						}
					}

				}
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage());
		}

	}

	private int getIndex(ArrayList<UpdateSet> updateSets, String columnName) {
		int index = 1;
		for (UpdateSet updateSet : updateSets) {
			boolean matched = updateSet.getColumns().stream()
					.anyMatch(column -> StringUtils.equals(columnName, column.getColumnName()));
			if (matched) {
				return index;
			}
			index++;
		}
		return -1;
	}

	private void setValue(Object object, boolean isUpdate) {
		if (object instanceof BaseEntity entity) {
			LoginUser loginUser = getLoginUser();
			if (isUpdate) {
				entity.setUpdateTime(LocalDateTime.now());
				entity.setUpdateUser(loginUser != null ? loginUser.getUserId() : -1);
				entity.setUpdateUserName(loginUser != null ? loginUser.getAccount() : "");
			}
			else {
				entity.setCreateUser(loginUser != null ? loginUser.getUserId() : -1);
				entity.setCreateUserName(loginUser != null ? loginUser.getAccount() : "");
				entity.setCreateTime(LocalDateTime.now());
				entity.setUpdateTime(LocalDateTime.now());
			}
		}
		if (!isUpdate) {
			// get @id from Entity
			Field id = getField(object.getClass(), Id.class);
			if (id != null) {
				Object idValue = ReflectUtil.getFieldValue(object, id);
				if (idValue == null) {
					if (id.getType().isAssignableFrom(String.class)) {
						ReflectUtil.setFieldValue(object, id, IdWorker.getIdStr());
					}
					else if (id.getType().isAssignableFrom(Long.class)) {
						ReflectUtil.setFieldValue(object, id, IdWorker.getId());
					}
				}
			}
			Field logicDelete = getField(object.getClass(), LogicDelete.class);
			if (logicDelete != null) {
				ReflectUtil.setFieldValue(object, logicDelete, YesOrNotEnum.N);
			}
			Field version = getField(object.getClass(), Version.class);
			if (version != null) {
				ReflectUtil.setFieldValue(object, version, VersionFieldUtils.getInitVersionVal(version.getType()));
			}
		}
	}

	private LoginUser getLoginUser() {
		try {
			return LoginContext.me().getLoginUser();
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			// 如果获取不到就返回-1
			return null;
		}
	}

	private Field getField(Class<?> c, Class<? extends Annotation> annotatedTypeClass) {
		String key = makeKey(c, annotatedTypeClass);
		Field cachedField = CACHE.getIfPresent(key);
		if (cachedField != null) {
			return cachedField;
		}
		Field[] fields = ReflectUtil.getFields(c);
		Field result = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotatedTypeClass)) {
				result = field;
				break;
			}
		}
		if (result != null) {
			CACHE.put(key, result);
			return result;
		}
		return null;
	}

	private String makeKey(Class<?> c, Class<? extends Annotation> annotatedTypeClass) {
		return c.getName().concat("-").concat(annotatedTypeClass.getName());
	}

}

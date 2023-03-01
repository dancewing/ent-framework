package org.mybatis.dynamic.sql;

public abstract class EntityModel {

	protected Class<?> entityClass;

	protected EntityModel(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

}

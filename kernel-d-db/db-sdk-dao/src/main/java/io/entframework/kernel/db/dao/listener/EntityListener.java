package io.entframework.kernel.db.dao.listener;

public interface EntityListener {

	default void beforeInsert(Object object) {
	}

	default void beforeInsertMultiple(Iterable<?> objects) {
		if (objects != null) {
			for (Object object : objects) {
				this.beforeInsert(object);
			}
		}
	}

	default void beforeUpdate(Object object) {

	}

}

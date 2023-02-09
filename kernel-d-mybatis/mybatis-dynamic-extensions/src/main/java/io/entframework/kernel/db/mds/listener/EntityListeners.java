package io.entframework.kernel.db.mds.listener;

import java.util.ArrayList;
import java.util.List;

public class EntityListeners implements EntityListener {
    private final List<EntityListener> listeners = new ArrayList<>();

    public EntityListeners(List<EntityListener> listeners) {
        this.listeners.addAll(listeners);
    }

    @Override
    public void beforeInsert(Object object) {
        listeners.forEach(entityListener -> entityListener.beforeInsert(object));
    }

    @Override
    public void beforeUpdate(Object object) {
        listeners.forEach(entityListener -> entityListener.beforeUpdate(object));
    }

    @Override
    public void beforeInsertMultiple(Iterable<?> objects) {
        listeners.forEach(entityListener -> entityListener.beforeInsertMultiple(objects));
    }
}

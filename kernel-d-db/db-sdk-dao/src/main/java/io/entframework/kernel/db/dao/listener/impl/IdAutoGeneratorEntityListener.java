package io.entframework.kernel.db.dao.listener.impl;

import cn.hutool.core.util.ReflectUtil;
import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.db.dao.listener.EntityListener;
import org.mybatis.dynamic.sql.annotation.GeneratedValue;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

/**
 * Entity主键自动生成
 */
public class IdAutoGeneratorEntityListener implements EntityListener {

    @Override
    public void beforeInsert(Object object) {
        EntityMeta entityMeta = Entities.getInstance(object.getClass());
        FieldAndColumn primaryKey = entityMeta.getPrimaryKey();
        if (primaryKey.field().isAnnotationPresent(GeneratedValue.class)) {
            return;
        }
        Object idValue = ReflectUtil.getFieldValue(object, primaryKey.fieldName());
        if (idValue == null) {
            if (primaryKey.fieldType().isAssignableFrom(String.class)) {
                ReflectUtil.setFieldValue(object, primaryKey.fieldName(), IdWorker.getIdStr());
            }
            else if (primaryKey.fieldType().isAssignableFrom(Long.class)) {
                ReflectUtil.setFieldValue(object, primaryKey.fieldName(), IdWorker.getId());
            }
        }
    }

}

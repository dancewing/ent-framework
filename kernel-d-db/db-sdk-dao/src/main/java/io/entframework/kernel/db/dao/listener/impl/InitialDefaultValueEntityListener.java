package io.entframework.kernel.db.dao.listener.impl;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.dao.listener.EntityListener;
import io.entframework.kernel.db.mybatis.util.VersionFieldUtils;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.mybatis.dynamic.sql.annotation.Version;
import org.mybatis.dynamic.sql.util.ReflectUtils;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;

import java.util.Optional;

public class InitialDefaultValueEntityListener implements EntityListener {

    @Override
    public void beforeInsert(Object object) {
        EntityMeta entityMeta = Entities.getInstance(object.getClass());

        Optional<FieldAndColumn> logicDeleteOptional = entityMeta.findColumn(LogicDelete.class);
        logicDeleteOptional.ifPresent(
                fieldAndColumn -> ReflectUtils.setFieldValue(object, fieldAndColumn.fieldName(), YesOrNotEnum.N));
        Optional<FieldAndColumn> versionOptional = entityMeta.findColumn(Version.class);
        versionOptional.ifPresent(fieldAndColumn -> ReflectUtils.setFieldValue(object, fieldAndColumn.fieldName(),
                VersionFieldUtils.getInitVersionVal(fieldAndColumn.fieldType())));
    }

}

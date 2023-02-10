/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.generator.plugin.server;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.generator.GeneratorUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.JoinTarget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static Set<FullyQualifiedJavaType> getRelatedFieldType(IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> relatedFieldType = new HashSet<>();
        TopLevelClass modelClass = (TopLevelClass) introspectedTable.getAttribute(Constants.INTROSPECTED_TABLE_MODEL_CLASS);
        if (GeneratorUtils.hasRelation(modelClass, JoinTarget.JoinType.ONE)) {
            List<Field> fields = GeneratorUtils.getRelatedFields(modelClass, JoinTarget.JoinType.ONE);
            if (fields.size() > 0) {
                for (Field field : fields) {
                    relatedFieldType.add(field.getType());
                }
            }
        }

        if (GeneratorUtils.hasRelation(modelClass, JoinTarget.JoinType.MORE)) {
            List<Field> fields = GeneratorUtils.getRelatedFields(modelClass, JoinTarget.JoinType.MORE);
            if (fields.size() > 0) {
                for (Field field : fields) {
                    relatedFieldType.add(field.getType().getTypeArguments().get(0));
                }
            }
        }
        return relatedFieldType;
    }
}

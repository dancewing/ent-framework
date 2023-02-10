/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.generator.plugin.server.methods.repository;

import io.entframework.kernel.db.generator.plugin.server.methods.AbstractMethodGenerator;
import io.entframework.kernel.db.generator.plugin.server.methods.MethodAndImports;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.HashSet;
import java.util.Set;

public class RepConstructorGenerator extends AbstractMethodGenerator {

    public RepConstructorGenerator(BuildConfig builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();

        imports.add(recordType);


        Method defaultConstructorMethod = new Method(this.hostJavaClass.getType().getShortName()); //$NON-NLS-1$
        defaultConstructorMethod.setConstructor(true);
        defaultConstructorMethod.setAbstract(isAbstract);

        Method classParamConstructorMethod = new Method(this.hostJavaClass.getType().getShortName()); //$NON-NLS-1$
        classParamConstructorMethod.setConstructor(true);
        classParamConstructorMethod.setAbstract(isAbstract);

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(String.format("Class<? extends %s>", recordType.getShortName())); //$NON-NLS-1$
        imports.add(parameterType);
        classParamConstructorMethod.addParameter(new Parameter(parameterType, "entityClass")); //$NON-NLS-1$

        if (!isAbstract) {

            defaultConstructorMethod.setVisibility(JavaVisibility.PUBLIC);
            defaultConstructorMethod.addBodyLine(String.format("super(%s.class);", recordType.getShortName()));

            classParamConstructorMethod.setVisibility(JavaVisibility.PUBLIC);
            classParamConstructorMethod.addBodyLine("super(entityClass);");

        }
        MethodAndImports.Builder builder = MethodAndImports.withMethod(defaultConstructorMethod)
                .withMethod(classParamConstructorMethod)
                .withImports(imports).withStaticImports(staticImports);

        return builder.build();
    }
}

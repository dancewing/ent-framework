/*
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.entframework.kernel.db.generator.plugin.web.runtime.render;

import io.entframework.kernel.db.generator.plugin.web.runtime.FullyQualifiedTypescriptType;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.CustomCollectors;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RenderingUtilities {

    private RenderingUtilities() {
    }

    public static final String JAVA_INDENT = "  "; //$NON-NLS-1$

    private static final TypeParameterRenderer typeParameterRenderer = new TypeParameterRenderer();

    private static final FieldRenderer fieldRenderer = new FieldRenderer();

    private static final InitializationBlockRenderer initializationBlockRenderer = new InitializationBlockRenderer();

    private static final MethodRenderer methodRenderer = new MethodRenderer();

    private static final InnerClassRenderer innerClassRenderer = new InnerClassRenderer();

    private static final InnerInterfaceRenderer innerInterfaceRenderer = new InnerInterfaceRenderer();

    private static final InnerEnumRenderer innerEnumRenderer = new InnerEnumRenderer();

    // should return an empty string if no type parameters
    public static String renderTypeParameters(List<TypeParameter> typeParameters, CompilationUnit compilationUnit) {
        return typeParameters.stream().map(tp -> typeParameterRenderer.render(tp, compilationUnit))
                .collect(CustomCollectors.joining(", ", "<", "> ")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static List<String> renderFields(List<Field> fields, CompilationUnit compilationUnit) {
        return fields.stream().flatMap(f -> renderField(f, compilationUnit)).collect(Collectors.toList());
    }

    private static Stream<String> renderField(Field field, CompilationUnit compilationUnit) {
        return addEmptyLine(fieldRenderer.render(field, compilationUnit).stream().map(RenderingUtilities::javaIndent));
    }

    public static List<String> renderInitializationBlocks(List<InitializationBlock> initializationBlocks) {
        return initializationBlocks.stream().flatMap(RenderingUtilities::renderInitializationBlock)
                .collect(Collectors.toList());
    }

    private static Stream<String> renderInitializationBlock(InitializationBlock initializationBlock) {
        return addEmptyLine(initializationBlockRenderer.render(initializationBlock).stream());
    }

    public static List<String> renderClassOrEnumMethods(List<Method> methods, CompilationUnit compilationUnit) {
        return methods.stream().flatMap(m -> renderMethodNoIndent(m, false, compilationUnit))
                .collect(Collectors.toList());
    }

    public static List<String> renderInterfaceMethods(List<Method> methods, CompilationUnit compilationUnit) {
        return methods.stream().flatMap(m -> renderMethod(m, true, compilationUnit)).collect(Collectors.toList());
    }

    private static Stream<String> renderMethod(Method method, boolean inInterface, CompilationUnit compilationUnit) {
        return addEmptyLine(methodRenderer.render(method, inInterface, compilationUnit).stream()
                .map(RenderingUtilities::javaIndent));
    }

    private static Stream<String> renderMethodNoIndent(Method method, boolean inInterface,
            CompilationUnit compilationUnit) {
        return addEmptyLine(methodRenderer.render(method, inInterface, compilationUnit).stream());
    }

    private static Stream<String> addEmptyLine(Stream<String> in) {
        return Stream.of(in, Stream.of("")) //$NON-NLS-1$
                .flatMap(Function.identity());
    }

    public static List<String> renderInnerClasses(List<InnerClass> innerClasses, CompilationUnit compilationUnit) {
        return innerClasses.stream().flatMap(ic -> renderInnerClass(ic, compilationUnit)).collect(Collectors.toList());
    }

    public static List<String> renderInnerClassNoIndent(InnerClass innerClass, CompilationUnit compilationUnit) {
        return innerClassRenderer.render(innerClass, compilationUnit);
    }

    private static Stream<String> renderInnerClass(InnerClass innerClass, CompilationUnit compilationUnit) {
        return addEmptyLine(innerClassRenderer.render(innerClass, compilationUnit).stream());
    }

    public static List<String> renderInnerInterfaces(List<InnerInterface> innerInterfaces,
            CompilationUnit compilationUnit) {
        return innerInterfaces.stream().flatMap(ii -> renderInnerInterface(ii, compilationUnit))
                .collect(Collectors.toList());
    }

    public static List<String> renderInnerInterfaceNoIndent(InnerInterface innerInterface,
            CompilationUnit compilationUnit) {
        return innerInterfaceRenderer.render(innerInterface, compilationUnit);
    }

    private static Stream<String> renderInnerInterface(InnerInterface innerInterface, CompilationUnit compilationUnit) {
        return addEmptyLine(innerInterfaceRenderer.render(innerInterface, compilationUnit).stream()
                .map(RenderingUtilities::javaIndent));
    }

    public static List<String> renderInnerEnums(List<InnerEnum> innerEnums, CompilationUnit compilationUnit) {
        return innerEnums.stream().flatMap(ie -> renderInnerEnum(ie, compilationUnit)).collect(Collectors.toList());
    }

    public static List<String> renderInnerEnumNoIndent(InnerEnum innerEnum, CompilationUnit compilationUnit) {
        return innerEnumRenderer.render(innerEnum, compilationUnit);
    }

    private static Stream<String> renderInnerEnum(InnerEnum innerEnum, CompilationUnit compilationUnit) {
        return addEmptyLine(innerEnumRenderer.render(innerEnum, compilationUnit).stream());
    }

    public static List<String> renderImports(CompilationUnit compilationUnit) {
        Set<FullyQualifiedJavaType> importedTypes = compilationUnit.getImportedTypes();
        Set<String> imports = new TreeSet<>();

        importedTypes.stream().filter(javaType -> !StringUtils.startsWith(javaType.getPackageName(), "java"))
                .forEach(javaType -> {
                    if (javaType instanceof FullyQualifiedTypescriptType) {
                        FullyQualifiedTypescriptType fqtt = (FullyQualifiedTypescriptType) javaType;
                        imports.add(renderImport(fqtt.getProjectRootAlias(), fqtt));
                    }
                    else {
                        imports.add(renderImport("", javaType));
                    }
                });
        if (imports.isEmpty()) {
            return Collections.emptyList();
        }
        return addEmptyLine(imports.stream()).collect(Collectors.toList());
    }

    private static Set<String> renderImportsWithAlias(Set<FullyQualifiedJavaType> imports, String webProjectRootAlias) {
        return imports.stream().filter(javaType -> !StringUtils.startsWith(javaType.getPackageName(), "java"))
                .map(javaType -> {
                    String importName = javaType.getShortName();
                    String fileName = javaType.getPackageName();
                    String packageName = fileName.replaceAll("\\.", "/");
                    return "import { " + importName + " } from '" + webProjectRootAlias + packageName + "';";
                }).collect(Collectors.toCollection(TreeSet::new));
    }

    private static String renderImport(String rootAlias, FullyQualifiedJavaType javaType) {
        String importName = javaType.getShortName();
        String fileName = javaType.getPackageName();
        String packageName = fileName.replaceAll("\\.", "/");
        return "import { " + importName + " } from '" + rootAlias + packageName + "';";
    }

    private static Set<String> renderImports(Set<FullyQualifiedJavaType> imports) {
        return imports.stream().filter(javaType -> !StringUtils.startsWith(javaType.getPackageName(), "java"))
                .map(javaType -> {
                    String importName = javaType.getShortName();
                    String fileName = javaType.getPackageName();
                    String packageName = fileName.replaceAll("\\.", "/");
                    return "import { " + importName + " } from '" + packageName + "';";
                }).collect(Collectors.toCollection(TreeSet::new));
    }

    private static String javaIndent(String in) {
        if (in.isEmpty()) {
            return in; // don't indent empty lines
        }

        return JAVA_INDENT + in;
    }

    public static List<String> removeLastEmptyLine(List<String> lines) {
        if (lines.get(lines.size() - 1).isEmpty()) {
            return lines.subList(0, lines.size() - 1);
        }
        else {
            return lines;
        }
    }

    public static String calculateTypescriptTypeName(CompilationUnit compilationUnit, FullyQualifiedJavaType fqjt) {

        if (fqjt.isArray()) {
            // if array, then calculate the name of the base (non-array) type
            // then add the array indicators back in
            String fqn = fqjt.getFullyQualifiedName();
            String typeName = calculateTypescriptTypeName(compilationUnit,
                    new FullyQualifiedJavaType(fqn.substring(0, fqn.indexOf('['))));
            return typeName + fqn.substring(fqn.indexOf('['));
        }

        if (!fqjt.getTypeArguments().isEmpty()) {
            return calculateParameterizedTypeName(compilationUnit, fqjt);
        }

        if (compilationUnit == null || typeDoesNotRequireImport(fqjt) || typeIsInSamePackage(compilationUnit, fqjt)
                || typeIsAlreadyImported(compilationUnit, fqjt)) {
            return javaToTypescriptType(fqjt);
        }
        else {
            return fqjt.getFullyQualifiedName();
        }
    }

    public static String javaToTypescriptType(FullyQualifiedJavaType javaType) {
        String type = javaType.getFullyQualifiedNameWithoutTypeParameters();
        if (StringUtils.equalsAny(type, "java.lang.Long", "java.lang.Short", "java.lang.Integer", "Long", "Short",
                "Integer")) {
            return "number";
        }
        if (StringUtils.equalsAny(type, "java.lang.String", "String")) {
            return "string";
        }
        if (StringUtils.equalsAny(type, "java.lang.Boolean", "Boolean")) {
            return "boolean";
        }
        if (StringUtils.equalsAny(type, "java.lang.Object", "Object")) {
            return "any";
        }
        // 需要判断
        if (StringUtils.equalsAny(type, "java.time.LocalDate", "java.time.LocalDateTime", "LocalDate",
                "LocalDateTime")) {
            return "string";
        }
        if (StringUtils.equalsAny(type, "java.util.Map", "Map")) {
            if (javaType.getTypeArguments().size() > 0) {
                return "Map";
            }
            return "any";
        }
        return javaType.getShortName();
    }

    private static String calculateParameterizedTypeName(CompilationUnit compilationUnit, FullyQualifiedJavaType fqjt) {
        String baseTypeName = javaToTypescriptType(fqjt);
        // 如果是集合，转换成数组
        if (StringUtils.equalsAny(fqjt.getFullyQualifiedNameWithoutTypeParameters(), "java.util.List",
                "java.util.Collection", "java.util.Set")) {
            if (fqjt.getTypeArguments().size() == 1) {
                return javaToTypescriptType(fqjt.getTypeArguments().get(0)) + "[]";
            }
        }
        return fqjt.getTypeArguments().stream().map(t -> calculateTypescriptTypeName(compilationUnit, t))
                .collect(Collectors.joining(", ", baseTypeName + "<", ">")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private static boolean typeDoesNotRequireImport(FullyQualifiedJavaType fullyQualifiedJavaType) {
        return fullyQualifiedJavaType.isPrimitive() || !fullyQualifiedJavaType.isExplicitlyImported();
    }

    private static boolean typeIsInSamePackage(CompilationUnit compilationUnit,
            FullyQualifiedJavaType fullyQualifiedJavaType) {
        return fullyQualifiedJavaType.getPackageName().equals(compilationUnit.getType().getPackageName());
    }

    private static boolean typeIsAlreadyImported(CompilationUnit compilationUnit,
            FullyQualifiedJavaType fullyQualifiedJavaType) {
        String name = fullyQualifiedJavaType.getFullyQualifiedNameWithoutTypeParameters();
        return compilationUnit.getImportedTypes().stream().anyMatch(e -> e.getImportList().contains(name));
    }

}

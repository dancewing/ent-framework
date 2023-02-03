package io.entframework.kernel.db.generator.plugin.web.runtime;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class TypescriptTopLevelClass extends TopLevelClass {
    public TypescriptTopLevelClass(FullyQualifiedJavaType type) {
        super(type);
    }

    public TypescriptTopLevelClass(String typeName) {
        super(typeName);
    }

    @Override
    public void addImportedType(FullyQualifiedJavaType importedType) {
        if (importedType != null
                && importedType.isExplicitlyImported()
                && !importedType.getShortName().equals(getType().getShortName())) {
            importedTypes.add(importedType);
        }
    }
}

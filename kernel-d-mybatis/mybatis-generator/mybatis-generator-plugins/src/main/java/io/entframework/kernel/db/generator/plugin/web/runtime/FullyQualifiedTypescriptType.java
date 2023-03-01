package io.entframework.kernel.db.generator.plugin.web.runtime;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class FullyQualifiedTypescriptType extends FullyQualifiedJavaType {

    private final String projectRootAlias;

    public FullyQualifiedTypescriptType(String fullTypeSpecification) {
        super(fullTypeSpecification);
        this.projectRootAlias = "";
    }

    public FullyQualifiedTypescriptType(String projectRootAlias, String fullTypeSpecification) {
        super(fullTypeSpecification);
        this.projectRootAlias = StringUtils.defaultString(projectRootAlias, "");
    }

    public String getProjectRootAlias() {
        return projectRootAlias;
    }

    public String getPackagePath() {
        return this.projectRootAlias + this.getPackageName().replaceAll("\\.", "/");
    }

    public String getFileName() {
        return StringUtils.substringAfterLast(this.getPackageName(), ".");
    }

    @Override
    public FullyQualifiedJavaType create(FullyQualifiedJavaType type) {
        return super.create(type);
    }

    @Override
    public FullyQualifiedJavaType create(String type) {
        return super.create(type);
    }

}

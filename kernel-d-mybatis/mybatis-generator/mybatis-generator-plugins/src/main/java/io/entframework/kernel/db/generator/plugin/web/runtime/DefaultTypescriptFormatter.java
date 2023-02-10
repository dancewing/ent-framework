package io.entframework.kernel.db.generator.plugin.web.runtime;

import io.entframework.kernel.db.generator.plugin.web.runtime.render.TopLevelClassRenderer;
import io.entframework.kernel.db.generator.plugin.web.runtime.render.TopLevelEnumerationRenderer;
import io.entframework.kernel.db.generator.plugin.web.runtime.render.TopLevelInterfaceRenderer;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

public class DefaultTypescriptFormatter implements JavaFormatter, CompilationUnitVisitor<String> {
    protected Context context;

    public DefaultTypescriptFormatter(Context context) {
        this.context = context;
    }

    @Override
    public String getFormattedContent(CompilationUnit compilationUnit) {
        return compilationUnit.accept(this);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String visit(TopLevelClass topLevelClass) {
        return new TopLevelClassRenderer().render(topLevelClass);
    }

    @Override
    public String visit(TopLevelEnumeration topLevelEnumeration) {
        return new TopLevelEnumerationRenderer().render(topLevelEnumeration);
    }

    @Override
    public String visit(Interface topLevelInterface) {
        return new TopLevelInterfaceRenderer().render(topLevelInterface);
    }
}

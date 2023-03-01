package org.mybatis.dynamic.sql.update.render;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.*;

import java.util.Optional;

public class UpdateValuePhraseVisitor extends UpdateRowMappingVisitor<Optional<SetAndWhere>> {

    protected final RenderingStrategy renderingStrategy;

    public UpdateValuePhraseVisitor(RenderingStrategy renderingStrategy) {
        this.renderingStrategy = renderingStrategy;
    }

    /**
     * 特殊处理，ValueMapping放到UpdateRowStatementProvider中的Parameters
     * @param mapping
     * @param <T>
     * @return
     */
    @Override
    public <T> Optional<SetAndWhere> visit(ValueMapping<T> mapping) {
        return SetAndWhere.withFieldName(mapping.columnName())
                .withValuePhrase(mapping.mapColumn(c -> calculateJdbcMapPlaceholder(c, mapping.columnName())))
                .withValue(mapping.value()).buildOptional();
    }

    @Override
    public Optional<SetAndWhere> visit(NullMapping mapping) {
        return SetAndWhere.withFieldName(mapping.columnName()).withValuePhrase("null") //$NON-NLS-1$
                .buildOptional();
    }

    @Override
    public Optional<SetAndWhere> visit(ConstantMapping mapping) {
        return SetAndWhere.withFieldName(mapping.columnName()).withValuePhrase(mapping.constant()).buildOptional();
    }

    @Override
    public Optional<SetAndWhere> visit(StringConstantMapping mapping) {
        return SetAndWhere.withFieldName(mapping.columnName()).withValuePhrase("'" + mapping.constant() + "'") //$NON-NLS-1$ //$NON-NLS-2$
                .buildOptional();
    }

    @Override
    public Optional<SetAndWhere> visit(PropertyMapping mapping) {
        return SetAndWhere.withFieldName(mapping.columnName())
                .withValuePhrase(mapping.mapColumn(c -> calculateJdbcPlaceholder(c, mapping.property())))
                .buildOptional();
    }

    @Override
    public Optional<SetAndWhere> visit(PropertyWhenPresentMapping mapping) {
        if (mapping.shouldRender()) {
            return visit((PropertyMapping) mapping);
        }
        else {
            return Optional.empty();
        }
    }

    private String calculateJdbcPlaceholder(SqlColumn<?> column, String parameterName) {
        return column.renderingStrategy().orElse(renderingStrategy).getFormattedJdbcPlaceholder(column, "row", //$NON-NLS-1$
                parameterName);
    }

    private String calculateJdbcMapPlaceholder(SqlColumn<?> column, String parameterName) {
        return column.renderingStrategy().orElse(renderingStrategy).getFormattedJdbcPlaceholder(column, "parameters", //$NON-NLS-1$
                parameterName);
    }

}

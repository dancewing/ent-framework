package org.mybatis.dynamic.sql.update.render;

import org.mybatis.dynamic.sql.exception.InvalidSqlException;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.UpdateRowModel;
import org.mybatis.dynamic.sql.util.Messages;

import java.util.*;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

public class UpdateRowRenderer<T> {
    private final UpdateRowModel<T> model;
    private final RenderingStrategy renderingStrategy;

    private UpdateRowRenderer(Builder<T> builder) {
        this.model = Objects.requireNonNull(builder.model);
        this.renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }

    public UpdateRowStatementProvider<T> render() {
        UpdateValuePhraseVisitor visitor = new UpdateValuePhraseVisitor(renderingStrategy);
        List<Optional<SetAndWhere>> fieldsAndValues = model.mapColumnMappings(m -> m.accept(visitor))
                .toList();

        if (fieldsAndValues.stream().noneMatch(Optional::isPresent)) {
            throw new InvalidSqlException(Messages.getString("ERROR.32")); //$NON-NLS-1$
        }

        List<Optional<SetAndWhere>> whereCondition = model.whereMappings(m -> m.accept(visitor))
                .toList();

        if (whereCondition.stream().noneMatch(Optional::isPresent)) {
            throw new InvalidSqlException(Messages.getString("ERROR.33")); //$NON-NLS-1$
        }

        List<Optional<SetAndWhere>> parameters = model.parameterMappings(m -> m.accept(visitor))
                .toList();

        return DefaultUpdateRowStatementProvider.withRow(model.row())
                .withUpdateStatement(calculateUpdateStatement(fieldsAndValues, whereCondition))
                .withParameters(calculateParameters(parameters))
                .build();
    }

    private Map<String, Object> calculateParameters(List<Optional<SetAndWhere>> parameters) {
        Map<String, Object> results = new HashMap<>();
        if (parameters != null) {
            parameters.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(setAndWhere -> {
                        if (setAndWhere.value().isPresent()) {
                            results.put(setAndWhere.fieldName(), setAndWhere.value().get());
                        }
                    });
        }
        return results;
    }

    private String calculateUpdateStatement(List<Optional<SetAndWhere>> fieldsAndValues, List<Optional<SetAndWhere>> whereCondition) {
        return "update" //$NON-NLS-1$
                + spaceBefore(model.table().tableNameAtRuntime())
                + spaceBefore("set")
                + spaceBefore(calculateSetFieldAndValuePhrase(fieldsAndValues))
                + spaceBefore("where")
                + spaceBefore(calculateWherePhrase(whereCondition));
    }

    private String calculateSetFieldAndValuePhrase(List<Optional<SetAndWhere>> fieldsAndValues) {
        return fieldsAndValues.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(fieldAndValue -> fieldAndValue.fieldName() + " = " + fieldAndValue.valuePhrase())
                .collect(Collectors.joining(", ", "", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private String calculateWherePhrase(List<Optional<SetAndWhere>> fieldsAndValues) {
        return fieldsAndValues.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(fieldAndValue -> fieldAndValue.fieldName() + " = " + fieldAndValue.valuePhrase())
                .collect(Collectors.joining(" and ", "", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static <T> Builder<T> withUpdateModel(UpdateRowModel<T> model) {
        return new Builder<T>().withUpdateModel(model);
    }

    public static class Builder<T> {
        private UpdateRowModel<T> model;
        private RenderingStrategy renderingStrategy;

        public Builder<T> withUpdateModel(UpdateRowModel<T> model) {
            this.model = model;
            return this;
        }

        public Builder<T> withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public UpdateRowRenderer<T> build() {
            return new UpdateRowRenderer<>(this);
        }
    }
}

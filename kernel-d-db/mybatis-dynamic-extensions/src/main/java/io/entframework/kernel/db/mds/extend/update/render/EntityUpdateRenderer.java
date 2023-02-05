package io.entframework.kernel.db.mds.extend.update.render;

import io.entframework.kernel.db.mds.extend.update.EntityUpdateModel;
import org.mybatis.dynamic.sql.exception.InvalidSqlException;
import org.mybatis.dynamic.sql.insert.render.FieldAndValue;
import org.mybatis.dynamic.sql.insert.render.ValuePhraseVisitor;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.Messages;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

public class EntityUpdateRenderer<T> {
    private final EntityUpdateModel<T> model;
    private final RenderingStrategy renderingStrategy;

    private EntityUpdateRenderer(Builder<T> builder) {
        this.model = Objects.requireNonNull(builder.model);
        this.renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }

    public EntityUpdateStatementProvider<T> render() {
        ValuePhraseVisitor visitor = new ValuePhraseVisitor(renderingStrategy);

        List<Optional<FieldAndValue>> fieldsAndValues = model.mapColumnMappings(m -> m.accept(visitor))
                .toList();

        if (fieldsAndValues.stream().noneMatch(Optional::isPresent)) {
            throw new InvalidSqlException(Messages.getString("ERROR.10")); //$NON-NLS-1$
        }

        return DefaultEntityUpdateStatementProvider.withRow(model.row())
                .withUpdateStatement(calculateUpdateStatement(fieldsAndValues))
                .build();
    }

    private String calculateUpdateStatement(List<Optional<FieldAndValue>> fieldsAndValues) {
        return "update" //$NON-NLS-1$
                + spaceBefore(model.table().tableNameAtRuntime())
                + spaceBefore("set")
                + spaceBefore(calculateSetFieldAndValuePhrase(fieldsAndValues));
    }

    private String calculateSetFieldAndValuePhrase(List<Optional<FieldAndValue>> fieldsAndValues) {
        return fieldsAndValues.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(fieldAndValue -> fieldAndValue.fieldName() + " = " + fieldAndValue.valuePhrase())
                .collect(Collectors.joining(", ", "", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private String calculateColumnsPhrase(List<Optional<FieldAndValue>> fieldsAndValues) {
        return fieldsAndValues.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(FieldAndValue::fieldName)
                .collect(Collectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private String calculateValuesPhrase(List<Optional<FieldAndValue>> fieldsAndValues) {
        return fieldsAndValues.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(FieldAndValue::valuePhrase)
                .collect(Collectors.joining(", ", "values (", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static <T> Builder<T> withUpdateModel(EntityUpdateModel<T> model) {
        return new Builder<T>().withUpdateModel(model);
    }

    public static class Builder<T> {
        private EntityUpdateModel<T> model;
        private RenderingStrategy renderingStrategy;

        public Builder<T> withUpdateModel(EntityUpdateModel<T> model) {
            this.model = model;
            return this;
        }

        public Builder<T> withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public EntityUpdateRenderer<T> build() {
            return new EntityUpdateRenderer<>(this);
        }
    }
}

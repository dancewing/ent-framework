package io.entframework.kernel.db.mybatis.mapper;


import io.entframework.kernel.db.mybatis.util.VersionFieldUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.annotation.GeneratedValue;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Version;
import org.mybatis.dynamic.sql.exception.DynamicSqlException;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.ReflectUtils;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;
import org.mybatis.dynamic.sql.util.mybatis3.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GeneralMapperSupport extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper, CommonSelectMapper, CommonInsertMapper, CommonUpdateRowMapper {

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    <E> List<E> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    <E> Optional<E> selectOne(SelectStatementProvider selectStatement);

    default <E> Optional<E> selectOne(Class<E> entityClass, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, entityClass, completer);
    }

    default <E> List<E> select(Class<E> entityClass, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, entityClass, completer);
    }

    default <E> List<E> selectDistinct(Class<E> entityClass, SelectDSLCompleter completer) {
        EntityMeta entities = Entities.getInstance(entityClass);
        return MyBatis3Utils.selectDistinct(this::selectMany, entities.getSelectColumns(), entities.getTable(), completer);
    }

    default <E> int insert(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
            List<FieldAndColumn> columns = entities.getColumnsForInsert();
                    columns.forEach(wrapper -> c.map(wrapper.column()).toProperty(wrapper.field().getName()));
                    return c;
                }
        );
    }

    default <E> int insertMultiple(List<E> records) {
        if (records != null && !records.isEmpty()) {
            Class<E> entityClass = (Class<E>) records.get(0).getClass();
            EntityMeta entities = Entities.getInstance(entityClass);
            return MyBatis3Utils.insertMultiple(this::insertMultiple, records, entities.getTable(), c -> {
                List<FieldAndColumn> columns = entities.getColumnsForInsert();
                columns.forEach(wrapper -> c.map(wrapper.column()).toProperty(wrapper.field().getName()));
                return c;
            });
        }
        return -1;
    }

    default <E> int insertSelective(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
            List<FieldAndColumn> columns = entities.getColumnsForInsert();
            columns.forEach(wrapper -> {
                Object value = ReflectUtils.getFieldValue(row, wrapper.fieldName());
                if (wrapper.isAnnotationPresent(Id.class) && !wrapper.isAnnotationPresent(GeneratedValue.class)) {
                    c.map(wrapper.column()).toProperty(wrapper.fieldName());
                } else {
                    c.map(wrapper.column()).toPropertyWhenPresent(wrapper.fieldName(), () -> value);
                }
            });
            return c;
        });
    }

    default <E> int updateByPrimaryKey(E row, boolean selective) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.updateRow(this::updateRow, row, entities.getTable(), c -> {
                    List<FieldAndColumn> columns = entities.getColumnsForUpdate();
                    columns.forEach(column -> {
                        Object value = ReflectUtils.getFieldValue(row, column.fieldName());
                        if (column.isAnnotationPresent(Version.class)) {
                            value = VersionFieldUtils.increaseVersionVal(column.fieldType(), value);
                            c.set(column.column()).toValue(value);
                        } else {
                            if (selective) {
                                if (value != null) {
                                    c.set(column.column()).toProperty(column.fieldName());
                                }
                            } else {
                                c.set(column.column()).toProperty(column.fieldName());
                            }
                        }
                    });

                    FieldAndColumn primaryKey = entities.getPrimaryKey();
                    SqlColumn<Object> pkColumn = primaryKey.column();
                    try {
                        c.where(pkColumn).toProperty(primaryKey.fieldName());
                        Optional<FieldAndColumn> versionColumnOp = entities.findVersionColumn();
                        versionColumnOp.ifPresent(versionColumn -> c.where(versionColumn.column()).toProperty(versionColumn.fieldName()));
                    } catch (Exception ex) {
                        throw new DynamicSqlException(ex.getMessage());
                    }
                    return c;
                }
        );
    }
}

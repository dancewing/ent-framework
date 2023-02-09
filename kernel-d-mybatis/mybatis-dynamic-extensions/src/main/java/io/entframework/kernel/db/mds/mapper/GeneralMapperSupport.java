package io.entframework.kernel.db.mds.mapper;

import io.entframework.kernel.db.mds.util.MapperProxyUtils;
import io.entframework.kernel.rule.util.ReflectionKit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;
import org.mybatis.dynamic.sql.util.mybatis3.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GeneralMapperSupport extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper, CommonSelectMapper, CommonInsertMapper {

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

    default <E> List<E> selectDistinct(SelectDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.selectDistinct(this::selectMany, entities.getSelectColumns(), entities.getTable(), completer);
    }

    default <E> int insert(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
                    List<FieldAndColumn> columns = entities.getColumns();
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
                List<FieldAndColumn> columns = entities.getColumns();
                columns.forEach(wrapper -> c.map(wrapper.column()).toProperty(wrapper.field().getName()));
                return c;
            });
        }
        return -1;
    }

    default <E> int insertSelective(E row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
            List<FieldAndColumn> columns = entities.getColumns();
            columns.forEach(wrapper -> {
                Object value = ReflectionKit.getFieldValue(row, wrapper.fieldName());
                c.map(wrapper.column()).toPropertyWhenPresent(wrapper.fieldName(), () -> value);
            });
            return c;
        });
    }
}

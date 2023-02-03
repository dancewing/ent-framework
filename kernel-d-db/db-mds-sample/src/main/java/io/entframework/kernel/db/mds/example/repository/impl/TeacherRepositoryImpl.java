package io.entframework.kernel.db.mds.example.repository.impl;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.mapper.TeacherDynamicSqlSupport;
import io.entframework.kernel.db.mds.example.repository.TeacherRepository;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.DeleteModel;

public class TeacherRepositoryImpl extends BaseRepositoryImpl<Teacher> implements TeacherRepository {
    public TeacherRepositoryImpl() {
        super(Teacher.class);
    }

    public TeacherRepositoryImpl(Class<? extends Teacher> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher insert(Teacher row) {
        int count = getMapper().insert(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Teacher> insertMultiple(List<Teacher> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        getMapper().insertMultiple(records);
        return records;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher update(Teacher row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Teacher row) {
        if (row == null || row.getId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        if (row.getVersion() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().delete(c -> {
            DeleteDSL<DeleteModel>.DeleteWhereBuilder deleteDSL = c.where();
            deleteDSL.and(TeacherDynamicSqlSupport.id, isEqualTo(row.getId()).filter(Objects::nonNull));
            deleteDSL.and(TeacherDynamicSqlSupport.version, isEqualTo(row.getVersion()).filter(Objects::nonNull));
            return deleteDSL;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher get(Object id) {
        Optional<Teacher> row = getMapper().selectByPrimaryKey(id);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
        });
    }
}
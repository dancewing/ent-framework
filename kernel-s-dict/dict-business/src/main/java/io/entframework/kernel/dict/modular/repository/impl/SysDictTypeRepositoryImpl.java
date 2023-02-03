package io.entframework.kernel.dict.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.dict.modular.entity.SysDictType;
import io.entframework.kernel.dict.modular.repository.SysDictTypeRepository;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysDictTypeRepositoryImpl extends BaseRepositoryImpl<SysDictType> implements SysDictTypeRepository {
    public SysDictTypeRepositoryImpl() {
        super(SysDictType.class);
    }

    public SysDictTypeRepositoryImpl(Class<? extends SysDictType> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysDictType insert(SysDictType row) {
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
    public List<SysDictType> insertMultiple(List<SysDictType> records) {
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
    public SysDictType update(SysDictType row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getDictTypeId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysDictType row) {
        if (row == null || row.getDictTypeId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysDictType get(Object dictTypeId) {
        Optional<SysDictType> row = getMapper().selectByPrimaryKey(dictTypeId);
        if (row.isPresent()) {
            SysDictType sysDictType =  row.get();
            if (sysDictType.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, dictTypeId);
            }
            return sysDictType;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, dictTypeId);
    }
}
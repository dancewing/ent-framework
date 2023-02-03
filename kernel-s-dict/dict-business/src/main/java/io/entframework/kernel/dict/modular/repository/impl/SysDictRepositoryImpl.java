package io.entframework.kernel.dict.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.dict.modular.entity.SysDict;
import io.entframework.kernel.dict.modular.repository.SysDictRepository;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysDictRepositoryImpl extends BaseRepositoryImpl<SysDict> implements SysDictRepository {
    public SysDictRepositoryImpl() {
        super(SysDict.class);
    }

    public SysDictRepositoryImpl(Class<? extends SysDict> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysDict insert(SysDict row) {
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
    public List<SysDict> insertMultiple(List<SysDict> records) {
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
    public SysDict update(SysDict row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getDictId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysDict row) {
        if (row == null || row.getDictId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysDict get(Object dictId) {
        Optional<SysDict> row = getMapper().selectByPrimaryKey(dictId);
        if (row.isPresent()) {
            SysDict sysDict =  row.get();
            if (sysDict.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, dictId);
            }
            return sysDict;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, dictId);
    }
}
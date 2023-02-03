package io.entframework.kernel.sms.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.sms.modular.entity.SysSms;
import io.entframework.kernel.sms.modular.repository.SysSmsRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysSmsRepositoryImpl extends BaseRepositoryImpl<SysSms> implements SysSmsRepository {
    public SysSmsRepositoryImpl() {
        super(SysSms.class);
    }

    public SysSmsRepositoryImpl(Class<? extends SysSms> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysSms insert(SysSms row) {
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
    public List<SysSms> insertMultiple(List<SysSms> records) {
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
    public SysSms update(SysSms row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getSmsId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysSms row) {
        if (row == null || row.getSmsId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getSmsId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysSms get(Object smsId) {
        Optional<SysSms> row = getMapper().selectByPrimaryKey(smsId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, smsId);
        });
    }
}
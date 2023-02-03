package io.entframework.kernel.system.modular.loginlog.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.loginlog.entity.SysLoginLog;
import io.entframework.kernel.system.modular.loginlog.repository.SysLoginLogRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysLoginLogRepositoryImpl extends BaseRepositoryImpl<SysLoginLog> implements SysLoginLogRepository {
    public SysLoginLogRepositoryImpl() {
        super(SysLoginLog.class);
    }

    public SysLoginLogRepositoryImpl(Class<? extends SysLoginLog> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysLoginLog insert(SysLoginLog row) {
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
    public List<SysLoginLog> insertMultiple(List<SysLoginLog> records) {
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
    public SysLoginLog update(SysLoginLog row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getLlgId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysLoginLog row) {
        if (row == null || row.getLlgId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getLlgId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysLoginLog get(Object llgId) {
        Optional<SysLoginLog> row = getMapper().selectByPrimaryKey(llgId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, llgId);
        });
    }
}
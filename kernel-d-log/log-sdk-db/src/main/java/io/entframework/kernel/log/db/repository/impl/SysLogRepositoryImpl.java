package io.entframework.kernel.log.db.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.log.db.entity.SysLog;
import io.entframework.kernel.log.db.repository.SysLogRepository;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysLogRepositoryImpl extends BaseRepositoryImpl<SysLog> implements SysLogRepository {
    public SysLogRepositoryImpl() {
        super(SysLog.class);
    }

    public SysLogRepositoryImpl(Class<? extends SysLog> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysLog insert(SysLog row) {
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
    public List<SysLog> insertMultiple(List<SysLog> records) {
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
    public SysLog update(SysLog row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getLogId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysLog row) {
        if (row == null || row.getLogId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getLogId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysLog get(Object logId) {
        Optional<SysLog> row = getMapper().selectByPrimaryKey(logId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, logId);
        });
    }
}
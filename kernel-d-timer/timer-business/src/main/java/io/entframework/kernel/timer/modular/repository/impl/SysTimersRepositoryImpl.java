package io.entframework.kernel.timer.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.timer.modular.entity.SysTimers;
import io.entframework.kernel.timer.modular.repository.SysTimersRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysTimersRepositoryImpl extends BaseRepositoryImpl<SysTimers> implements SysTimersRepository {
    public SysTimersRepositoryImpl() {
        super(SysTimers.class);
    }

    public SysTimersRepositoryImpl(Class<? extends SysTimers> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysTimers insert(SysTimers row) {
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
    public List<SysTimers> insertMultiple(List<SysTimers> records) {
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
    public SysTimers update(SysTimers row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getTimerId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysTimers row) {
        if (row == null || row.getTimerId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysTimers get(Object timerId) {
        Optional<SysTimers> row = getMapper().selectByPrimaryKey(timerId);
        if (row.isPresent()) {
            SysTimers sysTimers =  row.get();
            if (sysTimers.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, timerId);
            }
            return sysTimers;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, timerId);
    }
}
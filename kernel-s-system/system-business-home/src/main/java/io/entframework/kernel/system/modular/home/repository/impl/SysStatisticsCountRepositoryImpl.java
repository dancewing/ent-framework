package io.entframework.kernel.system.modular.home.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import io.entframework.kernel.system.modular.home.repository.SysStatisticsCountRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysStatisticsCountRepositoryImpl extends BaseRepositoryImpl<SysStatisticsCount> implements SysStatisticsCountRepository {
    public SysStatisticsCountRepositoryImpl() {
        super(SysStatisticsCount.class);
    }

    public SysStatisticsCountRepositoryImpl(Class<? extends SysStatisticsCount> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysStatisticsCount insert(SysStatisticsCount row) {
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
    public List<SysStatisticsCount> insertMultiple(List<SysStatisticsCount> records) {
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
    public SysStatisticsCount update(SysStatisticsCount row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getStatCountId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysStatisticsCount row) {
        if (row == null || row.getStatCountId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getStatCountId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysStatisticsCount get(Object statCountId) {
        Optional<SysStatisticsCount> row = getMapper().selectByPrimaryKey(statCountId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, statCountId);
        });
    }
}
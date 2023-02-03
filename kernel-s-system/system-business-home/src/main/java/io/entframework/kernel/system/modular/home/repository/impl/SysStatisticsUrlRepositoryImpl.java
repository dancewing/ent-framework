package io.entframework.kernel.system.modular.home.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import io.entframework.kernel.system.modular.home.repository.SysStatisticsUrlRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysStatisticsUrlRepositoryImpl extends BaseRepositoryImpl<SysStatisticsUrl> implements SysStatisticsUrlRepository {
    public SysStatisticsUrlRepositoryImpl() {
        super(SysStatisticsUrl.class);
    }

    public SysStatisticsUrlRepositoryImpl(Class<? extends SysStatisticsUrl> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysStatisticsUrl insert(SysStatisticsUrl row) {
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
    public List<SysStatisticsUrl> insertMultiple(List<SysStatisticsUrl> records) {
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
    public SysStatisticsUrl update(SysStatisticsUrl row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getStatUrlId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysStatisticsUrl row) {
        if (row == null || row.getStatUrlId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getStatUrlId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysStatisticsUrl get(Object statUrlId) {
        Optional<SysStatisticsUrl> row = getMapper().selectByPrimaryKey(statUrlId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, statUrlId);
        });
    }
}
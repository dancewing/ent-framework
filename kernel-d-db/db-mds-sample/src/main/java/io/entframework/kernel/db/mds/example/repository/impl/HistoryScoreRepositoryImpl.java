package io.entframework.kernel.db.mds.example.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.repository.HistoryScoreRepository;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HistoryScoreRepositoryImpl extends BaseRepositoryImpl<HistoryScore> implements HistoryScoreRepository {
    public HistoryScoreRepositoryImpl() {
        super(HistoryScore.class);
    }

    public HistoryScoreRepositoryImpl(Class<? extends HistoryScore> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryScore insert(HistoryScore row) {
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
    public List<HistoryScore> insertMultiple(List<HistoryScore> records) {
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
    public HistoryScore update(HistoryScore row) {
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
    public int delete(HistoryScore row) {
        if (row == null || row.getId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryScore get(Object id) {
        Optional<HistoryScore> row = getMapper().selectByPrimaryKey(id);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
        });
    }
}
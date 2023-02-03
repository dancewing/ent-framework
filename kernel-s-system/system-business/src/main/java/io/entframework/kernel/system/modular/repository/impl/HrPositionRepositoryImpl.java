package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.HrPosition;
import io.entframework.kernel.system.modular.repository.HrPositionRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HrPositionRepositoryImpl extends BaseRepositoryImpl<HrPosition> implements HrPositionRepository {
    public HrPositionRepositoryImpl() {
        super(HrPosition.class);
    }

    public HrPositionRepositoryImpl(Class<? extends HrPosition> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrPosition insert(HrPosition row) {
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
    public List<HrPosition> insertMultiple(List<HrPosition> records) {
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
    public HrPosition update(HrPosition row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getPositionId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(HrPosition row) {
        if (row == null || row.getPositionId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrPosition get(Object positionId) {
        Optional<HrPosition> row = getMapper().selectByPrimaryKey(positionId);
        if (row.isPresent()) {
            HrPosition hrPosition =  row.get();
            if (hrPosition.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, positionId);
            }
            return hrPosition;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, positionId);
    }
}
package io.entframework.kernel.config.modular.repository.impl;

import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.config.modular.repository.SysConfigRepository;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysConfigRepositoryImpl extends BaseRepositoryImpl<SysConfig> implements SysConfigRepository {
    public SysConfigRepositoryImpl() {
        super(SysConfig.class);
    }

    public SysConfigRepositoryImpl(Class<? extends SysConfig> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfig insert(SysConfig row) {
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
    public List<SysConfig> insertMultiple(List<SysConfig> records) {
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
    public SysConfig update(SysConfig row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getConfigId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysConfig row) {
        if (row == null || row.getConfigId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysConfig get(Object configId) {
        Optional<SysConfig> row = getMapper().selectByPrimaryKey(configId);
        if (row.isPresent()) {
            SysConfig sysConfig =  row.get();
            if (sysConfig.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, configId);
            }
            return sysConfig;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, configId);
    }
}
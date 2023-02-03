package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysApp;
import io.entframework.kernel.system.modular.repository.SysAppRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysAppRepositoryImpl extends BaseRepositoryImpl<SysApp> implements SysAppRepository {
    public SysAppRepositoryImpl() {
        super(SysApp.class);
    }

    public SysAppRepositoryImpl(Class<? extends SysApp> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysApp insert(SysApp row) {
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
    public List<SysApp> insertMultiple(List<SysApp> records) {
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
    public SysApp update(SysApp row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getAppId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysApp row) {
        if (row == null || row.getAppId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysApp get(Object appId) {
        Optional<SysApp> row = getMapper().selectByPrimaryKey(appId);
        if (row.isPresent()) {
            SysApp sysApp =  row.get();
            if (sysApp.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, appId);
            }
            return sysApp;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, appId);
    }
}
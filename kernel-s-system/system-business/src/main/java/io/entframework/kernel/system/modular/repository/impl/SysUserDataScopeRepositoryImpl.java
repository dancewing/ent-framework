package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysUserDataScope;
import io.entframework.kernel.system.modular.repository.SysUserDataScopeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysUserDataScopeRepositoryImpl extends BaseRepositoryImpl<SysUserDataScope> implements SysUserDataScopeRepository {
    public SysUserDataScopeRepositoryImpl() {
        super(SysUserDataScope.class);
    }

    public SysUserDataScopeRepositoryImpl(Class<? extends SysUserDataScope> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserDataScope insert(SysUserDataScope row) {
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
    public List<SysUserDataScope> insertMultiple(List<SysUserDataScope> records) {
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
    public SysUserDataScope update(SysUserDataScope row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getUserDataScopeId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysUserDataScope row) {
        if (row == null || row.getUserDataScopeId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getUserDataScopeId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserDataScope get(Object userDataScopeId) {
        Optional<SysUserDataScope> row = getMapper().selectByPrimaryKey(userDataScopeId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, userDataScopeId);
        });
    }
}
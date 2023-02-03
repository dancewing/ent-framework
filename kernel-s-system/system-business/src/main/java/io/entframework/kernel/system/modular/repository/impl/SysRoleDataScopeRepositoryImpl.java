package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysRoleDataScope;
import io.entframework.kernel.system.modular.repository.SysRoleDataScopeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysRoleDataScopeRepositoryImpl extends BaseRepositoryImpl<SysRoleDataScope> implements SysRoleDataScopeRepository {
    public SysRoleDataScopeRepositoryImpl() {
        super(SysRoleDataScope.class);
    }

    public SysRoleDataScopeRepositoryImpl(Class<? extends SysRoleDataScope> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleDataScope insert(SysRoleDataScope row) {
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
    public List<SysRoleDataScope> insertMultiple(List<SysRoleDataScope> records) {
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
    public SysRoleDataScope update(SysRoleDataScope row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getRoleDataScopeId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysRoleDataScope row) {
        if (row == null || row.getRoleDataScopeId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getRoleDataScopeId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleDataScope get(Object roleDataScopeId) {
        Optional<SysRoleDataScope> row = getMapper().selectByPrimaryKey(roleDataScopeId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, roleDataScopeId);
        });
    }
}
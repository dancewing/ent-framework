package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysUserRole;
import io.entframework.kernel.system.modular.repository.SysUserRoleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysUserRoleRepositoryImpl extends BaseRepositoryImpl<SysUserRole> implements SysUserRoleRepository {
    public SysUserRoleRepositoryImpl() {
        super(SysUserRole.class);
    }

    public SysUserRoleRepositoryImpl(Class<? extends SysUserRole> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRole insert(SysUserRole row) {
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
    public List<SysUserRole> insertMultiple(List<SysUserRole> records) {
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
    public SysUserRole update(SysUserRole row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getUserRoleId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysUserRole row) {
        if (row == null || row.getUserRoleId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getUserRoleId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUserRole get(Object userRoleId) {
        Optional<SysUserRole> row = getMapper().selectByPrimaryKey(userRoleId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, userRoleId);
        });
    }
}
package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysRoleResource;
import io.entframework.kernel.system.modular.repository.SysRoleResourceRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysRoleResourceRepositoryImpl extends BaseRepositoryImpl<SysRoleResource> implements SysRoleResourceRepository {
    public SysRoleResourceRepositoryImpl() {
        super(SysRoleResource.class);
    }

    public SysRoleResourceRepositoryImpl(Class<? extends SysRoleResource> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleResource insert(SysRoleResource row) {
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
    public List<SysRoleResource> insertMultiple(List<SysRoleResource> records) {
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
    public SysRoleResource update(SysRoleResource row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getRoleResourceId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysRoleResource row) {
        if (row == null || row.getRoleResourceId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getRoleResourceId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleResource get(Object roleResourceId) {
        Optional<SysRoleResource> row = getMapper().selectByPrimaryKey(roleResourceId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, roleResourceId);
        });
    }
}
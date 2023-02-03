package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysRole;
import io.entframework.kernel.system.modular.repository.SysRoleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysRoleRepositoryImpl extends BaseRepositoryImpl<SysRole> implements SysRoleRepository {
    public SysRoleRepositoryImpl() {
        super(SysRole.class);
    }

    public SysRoleRepositoryImpl(Class<? extends SysRole> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRole insert(SysRole row) {
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
    public List<SysRole> insertMultiple(List<SysRole> records) {
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
    public SysRole update(SysRole row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getRoleId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysRole row) {
        if (row == null || row.getRoleId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRole get(Object roleId) {
        Optional<SysRole> row = getMapper().selectByPrimaryKey(roleId);
        if (row.isPresent()) {
            SysRole sysRole =  row.get();
            if (sysRole.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, roleId);
            }
            return sysRole;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, roleId);
    }
}
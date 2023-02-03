package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;
import io.entframework.kernel.system.modular.repository.SysRoleMenuRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysRoleMenuRepositoryImpl extends BaseRepositoryImpl<SysRoleMenu> implements SysRoleMenuRepository {
    public SysRoleMenuRepositoryImpl() {
        super(SysRoleMenu.class);
    }

    public SysRoleMenuRepositoryImpl(Class<? extends SysRoleMenu> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleMenu insert(SysRoleMenu row) {
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
    public List<SysRoleMenu> insertMultiple(List<SysRoleMenu> records) {
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
    public SysRoleMenu update(SysRoleMenu row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getRoleMenuId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysRoleMenu row) {
        if (row == null || row.getRoleMenuId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getRoleMenuId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleMenu get(Object roleMenuId) {
        Optional<SysRoleMenu> row = getMapper().selectByPrimaryKey(roleMenuId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, roleMenuId);
        });
    }
}
package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.SysMenuResource;
import io.entframework.kernel.system.modular.repository.SysMenuResourceRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysMenuResourceRepositoryImpl extends BaseRepositoryImpl<SysMenuResource> implements SysMenuResourceRepository {
    public SysMenuResourceRepositoryImpl() {
        super(SysMenuResource.class);
    }

    public SysMenuResourceRepositoryImpl(Class<? extends SysMenuResource> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMenuResource insert(SysMenuResource row) {
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
    public List<SysMenuResource> insertMultiple(List<SysMenuResource> records) {
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
    public SysMenuResource update(SysMenuResource row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getMenuResourceId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysMenuResource row) {
        if (row == null || row.getMenuResourceId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getMenuResourceId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMenuResource get(Object menuResourceId) {
        Optional<SysMenuResource> row = getMapper().selectByPrimaryKey(menuResourceId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, menuResourceId);
        });
    }
}
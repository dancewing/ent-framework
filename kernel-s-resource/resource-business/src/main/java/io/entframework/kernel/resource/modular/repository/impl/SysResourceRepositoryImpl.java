package io.entframework.kernel.resource.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.resource.modular.entity.SysResource;
import io.entframework.kernel.resource.modular.repository.SysResourceRepository;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysResourceRepositoryImpl extends BaseRepositoryImpl<SysResource> implements SysResourceRepository {
    public SysResourceRepositoryImpl() {
        super(SysResource.class);
    }

    public SysResourceRepositoryImpl(Class<? extends SysResource> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysResource insert(SysResource row) {
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
    public List<SysResource> insertMultiple(List<SysResource> records) {
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
    public SysResource update(SysResource row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getResourceId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysResource row) {
        if (row == null || row.getResourceId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getResourceId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysResource get(Object resourceId) {
        Optional<SysResource> row = getMapper().selectByPrimaryKey(resourceId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, resourceId);
        });
    }
}
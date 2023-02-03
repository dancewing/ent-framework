package io.entframework.kernel.file.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.file.modular.entity.SysFileStorage;
import io.entframework.kernel.file.modular.repository.SysFileStorageRepository;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysFileStorageRepositoryImpl extends BaseRepositoryImpl<SysFileStorage> implements SysFileStorageRepository {
    public SysFileStorageRepositoryImpl() {
        super(SysFileStorage.class);
    }

    public SysFileStorageRepositoryImpl(Class<? extends SysFileStorage> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysFileStorage insert(SysFileStorage row) {
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
    public List<SysFileStorage> insertMultiple(List<SysFileStorage> records) {
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
    public SysFileStorage update(SysFileStorage row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getFileId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysFileStorage row) {
        if (row == null || row.getFileId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getFileId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysFileStorage get(Object fileId) {
        Optional<SysFileStorage> row = getMapper().selectByPrimaryKey(fileId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, fileId);
        });
    }
}
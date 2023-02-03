package io.entframework.kernel.file.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import io.entframework.kernel.file.modular.repository.SysFileInfoRepository;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysFileInfoRepositoryImpl extends BaseRepositoryImpl<SysFileInfo> implements SysFileInfoRepository {
    public SysFileInfoRepositoryImpl() {
        super(SysFileInfo.class);
    }

    public SysFileInfoRepositoryImpl(Class<? extends SysFileInfo> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysFileInfo insert(SysFileInfo row) {
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
    public List<SysFileInfo> insertMultiple(List<SysFileInfo> records) {
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
    public SysFileInfo update(SysFileInfo row) {
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
    public int delete(SysFileInfo row) {
        if (row == null || row.getFileId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysFileInfo get(Object fileId) {
        Optional<SysFileInfo> row = getMapper().selectByPrimaryKey(fileId);
        if (row.isPresent()) {
            SysFileInfo sysFileInfo =  row.get();
            if (sysFileInfo.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, fileId);
            }
            return sysFileInfo;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, fileId);
    }
}
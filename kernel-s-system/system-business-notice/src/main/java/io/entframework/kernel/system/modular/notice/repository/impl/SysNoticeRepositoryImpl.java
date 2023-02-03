package io.entframework.kernel.system.modular.notice.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.notice.entity.SysNotice;
import io.entframework.kernel.system.modular.notice.repository.SysNoticeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysNoticeRepositoryImpl extends BaseRepositoryImpl<SysNotice> implements SysNoticeRepository {
    public SysNoticeRepositoryImpl() {
        super(SysNotice.class);
    }

    public SysNoticeRepositoryImpl(Class<? extends SysNotice> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysNotice insert(SysNotice row) {
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
    public List<SysNotice> insertMultiple(List<SysNotice> records) {
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
    public SysNotice update(SysNotice row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getNoticeId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysNotice row) {
        if (row == null || row.getNoticeId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysNotice get(Object noticeId) {
        Optional<SysNotice> row = getMapper().selectByPrimaryKey(noticeId);
        if (row.isPresent()) {
            SysNotice sysNotice =  row.get();
            if (sysNotice.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, noticeId);
            }
            return sysNotice;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, noticeId);
    }
}
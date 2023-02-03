package io.entframework.kernel.message.db.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.message.db.entity.SysMessage;
import io.entframework.kernel.message.db.repository.SysMessageRepository;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysMessageRepositoryImpl extends BaseRepositoryImpl<SysMessage> implements SysMessageRepository {
    public SysMessageRepositoryImpl() {
        super(SysMessage.class);
    }

    public SysMessageRepositoryImpl(Class<? extends SysMessage> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMessage insert(SysMessage row) {
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
    public List<SysMessage> insertMultiple(List<SysMessage> records) {
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
    public SysMessage update(SysMessage row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getMessageId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysMessage row) {
        if (row == null || row.getMessageId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getMessageId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMessage get(Object messageId) {
        Optional<SysMessage> row = getMapper().selectByPrimaryKey(messageId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, messageId);
        });
    }
}
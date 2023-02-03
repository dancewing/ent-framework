package io.entframework.kernel.i18n.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.i18n.modular.entity.Translation;
import io.entframework.kernel.i18n.modular.repository.TranslationRepository;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TranslationRepositoryImpl extends BaseRepositoryImpl<Translation> implements TranslationRepository {
    public TranslationRepositoryImpl() {
        super(Translation.class);
    }

    public TranslationRepositoryImpl(Class<? extends Translation> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Translation insert(Translation row) {
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
    public List<Translation> insertMultiple(List<Translation> records) {
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
    public Translation update(Translation row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getTranId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Translation row) {
        if (row == null || row.getTranId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getTranId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Translation get(Object tranId) {
        Optional<Translation> row = getMapper().selectByPrimaryKey(tranId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, tranId);
        });
    }
}
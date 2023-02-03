package io.entframework.kernel.system.modular.theme.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.theme.entity.SysTheme;
import io.entframework.kernel.system.modular.theme.repository.SysThemeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysThemeRepositoryImpl extends BaseRepositoryImpl<SysTheme> implements SysThemeRepository {
    public SysThemeRepositoryImpl() {
        super(SysTheme.class);
    }

    public SysThemeRepositoryImpl(Class<? extends SysTheme> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysTheme insert(SysTheme row) {
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
    public List<SysTheme> insertMultiple(List<SysTheme> records) {
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
    public SysTheme update(SysTheme row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getThemeId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysTheme row) {
        if (row == null || row.getThemeId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getThemeId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysTheme get(Object themeId) {
        Optional<SysTheme> row = getMapper().selectByPrimaryKey(themeId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, themeId);
        });
    }
}
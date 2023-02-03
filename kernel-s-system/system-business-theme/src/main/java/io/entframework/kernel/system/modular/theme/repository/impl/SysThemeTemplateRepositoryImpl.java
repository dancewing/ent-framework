package io.entframework.kernel.system.modular.theme.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplate;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysThemeTemplateRepositoryImpl extends BaseRepositoryImpl<SysThemeTemplate> implements SysThemeTemplateRepository {
    public SysThemeTemplateRepositoryImpl() {
        super(SysThemeTemplate.class);
    }

    public SysThemeTemplateRepositoryImpl(Class<? extends SysThemeTemplate> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplate insert(SysThemeTemplate row) {
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
    public List<SysThemeTemplate> insertMultiple(List<SysThemeTemplate> records) {
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
    public SysThemeTemplate update(SysThemeTemplate row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getTemplateId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysThemeTemplate row) {
        if (row == null || row.getTemplateId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getTemplateId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplate get(Object templateId) {
        Optional<SysThemeTemplate> row = getMapper().selectByPrimaryKey(templateId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, templateId);
        });
    }
}
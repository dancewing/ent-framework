package io.entframework.kernel.system.modular.theme.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateField;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateFieldRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysThemeTemplateFieldRepositoryImpl extends BaseRepositoryImpl<SysThemeTemplateField> implements SysThemeTemplateFieldRepository {
    public SysThemeTemplateFieldRepositoryImpl() {
        super(SysThemeTemplateField.class);
    }

    public SysThemeTemplateFieldRepositoryImpl(Class<? extends SysThemeTemplateField> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplateField insert(SysThemeTemplateField row) {
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
    public List<SysThemeTemplateField> insertMultiple(List<SysThemeTemplateField> records) {
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
    public SysThemeTemplateField update(SysThemeTemplateField row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getFieldId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysThemeTemplateField row) {
        if (row == null || row.getFieldId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getFieldId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplateField get(Object fieldId) {
        Optional<SysThemeTemplateField> row = getMapper().selectByPrimaryKey(fieldId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, fieldId);
        });
    }
}
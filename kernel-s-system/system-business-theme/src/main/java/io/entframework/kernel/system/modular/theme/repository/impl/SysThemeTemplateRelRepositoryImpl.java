package io.entframework.kernel.system.modular.theme.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateRelRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysThemeTemplateRelRepositoryImpl extends BaseRepositoryImpl<SysThemeTemplateRel> implements SysThemeTemplateRelRepository {
    public SysThemeTemplateRelRepositoryImpl() {
        super(SysThemeTemplateRel.class);
    }

    public SysThemeTemplateRelRepositoryImpl(Class<? extends SysThemeTemplateRel> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplateRel insert(SysThemeTemplateRel row) {
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
    public List<SysThemeTemplateRel> insertMultiple(List<SysThemeTemplateRel> records) {
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
    public SysThemeTemplateRel update(SysThemeTemplateRel row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getRelationId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysThemeTemplateRel row) {
        if (row == null || row.getRelationId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getRelationId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysThemeTemplateRel get(Object relationId) {
        Optional<SysThemeTemplateRel> row = getMapper().selectByPrimaryKey(relationId);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, relationId);
        });
    }
}
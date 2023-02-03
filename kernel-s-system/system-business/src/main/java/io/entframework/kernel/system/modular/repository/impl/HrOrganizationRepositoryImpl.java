package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import io.entframework.kernel.system.modular.repository.HrOrganizationRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HrOrganizationRepositoryImpl extends BaseRepositoryImpl<HrOrganization> implements HrOrganizationRepository {
    public HrOrganizationRepositoryImpl() {
        super(HrOrganization.class);
    }

    public HrOrganizationRepositoryImpl(Class<? extends HrOrganization> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrOrganization insert(HrOrganization row) {
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
    public List<HrOrganization> insertMultiple(List<HrOrganization> records) {
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
    public HrOrganization update(HrOrganization row) {
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getOrgId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(HrOrganization row) {
        if (row == null || row.getOrgId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrOrganization get(Object orgId) {
        Optional<HrOrganization> row = getMapper().selectByPrimaryKey(orgId);
        if (row.isPresent()) {
            HrOrganization hrOrganization =  row.get();
            if (hrOrganization.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, orgId);
            }
            return hrOrganization;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, orgId);
    }
}
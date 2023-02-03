package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import io.entframework.kernel.system.modular.entity.HrPosition;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.repository.SysUserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysUserRepositoryImpl extends BaseRepositoryImpl<SysUser> implements SysUserRepository {
    public SysUserRepositoryImpl() {
        super(SysUser.class);
    }

    public SysUserRepositoryImpl(Class<? extends SysUser> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser insert(SysUser row) {
        HrPosition position = row.getPosition();
        if (position != null && row.getPositionId() == null) {
            getMapper(HrPosition.class).insert(position);
            row.setPositionId(position.getPositionId());
        }
        HrOrganization organization = row.getOrganization();
        if (organization != null && row.getOrgId() == null) {
            getMapper(HrOrganization.class).insert(organization);
            row.setOrgId(organization.getOrgId());
        }
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
    public List<SysUser> insertMultiple(List<SysUser> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<HrPosition> positions = new ArrayList<>();
        for (SysUser record : records) {
            HrPosition position = record.getPosition();
            Optional.ofNullable(position).ifPresent(positions::add);
        }
        getMapper(HrPosition.class).insertMultiple(positions);
        records.forEach(record -> record.setPositionId(record.getPosition().getPositionId()));
        List<HrOrganization> organizations = new ArrayList<>();
        for (SysUser record : records) {
            HrOrganization organization = record.getOrganization();
            Optional.ofNullable(organization).ifPresent(organizations::add);
        }
        getMapper(HrOrganization.class).insertMultiple(organizations);
        records.forEach(record -> record.setOrgId(record.getOrganization().getOrgId()));
        getMapper().insertMultiple(records);
        return records;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser update(SysUser row) {
        HrPosition position = row.getPosition();
        if (position != null) {
            if (position.getPositionId() != null) {
                getMapper(HrPosition.class).updateByPrimaryKey(position);
            } else {
                getMapper(HrPosition.class).insert(position);
            }
            row.setPositionId(position.getPositionId());
        }
        HrOrganization organization = row.getOrganization();
        if (organization != null) {
            if (organization.getOrgId() != null) {
                getMapper(HrOrganization.class).updateByPrimaryKey(organization);
            } else {
                getMapper(HrOrganization.class).insert(organization);
            }
            row.setOrgId(organization.getOrgId());
        }
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getUserId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysUser row) {
        if (row == null || row.getUserId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser get(Object userId) {
        SysUser sysUserQuery = new SysUser();
        ReflectionKit.setFieldValue(sysUserQuery, "userId", userId);
        Optional<SysUser> row = this.selectOne(sysUserQuery);
        if (row.isPresent()) {
            SysUser sysUser =  row.get();
            if (sysUser.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, userId);
            }
            return sysUser;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, userId);
    }
}
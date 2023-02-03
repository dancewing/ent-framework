package io.entframework.kernel.system.modular.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import io.entframework.kernel.system.modular.entity.SysApp;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.repository.SysMenuRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SysMenuRepositoryImpl extends BaseRepositoryImpl<SysMenu> implements SysMenuRepository {
    public SysMenuRepositoryImpl() {
        super(SysMenu.class);
    }

    public SysMenuRepositoryImpl(Class<? extends SysMenu> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMenu insert(SysMenu row) {
        SysApp app = row.getApp();
        if (app != null && row.getAppId() == null) {
            getMapper(SysApp.class).insert(app);
            row.setAppId(app.getAppId());
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
    public List<SysMenu> insertMultiple(List<SysMenu> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysApp> apps = new ArrayList<>();
        for (SysMenu record : records) {
            SysApp app = record.getApp();
            Optional.ofNullable(app).ifPresent(apps::add);
        }
        getMapper(SysApp.class).insertMultiple(apps);
        records.forEach(record -> record.setAppId(record.getApp().getAppId()));
        getMapper().insertMultiple(records);
        return records;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMenu update(SysMenu row) {
        SysApp app = row.getApp();
        if (app != null) {
            if (app.getAppId() != null) {
                getMapper(SysApp.class).updateByPrimaryKey(app);
            } else {
                getMapper(SysApp.class).insert(app);
            }
            row.setAppId(app.getAppId());
        }
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getMenuId());
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(SysMenu row) {
        if (row == null || row.getMenuId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        row.setDelFlag(YesOrNotEnum.Y);
        return getMapper().updateByPrimaryKey(row, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysMenu get(Object menuId) {
        SysMenu sysMenuQuery = new SysMenu();
        ReflectionKit.setFieldValue(sysMenuQuery, "menuId", menuId);
        Optional<SysMenu> row = this.selectOne(sysMenuQuery);
        if (row.isPresent()) {
            SysMenu sysMenu =  row.get();
            if (sysMenu.getDelFlag() != YesOrNotEnum.N) {
                throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, menuId);
            }
            return sysMenu;
        }
        throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, menuId);
    }
}
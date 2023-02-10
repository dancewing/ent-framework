/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeTemplateFieldExceptionEnum;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateField;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateFieldDynamicSqlSupport;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRelDynamicSqlSupport;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * 系统主题模板属性service接口实现类
 *
 * @date 2021/12/17 10:34
 */
public class SysThemeTemplateFieldServiceImpl extends BaseServiceImpl<SysThemeTemplateFieldRequest, SysThemeTemplateFieldResponse, SysThemeTemplateField> implements SysThemeTemplateFieldService {

    public SysThemeTemplateFieldServiceImpl() {
        super(SysThemeTemplateFieldRequest.class, SysThemeTemplateFieldResponse.class, SysThemeTemplateField.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = new SysThemeTemplateField();

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeTemplateFieldRequest, sysThemeTemplateField);

        // 设置是否必填：如果请求参数为空，默认设置为非必填
        sysThemeTemplateField.setFieldRequired(ObjectUtil.isNull(sysThemeTemplateFieldRequest.getFieldType()) ? YesOrNotEnum.N : sysThemeTemplateFieldRequest.getFieldRequired());

        this.getRepository().insert(sysThemeTemplateField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest);

        // Guns开头的模板字段不能删除，系统内置
        if (sysThemeTemplateField.getFieldCode().toUpperCase(Locale.ROOT).startsWith(SystemConstants.THEME_CODE_SYSTEM_PREFIX)) {
            throw new SystemModularException(SysThemeExceptionEnum.THEME_IS_SYSTEM);
        }

        // 校验系统主题模板属性使用
        this.verificationAttributeUsage(sysThemeTemplateField);

        this.getRepository().deleteByPrimaryKey(getEntityClass(), sysThemeTemplateField.getFieldId());
    }

    /**
     * 校验系统主题模板属性使用
     *
     * @date 2021/12/24 9:16
     */
    private void verificationAttributeUsage(SysThemeTemplateField sysThemeTemplateField) {
        // 查询当前属性是否被使用
        SysThemeTemplateFieldRequest request = new SysThemeTemplateFieldRequest();
        request.setFieldCode(sysThemeTemplateField.getFieldCode());

        // 被使用，抛出异常
        if (this.countBy(request) > 0) {
            throw new SystemModularException(SysThemeTemplateFieldExceptionEnum.FIELD_IS_USED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysThemeTemplateFieldResponse update(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest);

        // 编号不能修改
        sysThemeTemplateFieldRequest.setFieldCode(null);

        // 更新属性
        BeanUtil.copyProperties(sysThemeTemplateFieldRequest, sysThemeTemplateField);

        this.getRepository().updateByPrimaryKey(sysThemeTemplateField);

        return this.converterService.convert(sysThemeTemplateField, getResponseClass());
    }

    @Override
    public SysThemeTemplateFieldResponse detail(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        return this.converterService.convert(this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest), getResponseClass());
    }

    @Override
    public PageResult<SysThemeTemplateFieldResponse> findPage(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        return this.page(sysThemeTemplateFieldRequest);
    }

    @Override
    public List<SysThemeTemplateFieldResponse> findRelList(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        List<String> fieldCodes = this.getFieldCodes(sysThemeTemplateFieldRequest);
        List<SysThemeTemplateField> sysThemeTemplateFields = null;
        // 如果关联属性非空，拼接查询条件
        if (!fieldCodes.isEmpty()) {
            sysThemeTemplateFields = getRepository().select(SysThemeTemplateField.class, c -> c.where(SysThemeTemplateFieldDynamicSqlSupport.fieldCode, SqlBuilder.isIn(fieldCodes)));
        }

        if (sysThemeTemplateFields == null) {
            return Collections.emptyList();
        }

        return sysThemeTemplateFields.stream().map(sysThemeTemplateField -> this.converterService.convert(sysThemeTemplateField, getResponseClass())).toList();
    }

    /**
     * 查询所有关联的属性编码
     *
     * @date 2021/12/24 14:38
     */
    private List<String> getFieldCodes(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        // 查询有关联的属性
        List<SysThemeTemplateRel> sysThemeTemplateRels = getRepository().select(SysThemeTemplateRel.class, c -> c.where(SysThemeTemplateRelDynamicSqlSupport.templateId,
                SqlBuilder.isEqualTo(sysThemeTemplateFieldRequest.getTemplateId())));

        // 过滤出所有的属性编码
        return sysThemeTemplateRels.stream().map(SysThemeTemplateRel::getFieldCode).toList();
    }

    @Override
    public List<SysThemeTemplateFieldResponse> findNotRelList(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        // 查询有关联的属性
        List<String> fieldCodes = getFieldCodes(sysThemeTemplateFieldRequest);

        // 查询没有关联的属性
        List<SysThemeTemplateField> sysThemeTemplateFields;
        // 如果关联属性非空，拼接条件；否者查询全部
        if (fieldCodes.size() > 0) {
            sysThemeTemplateFields = getRepository().select(getEntityClass(), c -> c.where(SysThemeTemplateFieldDynamicSqlSupport.fieldCode, SqlBuilder.isNotIn(fieldCodes)));
        } else {
            sysThemeTemplateFields = getRepository().select(getEntityClass(), c -> c);
        }
        return sysThemeTemplateFields.stream().map(sysThemeTemplateField -> this.converterService.convert(sysThemeTemplateField, getResponseClass())).toList();
    }

    @Override
    public boolean getKeyFileFlag(String code) {
        SysThemeTemplateFieldRequest request = new SysThemeTemplateFieldRequest();
        request.setFieldCode(code);
        SysThemeTemplateFieldResponse sysThemeTemplateField = this.selectOne(request);
        if (sysThemeTemplateField == null) {
            return false;
        }

        return ThemeFieldTypeEnum.FILE == sysThemeTemplateField.getFieldType();
    }

    /**
     * 获取主题模板属性
     *
     * @param sysThemeTemplateFieldRequest 请求参数
     * @return 主题模板属性
     * @date 2021/12/17 11:03
     */
    private SysThemeTemplateField queryThemeTemplateFieldById(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        Optional<SysThemeTemplateField> sysThemeTemplateField = this.getRepository().selectByPrimaryKey(getEntityClass(), sysThemeTemplateFieldRequest.getFieldId());
        if (sysThemeTemplateField.isEmpty()) {
            throw new SystemModularException(SysThemeTemplateFieldExceptionEnum.FIELD_NOT_EXIST);
        }
        return sysThemeTemplateField.get();
    }
}

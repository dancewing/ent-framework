/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.service.impl;

import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.dict.api.enums.DictTypeClassEnum;
import io.entframework.kernel.dict.api.exception.DictException;
import io.entframework.kernel.dict.api.exception.enums.DictExceptionEnum;
import io.entframework.kernel.dict.modular.entity.SysDictType;
import io.entframework.kernel.dict.modular.pojo.request.SysDictTypeRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictTypeResponse;
import io.entframework.kernel.dict.modular.service.SysDictTypeService;
import io.entframework.kernel.pinyin.api.PinYinApi;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeRequest, SysDictTypeResponse, SysDictType>
        implements SysDictTypeService {

    public SysDictTypeServiceImpl() {
        super(SysDictTypeRequest.class, SysDictTypeResponse.class, SysDictType.class);
    }

    @Resource
    private PinYinApi pinYinApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = this.converterService.convert(dictTypeRequest, getEntityClass());
        sysDictType.setStatusFlag(StatusEnum.ENABLE);
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.getRepository().insert(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysDictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        sysDictType.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysDictType);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictTypeResponse update(SysDictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        this.converterService.copy(dictTypeRequest, sysDictType);
        sysDictType.setDictTypeCode(null);
        // 设置首字母拼音
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.getRepository().updateByPrimaryKey(sysDictType);
        return this.converterService.convert(sysDictType, getResponseClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SysDictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType oldSysDictType = this.querySysDictType(dictTypeRequest);
        oldSysDictType.setStatusFlag(dictTypeRequest.getStatusFlag());
        this.getRepository().updateByPrimaryKey(oldSysDictType);
    }

    @Override
    public SysDictTypeResponse detail(SysDictTypeRequest dictTypeRequest) {
        return this.selectOne(dictTypeRequest);
    }

    @Override
    public List<SysDictTypeResponse> findList(SysDictTypeRequest dictTypeRequest) {
        return this.select(dictTypeRequest);
    }

    @Override
    public PageResult<SysDictTypeResponse> findPage(SysDictTypeRequest dictTypeRequest) {
        return this.page(dictTypeRequest);
    }

    /**
     * 校验dictTypeClass是否是系统字典，如果是系统字典只能超级管理员操作
     *
     * @date 2020/12/25 15:57
     */
    private void validateSystemTypeClassOperate(SysDictTypeRequest dictTypeRequest) {
        if (DictTypeClassEnum.SYSTEM_TYPE == dictTypeRequest.getDictTypeClass()) {
            if (!LoginContext.me().getSuperAdminFlag()) {
                throw new DictException(DictExceptionEnum.SYSTEM_DICT_NOT_ALLOW_OPERATION);
            }
        }
    }

    /**
     * 根据主键id获取对象
     *
     * @date 2021/1/26 13:28
     */
    private SysDictType querySysDictType(SysDictTypeRequest dictTypeRequest) {
        Optional<SysDictType> sysDictType = this.getRepository().selectByPrimaryKey(getEntityClass(),
                dictTypeRequest.getDictTypeId());
        if (sysDictType.isEmpty()) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }
        return sysDictType.get();
    }

}
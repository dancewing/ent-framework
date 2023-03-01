/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.dict.api.exception.DictException;
import io.entframework.kernel.dict.api.exception.enums.DictExceptionEnum;
import io.entframework.kernel.dict.modular.entity.SysDict;
import io.entframework.kernel.dict.modular.entity.SysDictDynamicSqlSupport;
import io.entframework.kernel.dict.modular.pojo.TreeDictInfo;
import io.entframework.kernel.dict.modular.pojo.request.SysDictRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictResponse;
import io.entframework.kernel.dict.modular.service.SysDictService;
import io.entframework.kernel.pinyin.api.PinYinApi;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class SysDictServiceImpl extends BaseServiceImpl<SysDictRequest, SysDictResponse, SysDict>
        implements SysDictService {

    public SysDictServiceImpl() {
        super(SysDictRequest.class, SysDictResponse.class, SysDict.class);
    }

    @Resource
    private PinYinApi pinYinApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictRequest dictRequest) {

        // 校验字典重复
        this.validateRepeat(dictRequest, false);

        SysDict sysDict = this.converterService.convert(dictRequest, getEntityClass());
        sysDict.setDictParentId(DictConstants.DEFAULT_DICT_PARENT_ID);
        sysDict.setDictPids(
                StrPool.BRACKET_START + DictConstants.DEFAULT_DICT_PARENT_ID + StrPool.BRACKET_END + StrPool.COMMA);
        sysDict.setStatusFlag(StatusEnum.ENABLE);
        sysDict.setDictNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDict.getDictName()));
        this.getRepository().insert(sysDict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysDictRequest dictRequest) {
        SysDict sysDict = this.querySysDict(dictRequest);
        sysDict.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysDict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictResponse update(SysDictRequest dictRequest) {

        // 校验字典重复
        this.validateRepeat(dictRequest, true);

        SysDict sysDict = this.querySysDict(dictRequest);
        this.converterService.copy(dictRequest, sysDict);

        // 不能修改字典类型和编码
        sysDict.setDictTypeCode(null);
        sysDict.setDictCode(null);
        sysDict.setDictNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDict.getDictName()));

        this.getRepository().updateByPrimaryKey(sysDict);
        return this.converterService.convert(sysDict, getResponseClass());
    }

    @Override
    public SysDictResponse detail(SysDictRequest dictRequest) {
        return this.selectOne(dictRequest);
    }

    @Override
    public List<SysDictResponse> findList(SysDictRequest dictRequest) {
        return this.select(dictRequest);
    }

    @Override
    public PageResult<SysDictResponse> findPage(SysDictRequest dictRequest) {
        return this.page(dictRequest);
    }

    @Override
    public List<TreeDictInfo> getTreeDictList(SysDictRequest dictRequest) {

        // 获取字典类型下所有的字典
        List<SysDictResponse> sysDictList = this.findList(dictRequest);
        if (sysDictList == null || sysDictList.isEmpty()) {
            return new ArrayList<>();
        }

        // 构造树节点信息
        ArrayList<TreeDictInfo> treeDictInfos = new ArrayList<>();
        for (SysDictResponse sysDict : sysDictList) {
            TreeDictInfo treeDictInfo = new TreeDictInfo();
            treeDictInfo.setDictId(sysDict.getDictId());
            treeDictInfo.setDictCode(sysDict.getDictCode());
            treeDictInfo.setDictParentId(sysDict.getDictParentId());
            treeDictInfo.setDictName(sysDict.getDictName());
            treeDictInfos.add(treeDictInfo);
        }

        // 构建菜单树
        return new DefaultTreeBuildFactory<TreeDictInfo>().doTreeBuild(treeDictInfos);
    }

    @Override
    public String getDictName(String dictTypeCode, String dictCode) {
        List<SysDict> list = this.getRepository().select(getEntityClass(),
                c -> c.where(SysDictDynamicSqlSupport.dictTypeCode, SqlBuilder.isEqualTo(dictTypeCode))
                        .and(SysDictDynamicSqlSupport.dictCode, SqlBuilder.isEqualTo(dictCode))
                        .and(SysDictDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N)));

        // 如果查询不到字典，则返回空串
        if (list.isEmpty()) {
            return CharSequenceUtil.EMPTY;
        }

        // 字典code存在多个重复的，返回空串并打印错误日志
        if (list.size() > 1) {
            log.error(DictExceptionEnum.DICT_CODE_REPEAT.getUserTip(), "", dictCode);
            return CharSequenceUtil.EMPTY;
        }

        String dictName = list.get(0).getDictName();
        return Objects.requireNonNullElse(dictName, CharSequenceUtil.EMPTY);
    }

    @Override
    public List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode) {

        List<SysDict> dictList = this.getRepository().select(getEntityClass(),
                c -> c.where(SysDictDynamicSqlSupport.dictTypeCode, SqlBuilder.isEqualTo(dictTypeCode)));
        if (dictList.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<SimpleDict> simpleDictList = new ArrayList<>();
        for (SysDict sysDict : dictList) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setCode(sysDict.getDictCode());
            simpleDict.setName(sysDict.getDictName());
            simpleDictList.add(simpleDict);
        }
        return simpleDictList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDictId(Long dictId) {
        Optional<SysDict> sysDict = this.getRepository().selectByPrimaryKey(getEntityClass(), dictId);
        sysDict.ifPresent(dict -> this.getRepository().delete(dict));
    }

    /**
     * 获取详细信息
     *
     * @date 2021/1/13 10:50
     */
    private SysDict querySysDict(SysDictRequest dictRequest) {
        Optional<SysDict> sysDict = this.getRepository().selectByPrimaryKey(getEntityClass(), dictRequest.getDictId());
        if (sysDict.isEmpty()) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictRequest.getDictId());
        }
        if (YesOrNotEnum.Y == sysDict.get().getDelFlag()) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictRequest.getDictId());
        }
        return sysDict.get();
    }

    /**
     * 检查添加和编辑字典是否有重复的编码和名称
     *
     * @date 2021/5/12 16:58
     */
    private void validateRepeat(SysDictRequest dictRequest, boolean editFlag) {

        // 检验同字典类型下是否有一样的编码
        long count = this.getRepository().count(getEntityClass(),
                c -> c.where(SysDictDynamicSqlSupport.dictTypeCode, SqlBuilder.isEqualTo(dictRequest.getDictTypeCode()))
                        .and(SysDictDynamicSqlSupport.dictCode, SqlBuilder.isEqualTo(dictRequest.getDictCode()))
                        .and(SysDictDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                        .and(SysDictDynamicSqlSupport.dictId,
                                SqlBuilder.isNotEqualTo(dictRequest.getDictId()).filter(v -> editFlag)));
        if (count > 0) {
            throw new DictException(DictExceptionEnum.DICT_CODE_REPEAT, dictRequest.getDictTypeCode(),
                    dictRequest.getDictCode());
        }

        // 检验同字典类型下是否有一样的名称
        long dictNameCount = this.getRepository().count(getEntityClass(),
                c -> c.where(SysDictDynamicSqlSupport.dictTypeCode, SqlBuilder.isEqualTo(dictRequest.getDictTypeCode()))
                        .and(SysDictDynamicSqlSupport.dictName, SqlBuilder.isEqualTo(dictRequest.getDictName()))
                        .and(SysDictDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                        .and(SysDictDynamicSqlSupport.dictId,
                                SqlBuilder.isNotEqualTo(dictRequest.getDictId()).filter(v -> editFlag)));
        if (dictNameCount > 0) {
            throw new DictException(DictExceptionEnum.DICT_NAME_REPEAT, dictRequest.getDictTypeCode(),
                    dictRequest.getDictCode());
        }

    }

}
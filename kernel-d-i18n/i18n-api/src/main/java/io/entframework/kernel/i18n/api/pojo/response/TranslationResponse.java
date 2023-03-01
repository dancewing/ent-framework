/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.i18n.api.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 多语言 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponse extends BaseResponse {

    /**
     * 主键id
     */
    @ChineseDescription("主键id")
    private Long tranId;

    /**
     * 编码
     */
    @ChineseDescription("编码")
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @ChineseDescription("多语言条例名称")
    private String tranName;

    /**
     * 语种字典
     */
    @ChineseDescription("语种字典")
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @ChineseDescription("翻译的值")
    private String tranValue;

}
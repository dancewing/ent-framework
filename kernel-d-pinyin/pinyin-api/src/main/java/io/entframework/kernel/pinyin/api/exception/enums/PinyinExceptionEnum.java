/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pinyin.api.exception.enums;

import io.entframework.kernel.pinyin.api.constants.PinyinConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 拼音工具相关异常
 *
 * @date 2020/12/4 9:32
 */
@Getter
public enum PinyinExceptionEnum implements AbstractExceptionEnum {

    /**
     * 字符不能转成汉语拼音
     */
    PARSE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + PinyinConstants.PINYIN_EXCEPTION_STEP_CODE + "01",
            "拼音转化异常，具体信息：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    PinyinExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}

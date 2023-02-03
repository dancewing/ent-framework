package io.entframework.kernel.db.api.exception.enums;

import io.entframework.kernel.db.api.constants.DbConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

@Getter
public enum DaoSupportExceptionEnum implements AbstractExceptionEnum {
    /**
     * 数据库操作未知异常
     */
    FIELD_NOT_FOUND(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "没有从SqlTable {} 中找到字段: {}"),
    FIELD_WRONG_TYPE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "没有从SqlTable {} 字段: {} 错误的类型"),
;
    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DaoSupportExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}

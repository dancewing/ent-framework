/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.exception.base;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 所有业务异常的基类
 * <p>
 * 在抛出异常时候，务必带上AbstractExceptionEnum枚举
 * <p>
 * 业务异常分为三种
 * <p>
 * 第一种是用户端操作的异常，例如用户输入参数为空，用户输入账号密码不正确
 * <p>
 * 第二种是当前系统业务逻辑出错，例如系统执行出错，磁盘空间不足
 * <p>
 * 第三种是第三方系统调用出错，例如文件服务调用失败，RPC调用超时
 *
 * @date 2020/10/15 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 返回给用户的提示信息
     */
    private final String userTip;

    /**
     * 异常的模块名称
     */
    private final String moduleName;

    /**
     * 异常的具体携带数据
     */
    private Object data;

    /**
     * 根据模块名，错误码，用户提示直接抛出异常
     */
    public ServiceException(String moduleName, String errorCode, String userTip) {
        super(userTip);
        this.errorCode = errorCode;
        this.moduleName = moduleName;
        this.userTip = userTip;
    }

    /**
     * 如果要直接抛出ServiceException，可以用这个构造函数
     */
    public ServiceException(String moduleName, AbstractExceptionEnum exception) {
        super(exception.getUserTip());
        this.moduleName = moduleName;
        this.errorCode = exception.getErrorCode();
        this.userTip = exception.getUserTip();
    }

    /**
     * 不建议直接抛出ServiceException，因为这样无法确认是哪个模块抛出的异常
     * <p>
     * 建议使用业务异常时，都抛出自己模块的异常类
     */
    public ServiceException(AbstractExceptionEnum exception) {
        super(exception.getUserTip());
        this.moduleName = RuleConstants.RULE_MODULE_NAME;
        this.errorCode = exception.getErrorCode();
        this.userTip = exception.getUserTip();
    }

    /**
     * 携带数据的异常构造函数
     */
    public ServiceException(String moduleName, String errorCode, String userTip, Object data) {
        super(userTip);
        this.errorCode = errorCode;
        this.moduleName = moduleName;
        this.userTip = userTip;
        this.data = data;
    }

}

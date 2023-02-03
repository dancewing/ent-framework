/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.constants;

/**
 * message模块的常量
 *
 * @date 2021/1/1 20:58
 */
public interface MessageConstants {

    /**
     * 消息模块的名称
     */
    String MESSAGE_MODULE_NAME = "kernel-s-message";

    /**
     * flyway 表后缀名
     */
    String FLYWAY_TABLE_SUFFIX = "_message";

    /**
     * flyway 脚本存放位置
     */
    String FLYWAY_LOCATIONS = "classpath:kernel_schema/message";

    /**
     * 异常枚举的步进值
     */
    String MESSAGE_EXCEPTION_STEP_CODE = "23";

    /**
     * 发送所有用户标识
     */
    String RECEIVE_ALL_USER_FLAG = "all";

    /**
     * 默认websocket-url
     */
    String DEFAULT_WS_URL = "ws://localhost:8080/webSocket/{token}";

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.timer.api.constants;

/**
 * 定时任务模块的常量
 *
 * @date 2020/10/27 13:46
 */
public interface TimerConstants {

    /**
     * timer模块的名称
     */
    String TIMER_MODULE_NAME = "kernel-d-timer";

    /**
     * flyway 表后缀名
     */
    String FLYWAY_TABLE_SUFFIX = "_timer";

    /**
     * flyway 脚本存放位置
     */
    String FLYWAY_LOCATIONS = "classpath:kernel_schema/timer";

    /**
     * 枚举的步进值
     */
    String TIMER_EXCEPTION_STEP_CODE = "11";

}

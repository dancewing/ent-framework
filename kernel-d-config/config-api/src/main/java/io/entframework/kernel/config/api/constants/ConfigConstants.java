/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.config.api.constants;

/**
 * 系统配置表的常量
 *
 * @date 2020/10/16 11:05
 */
public interface ConfigConstants {

    /**
     * config模块的名称
     */
    String CONFIG_MODULE_NAME = "kernel-d-config";

    /**
     * flyway 表后缀名
     */
    String FLYWAY_TABLE_SUFFIX = "_config";

    /**
     * flyway 脚本存放位置
     */
    String FLYWAY_LOCATIONS = "classpath:kernel_schema/config";

    /**
     * 异常枚举的步进值
     */
    String CONFIG_EXCEPTION_STEP_CODE = "04";

    /**
     * 后端部署的地址，在sys_config中的编码
     */
    String SYS_SERVER_DEPLOY_HOST = "SYS_SERVER_DEPLOY_HOST";

}

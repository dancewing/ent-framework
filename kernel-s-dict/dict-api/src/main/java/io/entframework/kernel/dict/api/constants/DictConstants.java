/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.dict.api.constants;

/**
 * 字典模块的常量
 *
 * @date 2020/10/29 11:38
 */
public interface DictConstants {

    /**
     * dict模块的名称
     */
    String DICT_MODULE_NAME = "kernel-s-dict";

    /**
     * flyway 表后缀名
     */
    String FLYWAY_TABLE_SUFFIX = "_dict";

    /**
     * flyway 脚本存放位置
     */
    String FLYWAY_LOCATIONS = "classpath:kernel_schema/dict";

    /**
     * 异常枚举的步进值
     */
    String DICT_EXCEPTION_STEP_CODE = "13";

    /**
     * 默认字典根节点的id
     */
    Long DEFAULT_DICT_PARENT_ID = -1L;

    /**
     * 系统配置分组code
     */
    String CONFIG_GROUP_DICT_TYPE_CODE = "config_group";

    /**
     * 多语言
     */
    String LANGUAGES_DICT_TYPE_CODE = "languages";

}

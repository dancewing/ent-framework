/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 项目相关的工具类
 *
 * @date 2021/5/18 10:41
 */
@Slf4j
public class ProjectUtil {

    /**
     * 前后端分离项目的标识
     */
    private static Boolean SEPARATION_FLAG = null;

    /**
     * 获取项目是否是前后端分离的，通过系统中的ErrorView来判断
     * <p>
     * 如果是分离版项目，则项目中会有ErrorStaticJsonView，如果是不分离版本，项目中则会有CustomErrorView
     *
     * @return true-分离版，false-不分离版
     * @date 2021/5/18 10:42
     */
    public static boolean getSeparationFlag() {
        if (SEPARATION_FLAG != null) {
            return SEPARATION_FLAG;
        }

        try {
            Class.forName("io.entframework.kernel.system.integration.ErrorStaticJsonView");
            SEPARATION_FLAG = true;
            return true;
        } catch (ClassNotFoundException e) {
            SEPARATION_FLAG = false;
            return false;
        }
    }

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.holder;

/**
 * 初始化标记，防止初始化多次
 *
 * @date 2019-09-27-17:23
 */
public class InitScanFlagHolder {

    private static Boolean INIT_MANAGER_FLAG = false;

    public static synchronized Boolean getFlag() {
        return INIT_MANAGER_FLAG;
    }

    public static synchronized void setFlag() {
        INIT_MANAGER_FLAG = true;
    }

}

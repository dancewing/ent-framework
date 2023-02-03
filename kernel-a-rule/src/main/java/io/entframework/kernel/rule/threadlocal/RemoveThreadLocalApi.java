/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.rule.threadlocal;

/**
 * 对程序进行拓展，方便清除ThreadLocal
 *
 * @date 2021/10/29 11:14
 */
public interface RemoveThreadLocalApi {

    /**
     * 具体删除ThreadLocal的逻辑
     *
     * @date 2021/10/29 11:19
     */
    void removeThreadLocalAction();

}

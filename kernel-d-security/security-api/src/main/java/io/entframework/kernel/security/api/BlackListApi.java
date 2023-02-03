/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api;


import java.util.Collection;

/**
 * 黑名单Api
 * <p>
 * 在黑名单的用户会被禁止访问程序
 *
 * @date 2020/11/15 16:31
 */
public interface BlackListApi {

    /**
     * 添加名单条目
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @date 2020/11/15 16:32
     */
    void addBlackItem(String content);

    /**
     * 删除名单条目
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @date 2020/11/15 16:32
     */
    void removeBlackItem(String content);

    /**
     * 获取整个黑名单
     *
     * @return 黑名单内容的列表
     * @date 2020/11/15 16:33
     */
    Collection<String> getBlackList();

    /**
     * 是否包含某个值
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @return true-包含值，false-不包含值
     * @date 2020/11/20 16:55
     */
    boolean contains(String content);

}

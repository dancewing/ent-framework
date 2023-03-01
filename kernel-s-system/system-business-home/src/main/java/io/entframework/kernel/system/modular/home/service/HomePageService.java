/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service;

import io.entframework.kernel.system.api.pojo.home.HomeCompanyInfo;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.home.pojo.OnlineUserStat;

import java.util.List;

/**
 * 首页服务接口
 *
 * @date 2022/2/11 20:41
 */
public interface HomePageService {

    /**
     * 获取在线用户统计
     *
     * @date 2022/2/11 20:40
     */
    OnlineUserStat getOnlineUserList(OnlineUserRequest onlineUserRequest);

    /**
     * 获取首页公司部门人员信息统计
     *
     * @date 2022/2/11 21:03
     */
    HomeCompanyInfo getHomeCompanyInfo();

    /**
     * 获取常用功能集合
     *
     * @date 2022/2/11 22:02
     */
    List<SysMenu> getCommonFunctions();

    /**
     * 将缓存中的访问次数信息保存到数据库
     *
     * @date 2022/2/11 22:02
     */
    void saveStatisticsCacheToDb();

}

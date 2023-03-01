/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.api.pojo.home;

import lombok.Data;

/**
 * 首页系统企业或公司信息封装
 *
 * @date 2022/1/25 15:06
 */
@Data
public class HomeCompanyInfo {

    /**
     * 所有组织机构数
     */
    private long organizationNum;

    /**
     * 所有企业人员总数
     */
    private long enterprisePersonNum;

    /**
     * 所有职位总数
     */
    private long positionNum;

    /**
     * 当前登录用户，所在公司的部门数量
     */
    private Integer currentDeptNum;

    /**
     * 当前登录用户，所在公司的总人数
     */
    private long currentCompanyPersonNum;

}

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.user.UserSelectTreeNode;
import io.entframework.kernel.system.modular.entity.SysUserRole;
import io.entframework.kernel.system.modular.service.SysUserRoleService;
import io.entframework.kernel.system.modular.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 *
 * @date 2020/11/6 09:47
 */
@RestController
@ApiResource(name = "用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 增加用户
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_增加", path = "/sys-user/add")
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysUserRequest sysUserRequest) {
        sysUserService.add(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统用户
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_删除", path = "/sys-user/delete")
    public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysUserRequest sysUserRequest) {
        sysUserService.del(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 批量删除系统用户
     *
     * @date 2021/4/7 16:12
     */
    @PostResource(name = "系统用户_批量删除系统用户", path = "/sys-user/batch-delete")
    public ResponseData<Void> batchDelete(
            @RequestBody @Validated(BaseRequest.batchDelete.class) SysUserRequest sysUserRequest) {
        sysUserService.batchDelete(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新系统用户
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "更新系统用户", path = "/sys-user/update")
    public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysUserRequest sysUserRequest) {
        sysUserService.update(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新状态
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户-更新状态", path = "/sys-user/update-status")
    public ResponseData<Void> updateStatus(
            @RequestBody @Validated(SysUserRequest.changeStatus.class) SysUserRequest sysUserRequest) {
        sysUserService.updateStatus(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 重置密码
     *
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "系统用户_重置密码", path = "/sys-user/reset-pwd")
    public ResponseData<Void> resetPwd(
            @RequestBody @Validated(SysUserRequest.resetPwd.class) SysUserRequest sysUserRequest) {
        sysUserService.resetPassword(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 授权角色
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权角色", path = "/sys-user/grant-role")
    public ResponseData<Void> grantRole(
            @RequestBody @Validated(SysUserRequest.grantRole.class) SysUserRequest sysUserRequest) {
        sysUserService.grantRole(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 授权数据
     *
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权数据", path = "/sys-user/grant-data")
    public ResponseData<Void> grantData(
            @RequestBody @Validated(SysUserRequest.grantData.class) SysUserRequest sysUserRequest) {
        sysUserService.grantData(sysUserRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看系统用户
     *
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_查看", path = "/sys-user/detail")
    public ResponseData<SysUserResponse> detail(@Validated(BaseRequest.detail.class) SysUserRequest sysUserRequest) {
        return ResponseData.ok(sysUserService.detail(sysUserRequest));
    }

    /**
     * 获取当前登录用户的信息
     *
     * @date 2021/1/1 19:01
     */
    @GetResource(name = "获取当前登录用户的信息", path = "/sys-user/current-user-info", requiredPermission = false)
    public ResponseData<SysUserResponse> currentUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setUserId(loginUser.getUserId());
        return ResponseData.ok(sysUserService.detail(sysUserRequest));
    }

    /**
     * 查询系统用户
     *
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "系统用户_查询", path = "/sys-user/page")
    public ResponseData<PageResult<SysUserResponse>> page(SysUserRequest sysUserRequest) {
        return ResponseData.ok(sysUserService.findPage(sysUserRequest));
    }

    /**
     * 导出用户
     *
     * @date 2020/11/6 13:57
     */
    @GetResource(name = "系统用户_导出", path = "/sys-user/export")
    public void export(HttpServletResponse response) {
        sysUserService.export(response);
    }

    /**
     * 获取用户选择树数据（用在系统通知，选择发送人的时候）
     *
     * @date 2021/1/15 8:28
     */
    @GetResource(name = "获取用户选择树数据（用在系统通知，选择发送人的时候）", path = "/sys-user/get-user-select-tree")
    public ResponseData<List<UserSelectTreeNode>> getUserTree() {
        return ResponseData.ok(this.sysUserService.userSelectTree(new SysUserRequest()));
    }

    /**
     * 获取用户数据范围列表
     *
     * @date 2020/11/6 13:51
     */
    @GetResource(name = "系统用户_获取用户数据范围列表", path = "/sys-user/get-user-data-scope")
    public ResponseData<List<Long>> ownData(@Validated(BaseRequest.detail.class) SysUserRequest sysUserRequest) {
        List<Long> userBindDataScope = sysUserService.getUserBindDataScope(sysUserRequest.getUserId());
        return ResponseData.ok(userBindDataScope);
    }

    /**
     * 获取用户的角色列表
     *
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_获取用户的角色列表", path = "/sys-user/get-user-roles")
    public ResponseData<List<SysUserRole>> ownRole(@Validated(BaseRequest.detail.class) SysUserRequest sysUserRequest) {
        Long userId = sysUserRequest.getUserId();
        return ResponseData.ok(sysUserRoleService.findListByUserId(userId));
    }

    /**
     * 用户下拉列表，可以根据姓名搜索
     * @param sysUserRequest 请求参数：name 姓名(可选)
     * @return 返回除超级管理员外的用户列表
     * @date 2020/11/6 09:49
     */
    @GetResource(name = "系统用户_选择器", path = "/sys-user/selector")
    public ResponseData<List<SimpleDict>> selector(SysUserRequest sysUserRequest) {
        return ResponseData.ok(sysUserService.selector(sysUserRequest));
    }

    /**
     * 获取所有用户ID和名称列表
     *
     * @date 2022/1/17 14:24
     **/
    @GetResource(name = "获取所有用户ID和名称列表", path = "/sys-user/get-all-user-id-list")
    public ResponseData<List<SysUserRequest>> getAllUserIdList() {
        return ResponseData.ok(sysUserService.getAllUserIdList());
    }

    /**
     * 运维平台接口检测
     *
     * @date 2022/1/27 14:29
     **/
    @GetResource(name = "运维平台接口检测", path = "/sys-user/devops-api-check", requiredLogin = false,
            requiredPermission = false)
    public ResponseData<Integer> devopsApiCheck(SysUserRequest sysUserRequest) {
        return ResponseData.ok(sysUserService.devopsApiCheck(sysUserRequest));
    }

    /**
     * 根据用户主键获取用户对应的token
     *
     * @date 2022/1/17 14:24
     **/
    @GetResource(name = "根据用户主键获取用户对应的token", path = "/sys-user/get-token-by-user-id")
    public ResponseData<String> getTokenByUserId(Long userId) {
        return ResponseData.ok(sysUserService.getTokenByUserId(userId));
    }

}

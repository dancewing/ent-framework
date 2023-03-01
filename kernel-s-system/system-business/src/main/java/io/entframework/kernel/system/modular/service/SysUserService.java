/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.system.api.UserServiceApi;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.user.UserSelectTreeNode;
import io.entframework.kernel.system.modular.entity.SysUser;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 系统用户service
 *
 * @date 2020/11/6 10:28
 */
public interface SysUserService extends BaseService<SysUserRequest, SysUserResponse, SysUser>, UserServiceApi {

	/**
	 * 增加用户
	 * @param sysUserRequest 请求参数封装
	 * @date 2020/11/21 12:32
	 */
	void add(SysUserRequest sysUserRequest);

	/**
	 * 删除系统用户
	 * @param sysUserRequest 删除参数
	 * @date 2020/11/21 14:54
	 */
	void del(SysUserRequest sysUserRequest);

	/**
	 * 编辑用户
	 * @param sysUserRequest 请求参数封装
	 * @date 2020/11/21 12:32
	 */
	SysUserResponse update(SysUserRequest sysUserRequest);

	SysUser update(SysUser entity);

	/**
	 * 更新用户信息（一般用于更新个人信息）
	 * @param sysUserRequest 请求参数封装
	 * @date 2020/11/21 12:32
	 */
	void updateInfo(SysUserRequest sysUserRequest);

	/**
	 * 修改状态
	 * @param sysUserRequest 请求参数封装
	 * @date 2020/11/21 14:19
	 */
	void updateStatus(SysUserRequest sysUserRequest);

	/**
	 * 修改密码
	 * @param sysUserRequest 请求参数封装
	 * @date 2020/11/21 14:26
	 */
	void updatePassword(SysUserRequest sysUserRequest);

	/**
	 * 重置密码
	 * @param sysUserRequest 重置参数
	 * @date 2020/11/6 13:47
	 */
	void resetPassword(SysUserRequest sysUserRequest);

	/**
	 * 修改头像
	 * @param sysUserRequest 修改头像参数
	 * @date 2020/11/6 13:47
	 */
	void updateAvatar(SysUserRequest sysUserRequest);

	/**
	 * 授权角色给某个用户
	 * @param sysUserRequest 授权参数
	 * @date 2020/11/21 14:43
	 */
	void grantRole(SysUserRequest sysUserRequest);

	/**
	 * 授权组织机构数据范围给某个用户
	 * @param sysUserRequest 授权参数
	 * @date 2020/11/21 14:48
	 */
	void grantData(SysUserRequest sysUserRequest);

	/**
	 * 查看用户详情
	 * @param sysUserRequest 查看参数
	 * @return 用户详情结果
	 * @date 2020/11/6 13:46
	 */
	SysUserResponse detail(SysUserRequest sysUserRequest);

	/**
	 * 查询系统用户
	 * @param sysUserRequest 查询参数
	 * @return 查询分页结果
	 * @date 2020/11/21 15:24
	 */
	PageResult<SysUserResponse> findPage(SysUserRequest sysUserRequest);

	/**
	 * 查询系统用户
	 * @param sysUserRequest 查询参数
	 * @return 查询分页结果
	 * @date 2020/11/21 15:24
	 */
	List<SysUserResponse> getUserList(SysUserRequest sysUserRequest);

	/**
	 * 导出用户
	 * @param response httpResponse
	 * @date 2020/11/6 13:47
	 */
	void export(HttpServletResponse response);

	/**
	 * 用户选择树数据
	 * @param sysUserRequest 参数
	 * @date 2021/1/15 11:16
	 */
	List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest);

	/**
	 * 根据账号获取用户
	 * @param account 账号
	 * @return 用户
	 * @date 2020/11/6 15:09
	 */
	SysUser getUserByAccount(String account);

	/**
	 * 获取用户头像的url
	 * @param fileId 文件id
	 * @date 2020/12/27 19:13
	 */
	String getUserAvatarUrl(Long fileId);

	/**
	 * 获取用户头像的url
	 * @param fileId 文件id
	 * @param token 预览文件带的token
	 * @date 2020/12/27 19:13
	 */
	String getUserAvatarUrl(Long fileId, String token);

	/**
	 * 根据机构id获取用户树节点列表
	 * @param orgId 机构id
	 * @date 2021/1/15 11:16
	 */
	List<UserSelectTreeNode> getUserTreeNodeList(Long orgId, List<UserSelectTreeNode> treeNodeList);

	/**
	 * 用户下拉列表选择
	 * @param sysUserRequest 查询参数
	 * @return 用户列表集合
	 * @date 2020/11/6 13:47
	 */
	List<SimpleDict> selector(SysUserRequest sysUserRequest);

	/**
	 * 批量删除用户
	 *
	 * @date 2021/4/7 16:13
	 */
	int batchDelete(SysUserRequest sysUserRequest);

	/**
	 * 获取所有用户的id
	 *
	 * @date 2021/6/20 12:10
	 */
	List<Long> getAllUserIds();

	/**
	 * 获取所有用户ID和名称列表
	 * @return {@link List< SysUserRequest>}
	 * @date 2022/1/17 15:05
	 **/
	List<SysUserRequest> getAllUserIdList();

	/**
	 * 根据用户主键获取用户对应的token
	 * @return {@link String}
	 * @date 2022/1/17 15:05
	 **/
	String getTokenByUserId(Long userId);

	/**
	 * 运维平台接口检测
	 * @return {@link Integer}
	 * @date 2022/1/27 14:29
	 **/
	Integer devopsApiCheck(SysUserRequest sysUserRequest);

}

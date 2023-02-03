/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import java.util.Date;
import java.util.List;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.system.api.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系统用户结果
 *
 * @date 2020/4/2 9:19
 */
@Data
public class SysUserResponse {

	/**
	 * 主键
	 */
	@ChineseDescription("主键")
	private Long userId;

	/**
	 * 账号
	 */
	@ChineseDescription("账号")
	private String account;

	/**
	 * 昵称
	 */
	@ChineseDescription("昵称")
	private String nickName;

	/**
	 * 姓名
	 */
	@ChineseDescription("姓名")
	private String realName;

	/**
	 * 头像
	 */
	@ChineseDescription("头像")
	private Long avatar;

	/**
	 * 生日
	 */
	@ChineseDescription("生日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	/**
	 * 性别（M-男，F-女）
	 */
	@ChineseDescription("性别（M-男，F-女）")
	private String sex;

	/**
	 * 邮箱
	 */
	@ChineseDescription("邮箱")
	private String email;

	/**
	 * 手机
	 */
	@ChineseDescription("手机")
	private String phone;

	/**
	 * 密码
	 */
	@ChineseDescription("密码")
	private String password;

	/**
	 * 电话
	 */
	@ChineseDescription("电话")
	private String tel;

	/**
	 * 用户所属机构
	 */
	@ChineseDescription("用户所属机构ID")
	private Long orgId;

	@ChineseDescription("用户所属机构")
	private HrOrganizationResponse organization;

	/**
	 * 用户所属机构的职务
	 */
	@ChineseDescription("用户所属机构的职务")
	private Long positionId;

	/**
	 * 职务名称
	 */
	@ChineseDescription("职务")
	private HrPositionResponse position;

	/**
	 * 状态
	 */
	@ChineseDescription("状态")
	private UserStatusEnum statusFlag;

	/**
	 * 用户角色id
	 */
	@ChineseDescription("用户角色id")
	private List<Long> grantRoleIdList;

	/**
	 * 是否是超级管理员，超级管理员可以拥有所有权限（Y-是，N-否）
	 */
	@ChineseDescription("是否是超级管理员，超级管理员可以拥有所有权限（Y-是，N-否）")
	private String superAdminFlag;

}

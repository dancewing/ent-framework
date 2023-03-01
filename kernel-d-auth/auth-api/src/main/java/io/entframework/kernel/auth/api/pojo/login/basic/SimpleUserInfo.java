/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.pojo.login.basic;

import io.entframework.kernel.rule.enums.GenderEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户基本信息
 *
 * @date 2020/12/26 18:14
 */
@Data
public class SimpleUserInfo implements Serializable {

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 姓名
	 */
	private String realName;

	/**
	 * 头像
	 */
	private Long avatar;

	/**
	 * 生日
	 */
	private LocalDate birthday;

	/**
	 * 性别（M-男，F-女）
	 */
	private GenderEnum sex;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 电话
	 */
	private String tel;

}

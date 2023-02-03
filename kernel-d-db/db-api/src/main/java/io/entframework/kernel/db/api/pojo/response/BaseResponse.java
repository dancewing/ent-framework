package io.entframework.kernel.db.api.pojo.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.entframework.kernel.rule.annotation.ChineseDescription;

import lombok.Data;

@Data
public class BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@ChineseDescription("创建时间")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@ChineseDescription("创建人ID")
	private Long createUser;

	/**
	 * 创建人名称
	 */
	@ChineseDescription("创建人名称")
	private String createUserName;

	/**
	 * 更新时间
	 */
	@ChineseDescription("更新时间")
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@ChineseDescription("更新人ID")
	private Long updateUser;

	/**
	 * 更新人名称
	 */
	@ChineseDescription("更新人名称")
	private String updateUserName;
}

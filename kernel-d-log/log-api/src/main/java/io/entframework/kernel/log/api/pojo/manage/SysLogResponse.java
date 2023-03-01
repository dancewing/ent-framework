package io.entframework.kernel.log.api.pojo.manage;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 日志记录 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogResponse extends BaseResponse {

	/**
	 * 主键
	 */
	@ChineseDescription("主键")
	private Long logId;

	/**
	 * 日志的名称，一般为业务名称
	 */
	@ChineseDescription("日志的名称，一般为业务名称")
	private String logName;

	/**
	 * 日志记录的内容
	 */
	@ChineseDescription("日志记录的内容")
	private String logContent;

	/**
	 * 服务名称，一般为spring.application.name
	 */
	@ChineseDescription("服务名称，一般为spring.application.name")
	private String appName;

	/**
	 * 当前用户请求的url
	 */
	@ChineseDescription("当前用户请求的url")
	private String requestUrl;

	/**
	 * 当前服务器的ip
	 */
	@ChineseDescription("当前服务器的ip")
	private String serverIp;

	/**
	 * 客户端的ip
	 */
	@ChineseDescription("客户端的ip")
	private String clientIp;

	/**
	 * 用户id
	 */
	@ChineseDescription("用户id")
	private Long userId;

	/**
	 * 请求http方法
	 */
	@ChineseDescription("请求http方法")
	private String httpMethod;

	/**
	 * 客户浏览器标识
	 */
	@ChineseDescription("客户浏览器标识")
	private String clientBrowser;

	/**
	 * 客户操作系统
	 */
	@ChineseDescription("客户操作系统")
	private String clientOs;

	/**
	 * 创建时间
	 */
	@ChineseDescription("发生时间")
	private LocalDateTime createTime;

	/**
	 * 创建人账号
	 */
	@ChineseDescription("操作人")
	private String createUserName;

	/**
	 * http或方法的请求参数体
	 */
	@ChineseDescription("http或方法的请求参数体")
	private String requestParams;

	/**
	 * http或方法的请求结果
	 */
	@ChineseDescription("http或方法的请求结果")
	private String requestResult;

}
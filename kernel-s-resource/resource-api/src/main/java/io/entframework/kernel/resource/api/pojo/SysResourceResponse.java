package io.entframework.kernel.resource.api.pojo;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysResourceResponse extends BaseResponse {

	/**
	 * 资源id
	 */
	@ChineseDescription("资源id")
	private Long resourceId;

	/**
	 * 应用编码
	 */
	@ChineseDescription("应用编码")
	private String appCode;

	/**
	 * 资源编码
	 */
	@ChineseDescription("资源编码")
	private String resourceCode;

	/**
	 * 资源名称
	 */
	@ChineseDescription("资源名称")
	private String resourceName;

	/**
	 * 项目编码，一般为spring.application.name
	 */
	@ChineseDescription("项目编码，一般为spring.application.name")
	private String projectCode;

	/**
	 * 类名称
	 */
	@ChineseDescription("类名称")
	private String className;

	/**
	 * 方法名称
	 */
	@ChineseDescription("方法名称")
	private String methodName;

	/**
	 * 资源模块编码，一般为控制器类名排除Controller
	 */
	@ChineseDescription("资源模块编码，一般为控制器类名排除Controller")
	private String modularCode;

	/**
	 * 资源模块名称，一般为控制器名称
	 */
	@ChineseDescription("资源模块名称，一般为控制器名称")
	private String modularName;

	/**
	 * 资源初始化的服务器ip地址
	 */
	@ChineseDescription("资源初始化的服务器ip地址")
	private String ipAddress;

	/**
	 * 是否是视图类型：Y-是，N-否 如果是视图类型，url需要以 '/view' 开头， 视图类型的接口会渲染出html界面，而不是json数据，
	 * 视图层一般会在前后端不分离项目出现
	 */
	@ChineseDescription("")
	private YesOrNotEnum viewFlag;

	/**
	 * 资源url
	 */
	@ChineseDescription("资源url")
	private String url;

	/**
	 * http请求方法
	 */
	@ChineseDescription("http请求方法")
	private String httpMethod;

	/**
	 * 是否需要登录：Y-是，N-否
	 */
	@ChineseDescription("是否需要登录：Y-是，N-否")
	private YesOrNotEnum requiredLoginFlag;

	/**
	 * 是否需要鉴权：Y-是，N-否
	 */
	@ChineseDescription("是否需要鉴权：Y-是，N-否")
	private YesOrNotEnum requiredPermissionFlag;

	/**
	 * 需要进行参数校验的分组
	 */
	@ChineseDescription("需要进行参数校验的分组")
	private String validateGroups;

	/**
	 * 接口参数的字段描述
	 */
	@ChineseDescription("接口参数的字段描述")
	private String paramFieldDescriptions;

	/**
	 * 接口返回结果的字段描述
	 */
	@ChineseDescription("接口返回结果的字段描述")
	private String responseFieldDescriptions;

}
package io.entframework.kernel.system.modular.home.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 常用功能的统计次数 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysStatisticsCountRequest extends BaseRequest {

	/**
	 * 主键ID
	 */
	@NotNull(message = "主键ID不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("主键ID")
	private Long statCountId;

	/**
	 * 用户id
	 */
	@ChineseDescription("用户id")
	private Long userId;

	/**
	 * 访问的地址
	 */
	@ChineseDescription("访问的地址")
	private Long statUrlId;

	/**
	 * 访问的次数
	 */
	@ChineseDescription("访问的次数")
	private Integer statCount;

	@NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
	@ChineseDescription("ID集合")
	private java.util.List<Long> statCountIds;

}
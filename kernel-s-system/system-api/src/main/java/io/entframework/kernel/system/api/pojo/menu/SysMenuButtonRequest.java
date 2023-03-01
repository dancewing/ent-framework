/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.menu;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * 系统菜单按钮请求参数
 *
 * @date 2021/1/9 11:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuButtonRequest extends BaseRequest {

	/**
	 * 主键
	 */
	@NotNull(message = "按钮id不能为空", groups = { update.class, detail.class, delete.class })
	@ChineseDescription("主键")
	private Long buttonId;

	/**
	 * 菜单按钮主键集合
	 */
	@NotEmpty(message = "菜单按钮主键集合不能为空", groups = { batchDelete.class })
	@ChineseDescription("菜单按钮主键集合")
	private Set<@NotNull(message = "菜单按钮主键集合不能为空", groups = { batchDelete.class }) Long> buttonIds;

	/**
	 * 菜单id，按钮需要挂在菜单下
	 */
	@NotNull(message = "菜单id不能为空", groups = { add.class, update.class, list.class, def.class })
	@ChineseDescription("菜单id，按钮需要挂在菜单下")
	private Long menuId;

	/**
	 * 按钮的名称
	 */
	@NotBlank(message = "按钮名称不能为空", groups = { add.class, update.class })
	@ChineseDescription("按钮的名称")
	private String buttonName;

	/**
	 * 按钮的编码
	 */
	@NotBlank(message = "按钮编码不能为空", groups = { add.class, update.class })
	@TableUniqueValue(message = "按钮编码存在重复", groups = { add.class, update.class }, tableName = "sys_menu_button",
			columnName = "button_code", idFieldName = "button_id", excludeLogicDeleteItems = true)
	@ChineseDescription("按钮的编码")
	private String buttonCode;

	/**
	 * 批量删除验证分组
	 */
	public @interface batchDelete {

	}

	/**
	 * 一键添加系统默认按钮
	 */
	public @interface def {

	}

}

/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api;

import java.util.Set;

/**
 * 系统角色数据范围service接口实现类
 *
 * @date 2021/2/4 16:01
 */
public interface RoleDataScopeServiceApi {

	/**
	 * 根据机构id集合删除
	 * @param orgIds 结构id集合
	 * @return
	 * @date 2021/2/4 15:56
	 */
	void delByOrgIds(Set<Long> orgIds);

}

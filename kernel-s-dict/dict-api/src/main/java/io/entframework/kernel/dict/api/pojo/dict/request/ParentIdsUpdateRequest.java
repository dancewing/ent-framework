/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.dict.api.pojo.dict.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有父ids 修改的请求参数类
 *
 * @date 2020/12/11 18:12
 */
@Data
public class ParentIdsUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String oldParentIds;

	private String newParentIds;

	private Date updateTime;

	private Long updateUser;

}

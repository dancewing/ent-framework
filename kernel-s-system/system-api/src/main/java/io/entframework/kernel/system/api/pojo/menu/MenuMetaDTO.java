/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuMetaDTO {

	private String icon;

	private String title;

	private Boolean hideMenu;

	private Boolean ignoreKeepAlive;

	private Boolean showMenu;

	private Integer orderNo;

	private String frameSrc;

}

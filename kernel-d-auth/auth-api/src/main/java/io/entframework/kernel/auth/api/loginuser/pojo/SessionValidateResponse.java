/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.loginuser.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session校验
 *
 * @date 2021/9/29 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionValidateResponse {

    /**
     * 校验结果
     */
    private Boolean validateResult;

}

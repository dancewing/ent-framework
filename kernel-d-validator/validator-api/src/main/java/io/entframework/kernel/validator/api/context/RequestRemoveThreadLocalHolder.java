/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.validator.api.context;

import io.entframework.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除参数缓存相关的ThreadLocal
 *
 * @date 2021/10/29 11:37
 */
@Component
public class RequestRemoveThreadLocalHolder implements RemoveThreadLocalApi {

    @Override
    public void removeThreadLocalAction() {
        RequestGroupContext.clear();
        RequestParamContext.clear();
    }

}

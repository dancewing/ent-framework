/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.support;

import org.springframework.lang.Nullable;

public interface ObjectConversionService extends org.springframework.core.convert.ConversionService {

    void copy(@Nullable Object source, @Nullable Object target);

}

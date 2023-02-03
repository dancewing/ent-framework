/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.support;

import java.util.Collections;
import java.util.List;

public class ConverterService extends GenericConversionService implements ObjectConversionService {
    public <T> List<T> convert(List<Object> sourceList, Class<T> targetType) {
        if (sourceList == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(o -> this.convert(o, targetType)).toList();
    }
}

/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.support;

import org.mapstruct.MappingTarget;

public interface ObjectConverter<S, T> extends org.springframework.core.convert.converter.Converter<S, T> {

	void copy(S source, @MappingTarget T target);

}

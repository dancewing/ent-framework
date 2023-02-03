package io.entframework.kernel.converter.support;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.Nullable;

public interface GenericObjectConverter extends GenericConverter {
    void copy(@Nullable Object source, @Nullable Object target, TypeDescriptor sourceType, TypeDescriptor targetType);
}

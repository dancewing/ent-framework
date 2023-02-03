package io.entframework.kernel.timer.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.timer.api.pojo.SysTimersRequest;
import io.entframework.kernel.timer.api.pojo.SysTimersResponse;
import io.entframework.kernel.timer.modular.entity.SysTimers;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysTimersConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysTimersRequest, SysTimers> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysTimers, SysTimersResponse> {
    }
}
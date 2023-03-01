package io.entframework.kernel.system.modular.loginlog.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogResponse;
import io.entframework.kernel.system.modular.loginlog.entity.SysLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysLoginLogConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysLoginLogRequest, SysLoginLog> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysLoginLog, SysLoginLogResponse> {

    }

}
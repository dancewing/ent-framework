package io.entframework.kernel.config.modular.converter;

import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.config.modular.pojo.request.SysConfigRequest;
import io.entframework.kernel.config.modular.pojo.response.SysConfigResponse;
import io.entframework.kernel.converter.support.ObjectConverter;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysConfigConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysConfigRequest, SysConfig> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysConfig, SysConfigResponse> {

    }

}
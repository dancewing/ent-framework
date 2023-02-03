package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysUserDataScopeRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysUserDataScope;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysUserDataScopeConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysUserDataScopeRequest, SysUserDataScope> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysUserDataScope, SysUserDataScopeResponse> {
    }
}
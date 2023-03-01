package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysRoleDataScopeRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysRoleDataScope;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysRoleDataScopeConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysRoleDataScopeRequest, SysRoleDataScope> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysRoleDataScope, SysRoleDataScopeResponse> {

    }

}
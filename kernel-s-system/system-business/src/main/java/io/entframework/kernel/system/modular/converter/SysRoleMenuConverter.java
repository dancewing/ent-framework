package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysRoleMenuRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysRoleMenuConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysRoleMenuRequest, SysRoleMenu> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysRoleMenu, SysRoleMenuResponse> {
    }
}
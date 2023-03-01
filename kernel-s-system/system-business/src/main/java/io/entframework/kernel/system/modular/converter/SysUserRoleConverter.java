package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserRoleResponse;
import io.entframework.kernel.system.modular.entity.SysUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysUserRoleConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysUserRoleRequest, SysUserRole> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysUserRole, SysUserRoleResponse> {

    }

}
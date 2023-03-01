package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysMenuResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResourceResponse;
import io.entframework.kernel.system.modular.entity.SysMenuResource;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysMenuResourceConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysMenuResourceRequest, SysMenuResource> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysMenuResource, SysMenuResourceResponse> {

    }

}
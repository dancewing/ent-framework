package io.entframework.kernel.resource.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.resource.modular.entity.SysResource;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysResourceConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysResourceRequest, SysResource> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysResource, SysResourceResponse> {

    }

}
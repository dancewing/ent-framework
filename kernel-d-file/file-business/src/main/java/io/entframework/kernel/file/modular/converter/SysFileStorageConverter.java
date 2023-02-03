package io.entframework.kernel.file.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.file.api.pojo.request.SysFileStorageRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileStorageResponse;
import io.entframework.kernel.file.modular.entity.SysFileStorage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysFileStorageConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysFileStorageRequest, SysFileStorage> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysFileStorage, SysFileStorageResponse> {
    }
}
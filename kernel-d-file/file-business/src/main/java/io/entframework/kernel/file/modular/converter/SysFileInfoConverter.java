package io.entframework.kernel.file.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysFileInfoConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysFileInfoRequest, SysFileInfo> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysFileInfo, SysFileInfoResponse> {
    }
}
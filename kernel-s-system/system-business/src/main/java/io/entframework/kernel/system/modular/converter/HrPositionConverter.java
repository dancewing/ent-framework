package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.HrPositionRequest;
import io.entframework.kernel.system.api.pojo.response.HrPositionResponse;
import io.entframework.kernel.system.modular.entity.HrPosition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface HrPositionConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<HrPositionRequest, HrPosition> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<HrPosition, HrPositionResponse> {

    }

}
package io.entframework.kernel.system.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface HrOrganizationConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<HrOrganizationRequest, HrOrganization> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<HrOrganization, HrOrganizationResponse> {
    }
}
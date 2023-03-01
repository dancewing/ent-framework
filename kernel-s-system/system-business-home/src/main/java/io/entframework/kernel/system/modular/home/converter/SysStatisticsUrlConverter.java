package io.entframework.kernel.system.modular.home.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsUrlRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsUrlResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysStatisticsUrlConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysStatisticsUrlRequest, SysStatisticsUrl> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysStatisticsUrl, SysStatisticsUrlResponse> {

    }

}
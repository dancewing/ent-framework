package io.entframework.kernel.system.modular.home.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsCountRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsCountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysStatisticsCountConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysStatisticsCountRequest, SysStatisticsCount> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysStatisticsCount, SysStatisticsCountResponse> {

    }

}
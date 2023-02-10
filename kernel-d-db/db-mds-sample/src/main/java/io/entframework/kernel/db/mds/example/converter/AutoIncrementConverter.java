package io.entframework.kernel.db.mds.example.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.db.mds.example.entity.AutoIncrement;
import io.entframework.kernel.db.mds.example.pojo.request.AutoIncrementRequest;
import io.entframework.kernel.db.mds.example.pojo.response.AutoIncrementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface AutoIncrementConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<AutoIncrementRequest, AutoIncrement> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<AutoIncrement, AutoIncrementResponse> {
    }
}
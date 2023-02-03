package io.entframework.kernel.message.db.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;
import io.entframework.kernel.message.db.entity.SysMessage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysMessageConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysMessageRequest, SysMessage> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysMessage, SysMessageResponse> {
    }
}
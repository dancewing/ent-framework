package io.entframework.kernel.system.modular.notice.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeRequest;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeResponse;
import io.entframework.kernel.system.modular.notice.entity.SysNotice;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysNoticeConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysNoticeRequest, SysNotice> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysNotice, SysNoticeResponse> {
    }
}
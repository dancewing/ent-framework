package io.entframework.kernel.system.modular.theme.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysThemeTemplateConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysThemeTemplateRequest, SysThemeTemplate> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysThemeTemplate, SysThemeTemplateResponse> {
    }
}
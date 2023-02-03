package io.entframework.kernel.system.modular.theme.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysThemeTemplateRelConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysThemeTemplateRelRequest, SysThemeTemplateRel> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysThemeTemplateRel, SysThemeTemplateRelResponse> {
    }
}
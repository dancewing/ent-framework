package io.entframework.kernel.system.modular.theme.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateField;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysThemeTemplateFieldConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysThemeTemplateFieldRequest, SysThemeTemplateField> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysThemeTemplateField, SysThemeTemplateFieldResponse> {
    }
}
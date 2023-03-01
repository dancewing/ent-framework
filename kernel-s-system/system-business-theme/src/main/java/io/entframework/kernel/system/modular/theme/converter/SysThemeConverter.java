package io.entframework.kernel.system.modular.theme.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.theme.SysThemeRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeResponse;
import io.entframework.kernel.system.modular.theme.entity.SysTheme;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysThemeConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysThemeRequest, SysTheme> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysTheme, SysThemeResponse> {

    }

}
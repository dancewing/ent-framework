package io.entframework.kernel.i18n.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.i18n.api.pojo.request.TranslationRequest;
import io.entframework.kernel.i18n.api.pojo.response.TranslationResponse;
import io.entframework.kernel.i18n.modular.entity.Translation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface TranslationConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<TranslationRequest, Translation> {

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<Translation, TranslationResponse> {

    }

}
package io.entframework.kernel.dict.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.dict.modular.entity.SysDictType;
import io.entframework.kernel.dict.modular.pojo.request.SysDictTypeRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysDictTypeConverter {

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface RequestConverter extends ObjectConverter<SysDictTypeRequest, SysDictType> {

	}

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface ResponseConverter extends ObjectConverter<SysDictType, SysDictTypeResponse> {

	}

}
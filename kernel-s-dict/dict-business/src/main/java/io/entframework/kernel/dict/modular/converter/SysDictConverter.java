package io.entframework.kernel.dict.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.dict.modular.entity.SysDict;
import io.entframework.kernel.dict.modular.pojo.request.SysDictRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysDictConverter {

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface RequestConverter extends ObjectConverter<SysDictRequest, SysDict> {

	}

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface ResponseConverter extends ObjectConverter<SysDict, SysDictResponse> {

	}

}
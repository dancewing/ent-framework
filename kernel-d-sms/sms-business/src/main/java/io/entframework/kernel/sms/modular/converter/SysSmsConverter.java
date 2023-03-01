package io.entframework.kernel.sms.modular.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.sms.modular.entity.SysSms;
import io.entframework.kernel.sms.modular.pojo.request.SysSmsRequest;
import io.entframework.kernel.sms.modular.pojo.response.SysSmsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysSmsConverter {

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface RequestConverter extends ObjectConverter<SysSmsRequest, SysSms> {

	}

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface ResponseConverter extends ObjectConverter<SysSms, SysSmsResponse> {

	}

}
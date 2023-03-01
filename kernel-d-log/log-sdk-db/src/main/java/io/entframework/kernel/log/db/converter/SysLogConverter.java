package io.entframework.kernel.log.db.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.log.api.pojo.manage.SysLogRequest;
import io.entframework.kernel.log.api.pojo.manage.SysLogResponse;
import io.entframework.kernel.log.db.entity.SysLog;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysLogConverter {

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface RequestConverter extends ObjectConverter<SysLogRequest, SysLog> {

	}

	@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	interface ResponseConverter extends ObjectConverter<SysLog, SysLogResponse> {

	}

}
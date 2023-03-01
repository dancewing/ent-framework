package io.entframework.kernel.system.modular.converter;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.converter.support.ObjectConversionService;
import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.request.HrPositionRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.api.pojo.response.HrPositionResponse;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import io.entframework.kernel.system.modular.entity.HrPosition;
import io.entframework.kernel.system.modular.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysUserConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysUserRequest, SysUser> {

        default HrPosition mapHrPositionRequestToHrPosition(HrPositionRequest hrPositionRequest) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(hrPositionRequest, HrPosition.class);
        }

        default HrOrganization mapHrOrganizationRequestToHrOrganization(HrOrganizationRequest hrOrganizationRequest) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(hrOrganizationRequest, HrOrganization.class);
        }

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysUser, SysUserResponse> {

        default HrPositionResponse mapHrPositionToHrPositionResponse(HrPosition hrPosition) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(hrPosition, HrPositionResponse.class);
        }

        default HrOrganizationResponse mapHrOrganizationToHrOrganizationResponse(HrOrganization hrOrganization) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(hrOrganization, HrOrganizationResponse.class);
        }

    }

}
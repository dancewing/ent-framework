package io.entframework.kernel.system.modular.converter;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.converter.support.ObjectConversionService;
import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.system.api.pojo.request.SysAppRequest;
import io.entframework.kernel.system.api.pojo.request.SysMenuRequest;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.modular.entity.SysApp;
import io.entframework.kernel.system.modular.entity.SysMenu;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface SysMenuConverter {

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<SysMenuRequest, SysMenu> {

        default SysApp mapSysAppRequestToSysApp(SysAppRequest sysAppRequest) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(sysAppRequest, SysApp.class);
        }

    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<SysMenu, SysMenuResponse> {

        default SysAppResponse mapSysAppToSysAppResponse(SysApp sysApp) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(sysApp, SysAppResponse.class);
        }

    }

}
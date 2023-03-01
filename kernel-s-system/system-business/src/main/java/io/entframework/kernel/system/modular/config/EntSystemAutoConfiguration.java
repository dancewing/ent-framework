package io.entframework.kernel.system.modular.config;

import io.entframework.kernel.system.modular.service.HrOrganizationService;
import io.entframework.kernel.system.modular.service.HrPositionService;
import io.entframework.kernel.system.modular.service.SysAppService;
import io.entframework.kernel.system.modular.service.SysMenuResourceService;
import io.entframework.kernel.system.modular.service.SysMenuService;
import io.entframework.kernel.system.modular.service.SysRoleDataScopeService;
import io.entframework.kernel.system.modular.service.SysRoleMenuService;
import io.entframework.kernel.system.modular.service.SysRoleResourceService;
import io.entframework.kernel.system.modular.service.SysRoleService;
import io.entframework.kernel.system.modular.service.SysUserDataScopeService;
import io.entframework.kernel.system.modular.service.SysUserRoleService;
import io.entframework.kernel.system.modular.service.SysUserService;
import io.entframework.kernel.system.modular.service.impl.HrOrganizationServiceImpl;
import io.entframework.kernel.system.modular.service.impl.HrPositionServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysAppServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysMenuResourceServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysMenuServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysRoleDataScopeServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysRoleMenuServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysRoleResourceServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysRoleServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysUserDataScopeServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysUserRoleServiceImpl;
import io.entframework.kernel.system.modular.service.impl.SysUserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.system.modular.controller",
        "io.entframework.kernel.system.modular.converter", "io.entframework.kernel.system.modular.service" })
@EntityScan("io.entframework.kernel.system.modular.entity")
public class EntSystemAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SysUserService.class)
    public SysUserService sysUserService() {
        return new SysUserServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysUserRoleService.class)
    public SysUserRoleService sysUserRoleService() {
        return new SysUserRoleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysUserDataScopeService.class)
    public SysUserDataScopeService sysUserDataScopeService() {
        return new SysUserDataScopeServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleService.class)
    public SysRoleService sysRoleService() {
        return new SysRoleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleMenuService.class)
    public SysRoleMenuService sysRoleMenuService() {
        return new SysRoleMenuServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleResourceService.class)
    public SysRoleResourceService sysRoleResourceService() {
        return new SysRoleResourceServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleDataScopeService.class)
    public SysRoleDataScopeService sysRoleDataScopeService() {
        return new SysRoleDataScopeServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(HrOrganizationService.class)
    public HrOrganizationService hrOrganizationService() {
        return new HrOrganizationServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(HrPositionService.class)
    public HrPositionService hrPositionService() {
        return new HrPositionServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuService.class)
    public SysMenuService sysMenuService() {
        return new SysMenuServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuResourceService.class)
    public SysMenuResourceService sysMenuResourceService() {
        return new SysMenuResourceServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysAppService.class)
    public SysAppService sysAppService() {
        return new SysAppServiceImpl();
    }

}
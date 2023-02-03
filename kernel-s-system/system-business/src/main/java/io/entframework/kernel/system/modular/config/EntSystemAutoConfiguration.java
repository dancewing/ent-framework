package io.entframework.kernel.system.modular.config;

import io.entframework.kernel.system.modular.repository.HrOrganizationRepository;
import io.entframework.kernel.system.modular.repository.HrPositionRepository;
import io.entframework.kernel.system.modular.repository.SysAppRepository;
import io.entframework.kernel.system.modular.repository.SysMenuRepository;
import io.entframework.kernel.system.modular.repository.SysMenuResourceRepository;
import io.entframework.kernel.system.modular.repository.SysRoleDataScopeRepository;
import io.entframework.kernel.system.modular.repository.SysRoleMenuRepository;
import io.entframework.kernel.system.modular.repository.SysRoleRepository;
import io.entframework.kernel.system.modular.repository.SysRoleResourceRepository;
import io.entframework.kernel.system.modular.repository.SysUserDataScopeRepository;
import io.entframework.kernel.system.modular.repository.SysUserRepository;
import io.entframework.kernel.system.modular.repository.SysUserRoleRepository;
import io.entframework.kernel.system.modular.repository.impl.HrOrganizationRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.HrPositionRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysAppRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysMenuRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysMenuResourceRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysRoleDataScopeRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysRoleMenuRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysRoleRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysRoleResourceRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysUserDataScopeRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysUserRepositoryImpl;
import io.entframework.kernel.system.modular.repository.impl.SysUserRoleRepositoryImpl;
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
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.controller", "io.entframework.kernel.system.modular.converter", "io.entframework.kernel.system.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.system.modular.mapper")
public class EntSystemAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysUserRepository.class)
    public SysUserRepository sysUserRepository() {
        return new SysUserRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysUserService.class)
    public SysUserService sysUserService(SysUserRepository sysUserRepository) {
        return new SysUserServiceImpl(sysUserRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysUserRoleRepository.class)
    public SysUserRoleRepository sysUserRoleRepository() {
        return new SysUserRoleRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysUserRoleService.class)
    public SysUserRoleService sysUserRoleService(SysUserRoleRepository sysUserRoleRepository) {
        return new SysUserRoleServiceImpl(sysUserRoleRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysUserDataScopeRepository.class)
    public SysUserDataScopeRepository sysUserDataScopeRepository() {
        return new SysUserDataScopeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysUserDataScopeService.class)
    public SysUserDataScopeService sysUserDataScopeService(SysUserDataScopeRepository sysUserDataScopeRepository) {
        return new SysUserDataScopeServiceImpl(sysUserDataScopeRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleRepository.class)
    public SysRoleRepository sysRoleRepository() {
        return new SysRoleRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleService.class)
    public SysRoleService sysRoleService(SysRoleRepository sysRoleRepository) {
        return new SysRoleServiceImpl(sysRoleRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleMenuRepository.class)
    public SysRoleMenuRepository sysRoleMenuRepository() {
        return new SysRoleMenuRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleMenuService.class)
    public SysRoleMenuService sysRoleMenuService(SysRoleMenuRepository sysRoleMenuRepository) {
        return new SysRoleMenuServiceImpl(sysRoleMenuRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleResourceRepository.class)
    public SysRoleResourceRepository sysRoleResourceRepository() {
        return new SysRoleResourceRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleResourceService.class)
    public SysRoleResourceService sysRoleResourceService(SysRoleResourceRepository sysRoleResourceRepository) {
        return new SysRoleResourceServiceImpl(sysRoleResourceRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleDataScopeRepository.class)
    public SysRoleDataScopeRepository sysRoleDataScopeRepository() {
        return new SysRoleDataScopeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysRoleDataScopeService.class)
    public SysRoleDataScopeService sysRoleDataScopeService(SysRoleDataScopeRepository sysRoleDataScopeRepository) {
        return new SysRoleDataScopeServiceImpl(sysRoleDataScopeRepository);
    }

    @Bean
    @ConditionalOnMissingBean(HrOrganizationRepository.class)
    public HrOrganizationRepository hrOrganizationRepository() {
        return new HrOrganizationRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(HrOrganizationService.class)
    public HrOrganizationService hrOrganizationService(HrOrganizationRepository hrOrganizationRepository) {
        return new HrOrganizationServiceImpl(hrOrganizationRepository);
    }

    @Bean
    @ConditionalOnMissingBean(HrPositionRepository.class)
    public HrPositionRepository hrPositionRepository() {
        return new HrPositionRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(HrPositionService.class)
    public HrPositionService hrPositionService(HrPositionRepository hrPositionRepository) {
        return new HrPositionServiceImpl(hrPositionRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuRepository.class)
    public SysMenuRepository sysMenuRepository() {
        return new SysMenuRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuService.class)
    public SysMenuService sysMenuService(SysMenuRepository sysMenuRepository) {
        return new SysMenuServiceImpl(sysMenuRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuResourceRepository.class)
    public SysMenuResourceRepository sysMenuResourceRepository() {
        return new SysMenuResourceRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysMenuResourceService.class)
    public SysMenuResourceService sysMenuResourceService(SysMenuResourceRepository sysMenuResourceRepository) {
        return new SysMenuResourceServiceImpl(sysMenuResourceRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysAppRepository.class)
    public SysAppRepository sysAppRepository() {
        return new SysAppRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysAppService.class)
    public SysAppService sysAppService(SysAppRepository sysAppRepository) {
        return new SysAppServiceImpl(sysAppRepository);
    }
}
package io.entframework.kernel.i18n.modular.config;

import io.entframework.kernel.i18n.modular.repository.TranslationRepository;
import io.entframework.kernel.i18n.modular.repository.impl.TranslationRepositoryImpl;
import io.entframework.kernel.i18n.modular.service.TranslationService;
import io.entframework.kernel.i18n.modular.service.impl.TranslationServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.i18n.modular.controller", "io.entframework.kernel.i18n.modular.converter", "io.entframework.kernel.i18n.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.i18n.modular.mapper")
public class EntI18nAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TranslationRepository.class)
    public TranslationRepository translationRepository() {
        return new TranslationRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TranslationService.class)
    public TranslationService translationService(TranslationRepository translationRepository) {
        return new TranslationServiceImpl(translationRepository);
    }
}
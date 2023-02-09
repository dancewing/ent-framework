package io.entframework.kernel.i18n.modular.config;

import io.entframework.kernel.i18n.modular.service.TranslationService;
import io.entframework.kernel.i18n.modular.service.impl.TranslationServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.i18n.modular.controller", "io.entframework.kernel.i18n.modular.converter", "io.entframework.kernel.i18n.modular.service"})
public class EntI18nAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TranslationService.class)
    public TranslationService translationService() {
        return new TranslationServiceImpl();
    }
}
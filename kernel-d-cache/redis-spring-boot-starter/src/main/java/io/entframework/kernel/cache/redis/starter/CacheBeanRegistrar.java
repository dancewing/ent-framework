package io.entframework.kernel.cache.redis.starter;

import lombok.Getter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class CacheBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Getter
    private static BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
            BeanDefinitionRegistry beanDefinitionRegistry) {
        CacheBeanRegistrar.beanDefinitionRegistry = beanDefinitionRegistry;
    }

}

package io.entframework.kernel.cache.redis.starter;

import io.entframework.kernel.cache.redis.RedisCacheManagerFactory;
import io.entframework.kernel.cache.redis.config.KernelRedisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
public class CacheAutoConfigure implements BeanPostProcessor, DisposableBean {

    @Autowired(required = false)
    private KernelRedisCacheProperties kernelRedisCacheProperties;

    private RedisCacheManagerFactory redisCacheManagerFactory;

    private volatile boolean init;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!init && CacheBeanRegistrar.getBeanDefinitionRegistry() != null) {
            init = true;

            BeanDefinitionRegistry beanDefinitionRegistry = CacheBeanRegistrar.getBeanDefinitionRegistry();
            redisCacheManagerFactory = new RedisCacheManagerFactory(kernelRedisCacheProperties);
            Map<String, BeanDefinition> definitionMap = redisCacheManagerFactory.createBeanDefinition();
            for (Map.Entry<String, BeanDefinition> entry : definitionMap.entrySet()) {
                String cacheBeanName = entry.getKey();
                BeanDefinition beanDefinition = entry.getValue();
                if (beanDefinitionRegistry.containsBeanDefinition(cacheBeanName)) {
                    beanDefinitionRegistry.removeBeanDefinition(cacheBeanName);
                    log.info("remove bean definition: {}", cacheBeanName);
                }
                log.info("register spring redis bean: {}", cacheBeanName);
                beanDefinitionRegistry.registerBeanDefinition(cacheBeanName, beanDefinition);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void destroy() throws Exception {
        if (redisCacheManagerFactory != null) {
            redisCacheManagerFactory.destroy();
        }
    }

}

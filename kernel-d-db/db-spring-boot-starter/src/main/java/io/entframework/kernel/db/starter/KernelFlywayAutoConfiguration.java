package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.flyway.FlywayService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author jeff_qian
 */
@Configuration
public class KernelFlywayAutoConfiguration {

    @Bean
    @Primary
    public FlywayService flywayService() {
        return new FlywayService();
    }
}

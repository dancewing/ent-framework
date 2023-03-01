package io.entframework.kernel.resource.modular.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class ResourceInitListener extends FlywayInitListener implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, ResourceConstants.FLYWAY_LOCATIONS, ResourceConstants.FLYWAY_TABLE_SUFFIX);
    }

}

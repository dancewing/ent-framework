package io.entframework.kernel.system.starter.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.system.api.constants.SystemConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class SystemInitListener extends FlywayInitListener implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, SystemConstants.FLYWAY_LOCATIONS, SystemConstants.FLYWAY_TABLE_SUFFIX);
    }

}

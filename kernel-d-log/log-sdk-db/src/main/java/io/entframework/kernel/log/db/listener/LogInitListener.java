package io.entframework.kernel.log.db.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.log.api.constants.LogConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class LogInitListener extends FlywayInitListener implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, LogConstants.FLYWAY_LOCATIONS, LogConstants.FLYWAY_TABLE_SUFFIX);
    }
}

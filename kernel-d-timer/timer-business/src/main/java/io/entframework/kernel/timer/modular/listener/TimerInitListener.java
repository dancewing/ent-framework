package io.entframework.kernel.timer.modular.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.timer.api.constants.TimerConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class TimerInitListener extends FlywayInitListener implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, TimerConstants.FLYWAY_LOCATIONS, TimerConstants.FLYWAY_TABLE_SUFFIX);
    }
}

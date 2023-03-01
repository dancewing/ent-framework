package io.entframework.kernel.message.db.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.message.api.constants.MessageConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class MessageInitListener extends FlywayInitListener implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, MessageConstants.FLYWAY_LOCATIONS, MessageConstants.FLYWAY_TABLE_SUFFIX);
    }

}

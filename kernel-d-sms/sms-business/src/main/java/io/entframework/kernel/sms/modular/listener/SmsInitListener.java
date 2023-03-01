package io.entframework.kernel.sms.modular.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.sms.api.constants.SmsConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class SmsInitListener extends FlywayInitListener implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, SmsConstants.FLYWAY_LOCATIONS, SmsConstants.FLYWAY_TABLE_SUFFIX);
    }

}

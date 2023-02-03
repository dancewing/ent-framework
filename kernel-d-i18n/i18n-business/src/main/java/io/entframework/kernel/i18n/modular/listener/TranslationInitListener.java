package io.entframework.kernel.i18n.modular.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.i18n.api.constants.TranslationConstants;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class TranslationInitListener extends FlywayInitListener implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, TranslationConstants.FLYWAY_LOCATIONS, TranslationConstants.FLYWAY_TABLE_SUFFIX);
    }
}

package io.entframework.kernel.file.modular.listener;

import io.entframework.kernel.db.flyway.FlywayInitListener;
import io.entframework.kernel.file.api.constants.FileConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class FileInitListener extends FlywayInitListener implements Ordered {

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        flywayMigrate(environment, FileConstants.FLYWAY_LOCATIONS, FileConstants.FLYWAY_TABLE_SUFFIX);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

}

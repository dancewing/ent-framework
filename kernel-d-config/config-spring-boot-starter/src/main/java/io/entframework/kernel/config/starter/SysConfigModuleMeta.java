package io.entframework.kernel.config.starter;

import io.entframework.kernel.config.api.constants.ConfigConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class SysConfigModuleMeta extends AbstractModuleMeta {

    public SysConfigModuleMeta() {
        super(ConfigConstants.CONFIG_MODULE_NAME, ConfigConstants.FLYWAY_TABLE_SUFFIX, ConfigConstants.FLYWAY_LOCATIONS);
    }
}

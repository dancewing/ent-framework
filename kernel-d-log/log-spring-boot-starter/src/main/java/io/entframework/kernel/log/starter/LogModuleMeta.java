package io.entframework.kernel.log.starter;

import io.entframework.kernel.log.api.constants.LogConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class LogModuleMeta extends AbstractModuleMeta {

	public LogModuleMeta() {
		super(LogConstants.LOG_MODULE_NAME, LogConstants.FLYWAY_TABLE_SUFFIX, LogConstants.FLYWAY_LOCATIONS);
	}

}

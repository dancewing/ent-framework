package io.entframework.kernel.system.starter;

import io.entframework.kernel.rule.plugin.AbstractModuleMeta;
import io.entframework.kernel.system.api.constants.SystemConstants;

public class SystemModuleMeta extends AbstractModuleMeta {

	public SystemModuleMeta() {
		super(SystemConstants.SYSTEM_MODULE_NAME, SystemConstants.FLYWAY_TABLE_SUFFIX,
				SystemConstants.FLYWAY_LOCATIONS);
	}

}

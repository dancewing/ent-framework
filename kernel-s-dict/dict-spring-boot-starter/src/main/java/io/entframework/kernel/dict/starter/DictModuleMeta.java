package io.entframework.kernel.dict.starter;

import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class DictModuleMeta extends AbstractModuleMeta {

	public DictModuleMeta() {
		super(DictConstants.DICT_MODULE_NAME, DictConstants.FLYWAY_TABLE_SUFFIX, DictConstants.FLYWAY_LOCATIONS);
	}

}

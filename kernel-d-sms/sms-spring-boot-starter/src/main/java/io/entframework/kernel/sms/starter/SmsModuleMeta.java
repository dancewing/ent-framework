package io.entframework.kernel.sms.starter;

import io.entframework.kernel.rule.plugin.AbstractModuleMeta;
import io.entframework.kernel.sms.api.constants.SmsConstants;

public class SmsModuleMeta extends AbstractModuleMeta {

	public SmsModuleMeta() {
		super(SmsConstants.SMS_MODULE_NAME, SmsConstants.FLYWAY_TABLE_SUFFIX, SmsConstants.FLYWAY_LOCATIONS);
	}

}

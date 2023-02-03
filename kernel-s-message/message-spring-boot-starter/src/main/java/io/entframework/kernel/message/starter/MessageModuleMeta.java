package io.entframework.kernel.message.starter;

import io.entframework.kernel.message.api.constants.MessageConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class MessageModuleMeta extends AbstractModuleMeta {

    public MessageModuleMeta() {
        super(MessageConstants.MESSAGE_MODULE_NAME, MessageConstants.FLYWAY_TABLE_SUFFIX, MessageConstants.FLYWAY_LOCATIONS);
    }
}
